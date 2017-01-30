package makeTriplicity;

import java.util.ArrayList;

public class TriplePhrase {
	
	private String medicineName;
	private ArrayList<Phrase> targetPhraseList;
	private ArrayList<Phrase> effectPhraseList;
	

	public TriplePhrase() {
		this.medicineName = "";
		this.targetPhraseList = new ArrayList<Phrase>();
		this.effectPhraseList = new ArrayList<Phrase>();
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


	public ArrayList<Phrase> getEffectPhraseList() {
		return effectPhraseList;
	}


	public void setEffectPhraseList(ArrayList<Phrase> effectPhraseList) {
		this.effectPhraseList = effectPhraseList;
	}
	
	
	
	
	
}
