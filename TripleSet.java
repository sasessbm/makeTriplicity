package makeTriplicity;

public class TripleSet {
	
	private String medicineName;
	private String target;
	private String effect;
	
	
	public TripleSet(String medicineName, String target, String effect) {
		this.medicineName = medicineName;
		this.target = target;
		this.effect = effect;
	}


	public String getMedicineName() {
		return medicineName;
	}


	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}


	public String getTarget() {
		return target;
	}


	public void setTarget(String target) {
		this.target = target;
	}


	public String getEffect() {
		return effect;
	}


	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	
	

}