package makeTriplicity;

public class TriplePhrase {
	
	private String medicineName;
	private String targetPhrase;
	private String effectPhrase;
	
	public TriplePhrase(String medicineName, String targetPhrase,
			String effectPhrase) {
		this.medicineName = medicineName;
		this.targetPhrase = targetPhrase;
		this.effectPhrase = effectPhrase;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getTargetPhrase() {
		return targetPhrase;
	}

	public void setTargetPhrase(String targetPhrase) {
		this.targetPhrase = targetPhrase;
	}

	public String getEffectPhrase() {
		return effectPhrase;
	}

	public void setEffectPhrase(String effectPhrase) {
		this.effectPhrase = effectPhrase;
	}

}
