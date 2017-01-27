package makeTriplicity;

import java.util.ArrayList;

public class Snippet {
	
	private String snippetText;
	private ArrayList<Sentence> sentenceList;
	
	//コンストラクタ
	public Snippet(String snippetText) {
		this.snippetText = snippetText;
	}

	public String getSnippetText() {
		return snippetText;
	}

	public void setSnippetText(String snippetText) {
		this.snippetText = snippetText;
	}

	public ArrayList<Sentence> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(ArrayList<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}
	
	

}
