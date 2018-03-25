package com.spider.spider_demo;

import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

class FBpipeline implements Pipeline{

	public FBpipeline(MainProcessor mainprocessor){
		this.mainprocessor = mainprocessor;
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		message = new MessageProcessor(mainprocessor);
		for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()){
			
			String split = entry.getValue().toString().replace("<!--", "");
			split = split.replace("-->", "");
			Document DOM = Jsoup.parse(split);
			Element name = DOM.select("a[class=_2nlw _2nlv]").first();
			String result = " : 已抓取頁面    : "+name.text()+"\n";
			message.content = result ;
			deliver();
		}
	}
	
	private void deliver(){
		message.send(message,message.content);;
	}
	
	private MainProcessor mainprocessor;
	private MessageProcessor message;
	
}
