package com.spider.spider_demo;

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
