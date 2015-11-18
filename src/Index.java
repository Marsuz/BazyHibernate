import java.io.IOException;
import java.util.*;

import model.Word;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.ProcessedUrl;
import model.Sentence;
import persistence.HibernateUtils;

public class Index 
{		
	public void indexWebPage(String url) throws IOException
	{
		
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.body().select("*");
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		ProcessedUrl newUrl = new ProcessedUrl(url, new Date(), new HashSet<Sentence>());
		session.persist(newUrl);
		for (Element element : elements) 
		{
			if (element.ownText().trim().length() > 1)
			{
				for (String sentenceContent : element.ownText().split("\\. "))
				{

					Sentence sentence = new Sentence(sentenceContent);
					for(Word x : sentence.getContent()) {
						List<Word> words = session.createQuery("select w from Word w where w.id = :arg").setParameter("arg", x.getId()).list();
						if(words.size() == 0) {
							session.persist(x);
						}
					}
					newUrl.addSentence(sentence);
					sentence.setUrl(newUrl);
					session.persist(sentence);

				}
			}
		}
		transaction.commit();
		session.close();
	}

	public List<Sentence> findSentencesByWords(String words)
	{
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		String[] wordsArray = words.split(" ");
		List<Sentence> result = session.createQuery("select s from Sentence s join s.content w " +
													"where w.id in :query " +
													"group by s.id, w order by count(*) desc").setParameterList("query", wordsArray).list();
		transaction.commit();
		session.close();

		return result;
	}
	
	public List<Sentence> findSentencesLongerThan(Long length) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		List<Sentence> result = session.createQuery("select s from Sentence s join s.content w " +
													"group by s.id having sum(length(w.id)) >= :len").setParameter("len", length).list();
		transaction.commit();
		session.close();
		
		return result;
	}

	public List<ProcessedUrl> getSitesWithSentencesCount() {

		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		List<ProcessedUrl> result = session.createQuery("select p from ProcessedUrl p order by p.sentences.size").list();

		transaction.commit();
		session.close();

		return result;
	}

	public int getWordCount(String word, String url) {

		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		//Word word = new Word(w);
		List<Sentence> sentences = session.createQuery("select distinct s from Sentence s " +
												"join s.content w " +
												"join s.url u " +
												"where w.id = :arg and u.url = :arg2").setParameter("arg", word).setParameter("arg2", url).list();
		int result = 0;
		for(Sentence s : sentences) {
			for(Word x : s.getContent()) {
				if(x.getId().equals(word)) {
					result++;
				}
			}
		}
		transaction.commit();
		session.close();

		return result;

	}
	
}
