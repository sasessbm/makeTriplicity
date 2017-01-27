package makeTriplicity;

public class Morpheme {
	
	private String morphemeText;
	private String partOfSpeech;
	
	
	//コンストラクタ
	public Morpheme(String morphemeText, String partOfSpeech) {
		this.morphemeText = morphemeText;
		this.partOfSpeech = partOfSpeech;
	}

	public String getMorphemeText(){
		return morphemeText;
	}
	
	public void setMorphemeText(String morphemeText){
		this.morphemeText = morphemeText;
	}
	
	public String getPartOfSpeech(){
		return partOfSpeech;
	}
	
	public void setPartOfSpeech(String partOfSpeech){
		this.partOfSpeech = partOfSpeech;
	}
	
}
