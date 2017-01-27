package makeTriplicity;

import java.util.ArrayList;

public class Sentence {
	
	private String sentenceText;
	private ArrayList<Phrase> phraseList;
	
	//コンストラクタ
	public Sentence(String sentenceText) {
		this.sentenceText = sentenceText;
	}
	
	public String getSentenceText() {
		return sentenceText;
	}
	
	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}
	
	public ArrayList<Phrase> getPhraseList() {
		return phraseList;
	}
	
	public void setPhraseList(ArrayList<Phrase> phraseList) {
		this.phraseList = phraseList;
	}

}
