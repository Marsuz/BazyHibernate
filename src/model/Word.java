package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcin on 2015-11-17.
 */
public class Word{

    private String id;
    private Set<Sentence> sentences;


    public Word() {
    }

    public Word(String word) {
        id = word;
        sentences = new HashSet<Sentence>();
    }

    public Set<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String getId() {
        return id;
    }

    public void setId(String wordId) {
        this.id = wordId;
    }

    public void addSentence(Sentence x) {
        sentences.add(x);
    }
}
