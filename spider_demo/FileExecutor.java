package com.spider.spider_demo;
/***
將從FBPageProcessor接收處理過的資料作後處理，寫成檔案的pipeline，連接MessageProcessor、MainProcessor
***/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

class FileExecutor implements Pipeline{

	public FileExecutor(MainProcessor mainprocessor,int count){
		
		this.mainprocessor = mainprocessor;
		this.count = count;
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		message = new MessageProcessor(mainprocessor);
		message.collection.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
		for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()){
			
			String split = entry.getValue().toString().replace("<!--", "");
			split = split.replace("-->", "");
			Document DOM = Jsoup.parse(split);
		    	name = DOM.select("a[class=_2nlw _2nlv]").first();
			col = DOM.select("li[class~=_3pw9]");
			if(new File("C:/down/Post" + count + ".txt").exists()){
				
				if(!filter()){
					return;
				}
			}
		}
		message.collection.append(name.text()+"'s info{");	
		for(Element e2 : col){
			
			if(e2 == col.last()){
				message.collection.append("\n\t\""+e2.text()+"\"");
			}else{
				message.collection.append("\n\t\""+e2.text()+"\",");
			}	
		}
		message.collection.append("\n}\n");
		message.collection.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n\n");
		ConvertToFile(count);
	}
	
	private void deliver(){
		message.send(message,count);;
	}
	
	private boolean filter(){
		
		try{
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File("C:/down/Post" + count + ".txt")),"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);  
	        String line = null;
	        while((line = bufferedReader.readLine()) != null){
	        	
	        	if (line.contains(name.text())){
	        		
	        		bufferedReader.close();
	                 return false;  
	        	}     
	        }
	        bufferedReader.close();
			return true;
		}catch(IOException io){
			
			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nfilter:IOException\n - - - - - - - - - - - - - - -");
			return false;
		}
	}
	
	public void ConvertToFile(int count){
		
		try{
			
			deliver();
			file = new FileWriter("C:/down/Post" + count + ".txt",true);
			file.write(message.collection.toString());
			file.flush();
			file.close();
		}catch(IOException io){
			
			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nConvertToFile:IOException\n - - - - - - - - - - - - - - -");
			return;
		}
	}
	
	private MainProcessor mainprocessor;
	private MessageProcessor message;
	private Element name = null;
	private Elements col = null;
	private int count;
	private FileWriter file;
	
}
