package makeTriplicity;

import java.util.ArrayList;

public class Phrase {
	
	private String phraseText;
	private int dependencyIndex;
	private ArrayList<Morpheme> morphemeList;
	
	
	//コンストラクタ
	public Phrase(String phraseText, int dependencyIndex) {
		this.phraseText = phraseText;
		this.dependencyIndex = dependencyIndex;
	}
	
	
	public String getPhraseText() {
		return phraseText;
	}
	
	public void setPhraseText(String phraseText) {
		this.phraseText = phraseText;
	}
	
	public int getDependencyIndex() {
		return dependencyIndex;
	}
	
	public void setDependencyIndex(int dependencyIndex) {
		this.dependencyIndex = dependencyIndex;
	}
	
	public ArrayList<Morpheme> getMorphemeList() {
		return morphemeList;
	}
	
	public void setMorphemeList(ArrayList<Morpheme> morphemeList) {
		this.morphemeList = morphemeList;
	} 
	

}
