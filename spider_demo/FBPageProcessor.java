package com.spider.spider_demo;
/***
爬蟲線程的主要邏輯區塊，處理資料後再送至pipeline、executor處理，連接FBpipeline、FileExcecutor、MainProcessor
***/
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Request;

public class FBPageProcessor extends Thread implements us.codecraft.webmagic.processor.PageProcessor {

    public FBPageProcessor(FBPageProcessor spider,String input,String input2,String input3){
    	
    	this.spider = spider;
    	this.input = input;
    	this.input2 = input2;
    	this.input3 = input3;
    }
    
    public FBPageProcessor(MainProcessor mainprocessor){
    	this.mainprocessor = mainprocessor;
    }
    
    public void Stop(){
    	spider.isStop = true;
    }
    
    public void setcount(int count){
    	this.count = count;
    }
    
    @Override
    public Site getSite(){
    	
    	for (Cookie cookie : cookies) { 
            site.addCookie(cookie.getName().toString(),cookie.getValue().toString());
        }
 
        return site.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
    }
    
    @Override
    public void process(Page page) {
   
    	if(page.getUrl().toString().matches("https://www.facebook.com/[a-zA-Z0-9\\.]*\\??(id=([0-9])*)*(&amp;)?fref=pb&sk=about&section=contact-info")){
    		
    		Pattern html=Pattern.compile("<!--(.+)-->");
    		Matcher result = html.matcher(page.getHtml().toString());
    		StringBuilder collection = new StringBuilder();
    		while(result.find()){
    			collection.append(result.group());
    		}
    		page.putField("name", collection.toString());
    	}
    	else{
    		
    		Pattern href = Pattern.compile("https://www.facebook.com/[a-zA-Z0-9\\.]*\\??(id=([0-9])*)*(&amp;)?fref=pb");
    		Matcher result = href.matcher(html);
    		int odd = 1;
    		while(result.find()){
    			
    			if((odd%2) == 0){
    				
    				odd++;
    				continue;
    			}
    			else{
    				
    				odd++;
    				scheduler.push(new Request(result.group()+"&sk=about&section=contact-info"),task);
    			}
    		}
    	}	
    }
    
    private void login(WebDriver driver,String url,String Cookies, String seed, String account, String password) {
    	
    	new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - 嘗試登入Facebook - - - - - - - - - - - - - - -");
    	File file = new File(Cookies);
    	if(!file.exists()){
    		
	    	driver.get(url);
	    	driver.findElement(By.xpath("//*[@id='email']")).sendKeys(account);
	    	driver.findElement(By.xpath("//*[@id='pass']")).sendKeys(password);
	    	if(isAppear(driver,"//*[@id='loginbutton']")){
	    		driver.findElement(By.xpath("//*[@id='loginbutton']")).click();
	    	}else{
	    		driver.findElement(By.xpath("//*[@id='u_0_2']")).click();
	    	}
	    	cookies = driver.manage().getCookies();
	    	try{
	    		
		    	file.createNewFile();
		        FileWriter fw = new FileWriter(file);
		        BufferedWriter bw = new BufferedWriter(fw);
		        for(Cookie ck : driver.manage().getCookies()){
		        	
		            bw.write(ck.getName() + ";" + ck.getValue() + ";");
		            bw.newLine();
		        }
		        bw.flush();
		        bw.close();
		        fw.close();
		        use_Cookies(driver, url, Cookies, seed);
	    	}catch(IOException io){
	    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nLogin:IOException\n - - - - - - - - - - - - - - -");
	    	}
    	}	
    	else{
    		use_Cookies(driver, url, Cookies, seed);
    	}
    }
    
    private void use_Cookies(WebDriver driver, String url, String Cookies, String seed){
    	
    	driver.get(url);
    	new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - 使用Cookies  - - - - - - - - - - - - - - - -");
    	File file=new File(Cookies);
    	try{
    		
    		FileReader fr=new FileReader(file);
    		BufferedReader br=new BufferedReader(fr);
	        String line;
	        while((line=br.readLine())!= null){
	        	
	        	StringTokenizer str=new StringTokenizer(line,";");
	            while(str.hasMoreTokens()){ 
	            	
	            	String name=str.nextToken();
	                String value=str.nextToken();
	                Cookie ck=new Cookie(name,value);
	                driver.manage().addCookie(ck);
	            }
	        }
	        br.close();
    	}catch(IOException io){
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nuse_Cookies:IOException\n - - - - - - - - - - - - - - -");
    	}
        driver.get(seed);
        new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - Sucess - - - - - - - - - - - - - - - -");
        cookies = driver.manage().getCookies();
        new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n Seed:"+ seed +"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }
    
