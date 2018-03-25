package com.spider.spider_demo;

/***
此項目中的Main character，作為各類別，不同執行緒之間連接用的載體。連接Container - > FBPageProcessor - > 
 - > FBpipeline、FileExecutor - > MessageProcessor - > MainProcessor
 以及StatusMessage - > MessageProcessor - > MainProcessor
***/

import java.io.File; 

public class MainProcessor {
	
	public MainProcessor(){
		
		container = new Container();
		if(!new File("C:/down").exists()){
			new File("C:/down").mkdirs();
		}
		if(!new File("C:/cookies").exists()){
			new File("C:/cookies").mkdirs();
		}
	}
	
	public void isUpdate(String content){
		container.rewrite(content);
	}
	
	public void isUpdate(int count){	

		File file = new File("C:/down/Post" + count + ".txt");
    		if(!file.exists()){
    			container.rewrite(count,true);
    		}
	}
	
	public static void main(String[] argv){
		
		MainProcessor mainprocessor = new MainProcessor();
		mainprocessor.container = new Container(mainprocessor);
		mainprocessor.container.setVisible(true);
		mainprocessor.container.initComponents();
	}
	
	public Container container;
	
}
