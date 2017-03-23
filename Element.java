package makeTriplicity;

import java.util.ArrayList;

public class Element {
	
	private String text;
	private ArrayList<Morpheme> morphemeList;
	
	public Element() {
		
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public ArrayList<Morpheme> getMorphemeList() {
		return morphemeList;
	}
	
	public void setMorphemeList(ArrayList<Morpheme> morphemeList) {
		this.morphemeList = morphemeList;
	}

}