    private boolean isAppear(WebDriver driver, String xpath){
    	
    	boolean status = false;
    	try{
    		
    		driver.findElement(By.xpath(xpath));
    		status = true;
    	}catch(NoSuchElementException ex){
    		status = false;
    	}
    	return status;
    }
    
    private String scroll(WebDriver driver, String xpath) throws InterruptedException{
    	
        while(!isAppear(driver,xpath)){
        	
        	try{
        		
	        	Thread.sleep(2000);
	        	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        	}catch(InvalidSelectorException in){
        		Thread.sleep(2000);
        	}
        }
        return isAppear(driver,xpath)? xpath : "";
    }
    
    private boolean prepare(){
    	
    	if(isStop){
    		
			task.stop();
			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \n爬取停止\n - - - - - - - - - - - - - - -");
		}
    	return isStop;
    }
    
    private void load(WebDriver driver) throws InterruptedException{
    	
    	int count = 1;
    	while(isAppear(driver,"//*[@id='reaction_profile_pager"+count+"']/div/a")){
    		
    		while(isAppear(driver,"//*[@id='reaction_profile_pager"+count+"']/div/a")){
    			
    			try{
    				
    				driver.findElement(By.xpath("//*[@id='reaction_profile_pager" + count + "']/div/a")).click();
    				if(isStop){
    					
    					new StatusMessage(mainprocessor, "- - - - - - - - - - - - - - - - \n加載停止\n - - - - - - - - - - - - - - - -");
    					return;
    				}
    			}catch(ElementNotVisibleException e){
    				Thread.sleep(500);
    			}catch(NoSuchElementException n){
    				Thread.sleep(500);
    			}catch(StaleElementReferenceException s){
    				Thread.sleep(500);
    			}catch(WebDriverException w){
    				Thread.sleep(500);
    			}
    			finally{
    				count++;
    			}	
    		}
    		count = 1;
    	}
    }
    
    private void crawling(WebDriver driver, FBPageProcessor quest, String seed, String account, String password) throws InterruptedException,java.net.SocketTimeoutException{
    	
    	login(driver, "https://www.facebook.com","C:/cookies/browser.data", seed, account, password);
    	task = Spider.create(quest);
    	do{
    		
			String path = driver.findElement(By.xpath(scroll(driver, "(//a[@class='_2x4v'])["+count+"]"))).getAttribute("href");
			driver.get(path);
			if(prepare()){return;}
			load(driver);
			html = driver.findElement(By.xpath("//*")).getAttribute("outerHTML");
			while(task.getStatus().toString() == "Running"){
				if(prepare()){return;}
			}
			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \n加載Scheduler. . . . . .\n - - - - - - - - - - - - - - -");
			Spider.create(quest).thread(1).addUrl("https://www.facebook.com").run();
			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \n正在爬取第    " + count + "	則貼文. . . . . .\n - - - - - - - - - - - - - - -");
			FBpipeline fbpipeline = new FBpipeline(mainprocessor);
			FileExecutor fileexcecutor = new FileExecutor(mainprocessor,count);
			task = Spider.create(quest).thread(5).addPipeline(fbpipeline).addPipeline(fileexcecutor);
			task.setScheduler(scheduler.setDuplicateRemover(new HashSetDuplicateRemover())).start();
			driver.navigate().back();
    	}while(scroll(driver,"(//a[@class='_2x4v'])["+(count++)+"]")!="");
    }
    
    public void run(){
    	
    	DOMConfigurator.configure("C:/Users/steve216/workspace/spider-demo/src/log4j.xml");
    	System.setProperty("webdriver.chrome.driver", "D:/drivers/chromedriver_win32-2.36/chromedriver.exe");	 
    	Map<String, Object> prefs = new HashMap<String, Object>();
    	prefs.put("profile.default_content_setting_values.notifications", 2);   
        ChromeOptions options = new ChromeOptions();  
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=720,480");
    	WebDriver driver = new ChromeDriver(options);
    	try{
    		spider.crawling(driver,spider, input, input2, input3);
    	}catch(InterruptedException in){
    		
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nrun:InterruptedException\n - - - - - - - - - - - - - - -");
    		return;
    	}catch(java.net.SocketTimeoutException s){
    		
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nrun:SocketTimeoutException\n - - - - - - - - - - - - - - -");
    		return;
    	}finally{
    		driver.close();
    	}
    }
    
    private Site site = (Site.me().setRetryTimes(3).setSleepTime(1000));
    private String html;
    private Set<Cookie> cookies;
    private QueueScheduler scheduler = new QueueScheduler();
    private Spider task;
    private MainProcessor mainprocessor;
    private String input,input2,input3;
    private FBPageProcessor spider;
    private int count = 1;
    private volatile boolean isStop = false;
  
}
