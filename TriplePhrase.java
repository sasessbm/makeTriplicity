package makeTriplicity;

import java.util.ArrayList;

public class TriplePhrase {
	
	private String medicineName;
	private ArrayList<Phrase> targetPhraseList;
	private Phrase effectPhrase;
	
	public TriplePhrase() {
		
	}
	
	public String getMedicineName() {
		return medicineName;
	}
	
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	public ArrayList<Phrase> getTargetPhraseList() {
		return targetPhraseList;
	}

	public void setTargetPhraseList(ArrayList<Phrase> targetPhraseList) {
		this.targetPhraseList = targetPhraseList;
	}

	public Phrase getEffectPhrase() {
		return effectPhrase;
	}
	
	public void setEffectPhrase(Phrase effectPhrase) {
		this.effectPhrase = effectPhrase;
	}
	

	
	
	
	
	
}
