package com.spider.spider_demo;

public class MessageProcessor{
	
	public MessageProcessor(MainProcessor mainprocessor){
		this.mainprocessor = mainprocessor;
	}
	
	public void send(MessageProcessor message,String content){
		message.mainprocessor.isUpdate(content);
	}
	
	public void send(MessageProcessor message,int count){
		message.mainprocessor.isUpdate(count);
	}
	
	private MainProcessor mainprocessor;
    	String content = new String();
	StringBuilder collection = new StringBuilder();
	
}
