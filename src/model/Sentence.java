package model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Sentence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToMany
	@JoinTable(name = "SentenceWord", joinColumns = @JoinColumn(name = "sentenceId", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "wordId", referencedColumnName = "id"))
	private List<Word> content;
	@ManyToOne
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
