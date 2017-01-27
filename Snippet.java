package makeTriplicity;

import java.util.ArrayList;

public class Snippet {
	
	private String snippetText;
	private ArrayList<String> sentenceList;
	
	public Snippet(String snippetText) {
		this.snippetText = snippetText;
	}

	public String getSnippetText() {
		return snippetText;
	}

	public void setSnippetText(String snippetText) {
		this.snippetText = snippetText;
	}

	public ArrayList<String> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(ArrayList<String> sentenceList) {
		this.sentenceList = sentenceList;
	}
	
	

}
