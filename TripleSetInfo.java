package makeTriplicity;

public class TripleSetInfo {
	
	private int medicinePhraseId;
	private int targetPhraseId;
	private int effectPhraseId;
	
	public TripleSetInfo() {
		medicinePhraseId = -1;
		targetPhraseId = -1;
		effectPhraseId = -1;
	}
	
	public int getMedicinePhraseId() {
		return medicinePhraseId;
	}
	
	public void setMedicinePhraseId(int medicinePhraseId) {
		this.medicinePhraseId = medicinePhraseId;
	}
	
	public int getTargetPhraseId() {
		return targetPhraseId;
	}
	
	public void setTargetPhraseId(int targetPhraseId) {
		this.targetPhraseId = targetPhraseId;
	}
	
	public int getEffectPhraseId() {
		return effectPhraseId;
	}
	
	public void setEffectPhraseId(int effectPhraseId) {
		this.effectPhraseId = effectPhraseId;
	}

}
