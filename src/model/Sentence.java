package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
	
	private long id;
	private List<Word> content;
	private ProcessedUrl url;

	public Sentence() 
	{
		
	}
	
	public Sentence(String content)
	{
		this.content = new ArrayList<Word>();
		for(String x : content.split(" ")) {
			this.content.add(new Word(x));
		}
	}

	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public List<Word> getContent() {
		return content;
	}

	public void setContent(List<Word> content) {
		this.content = content;
	}
	
	public ProcessedUrl getUrl() {
		return url;
	}

	public void setUrl(ProcessedUrl url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		for(Word x : content) {
			sb.append(x.getId()).append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return(sb.toString());
	}


}
