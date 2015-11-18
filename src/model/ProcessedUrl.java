package model;

import java.util.Date;
import java.util.Set;

public class ProcessedUrl {

	private long id;
	private String url;
	private Date date;
	private Set<Sentence> sentences;
	
	public ProcessedUrl() {}
	
	public ProcessedUrl(String url, Date date, Set<Sentence> sentences) {
		this.url = url;
		this.date = date;
		this.sentences = sentences;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(Set<Sentence> sentences) {
		this.sentences = sentences;
	}

	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
	}
	
	
}
