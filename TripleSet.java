package makeTriplicity;

public class TripleSet {
	
	String medicineName;
	private Element targetElement;
	private Element effectElement;


	public TripleSet() {
		
	}
	

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Element getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(Element targetElement) {
		this.targetElement = targetElement;
	}

	public Element getEffectElement() {
		return effectElement;
	}

	public void setEffectElement(Element effectElement) {
		this.effectElement = effectElement;
	}
	
}
