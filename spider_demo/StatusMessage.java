package com.spider.spider_demo;
/***
獨立區塊，可在各區塊輸出訊息至Current Status訊息框，連接MessageProcessor - > MainProcessor
***/
public class StatusMessage {
	
	public StatusMessage(MainProcessor mainprocessor,String message){
		
		this.mainprocessor = mainprocessor;
		process(message);
	}
	
	private void process(String message){
		
		this.message = new MessageProcessor(mainprocessor);
		this.message.content = "\n" + message + "\n";
		deliver();
	}
	
	private void deliver(){
		message.send(message,message.content);;
	}
	
	private MainProcessor mainprocessor;
	private MessageProcessor message;
	
}
