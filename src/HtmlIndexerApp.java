import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import model.ProcessedUrl;
import model.Sentence;
import persistence.HibernateUtils;

public class HtmlIndexerApp 
{

	public static void main(String[] args) throws IOException
	{
		HibernateUtils.getSession().close();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Index indexer = new Index(); 
		
		while (true)
		{
			System.out.println("\nHtmlIndexer [? for help] > : ");
			String command = bufferedReader.readLine();
	        long startAt = new Date().getTime();

			if (command.startsWith("?"))
			{
				System.out.println("'?'      	- print this help");
				System.out.println("'x'      	- exit HtmlIndexer");
				System.out.println("'i URLs'  	- index URLs, space separated");
				System.out.println("'fw WORDS'	- find sentences containing all WORDs, space separated");
				System.out.println("'fl LENGTH'	- find sentences longer than LENGTH");
				System.out.println("'sites' - print all indexed sites with number of sentences they contain");
				System.out.println("'cw WORD URL - print number of WORDs in URL");
			}
			else if (command.startsWith("x"))
			{
				System.out.println("HtmlIndexer terminated.");
				HibernateUtils.shutdown();
				break;				
			}
			else if (command.startsWith("i "))
			{
				for (String url : command.substring(2).split(" "))
				{
					try {
						indexer.indexWebPage(url);
						System.out.println("Indexed: " + url);
					} catch (Exception e) {
						System.out.println("Error indexing: " + e.getMessage());
					}
				}
			}
			else if (command.startsWith("fw "))
			{
				for (Sentence sentence : indexer.findSentencesByWords(command.substring(3)))
				{
					System.out.println("Found in sentence: " + sentence + " in URL: " + sentence.getUrl().getUrl());
				}
			}
			
			else if (command.startsWith("fl "))
			{
				for (Sentence sentence : indexer.findSentencesLongerThan(Long.parseLong(command.substring(3))))
				{
					System.out.println("Found in sentence: " + sentence + " in URL: " + sentence.getUrl().getUrl());
				}
			}
			else if (command.startsWith("sites")) {

				for (ProcessedUrl processedUrl : indexer.getSitesWithSentencesCount()) {
					System.out.println("Site: " + processedUrl.getUrl() + " sentences: " + processedUrl.getSentences().size());
				}

			}
			else if(command.startsWith("cw ")) {

				String[] arguments = command.split(" ");
				System.out.println(indexer.getWordCount(arguments[1], arguments[2]));

			}
			
			System.out.println("took "+ (new Date().getTime() - startAt)+ " ms");		

		}

	}

}
