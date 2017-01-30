package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSet {

	public static TripleSet getTripleSet(TriplePhrase triplePhrase) {
		
		TripleSet tripleSet = new TripleSet();
		
		String medicineName = triplePhrase.getMedicineName();
		String target = "";
		String effect = "";
		
		ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
		ArrayList<Morpheme> effectMorphemeList = new ArrayList<Morpheme>();
		
		for(Phrase phrase : triplePhrase.getTargetPhraseList()){
			for(Morpheme morpheme : phrase.getMorphemeList()){
				targetMorphemeList.add(morpheme);
			}
		}
		
		for(Phrase phrase : triplePhrase.getEffectPhraseList()){
			for(Morpheme morpheme : phrase.getMorphemeList()){
				effectMorphemeList.add(morpheme);
			}
		}
		
		target = getAttribute(targetMorphemeList);
		effect = getAttribute(effectMorphemeList);
		
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTarget(target);
		tripleSet.setEffect(effect);
		
		return tripleSet;
	}
	
	public static String getAttribute(ArrayList<Morpheme> morphemeList){
		
		String attribute = "";
		
		for(Morpheme morpheme : morphemeList){
			if((morpheme.getPartOfSpeech().equals("助詞") || morpheme.getPartOfSpeech().equals("助動詞")) 
					& !morpheme.getOriginalForm().equals("の")){ break; }
			
			if(morpheme.getPartOfSpeech().equals("動詞")){
				//動詞は原形で取得
				attribute += morpheme.getOriginalForm(); 
			}else{
				attribute += morpheme.getMorphemeText(); 
			}
		}
		
		return attribute;
	}

}
