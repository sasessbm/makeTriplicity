package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetFirst {

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
				target += morpheme.getMorphemeText();
			}
		}
		
//		for(Phrase phrase : triplePhrase.getEffectPhrase()){
//			for(Morpheme morpheme : phrase.getMorphemeList()){
//				effectMorphemeList.add(morpheme);
//				effect += morpheme.getMorphemeText();
//			}
//		}
		
		//target = getAttribute(targetMorphemeList);
		//effect = getAttribute(effectMorphemeList);
		
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTarget(target.replace("、", ""));
		tripleSet.setEffect(effect.replace("、", ""));
		
		return tripleSet;
	}
	
	public static String getAttribute(ArrayList<Morpheme> morphemeList){
		
		String attribute = "";
		int denialIndex = 0;
		int morphemeIndex = 0;
		
		for(Morpheme morpheme : morphemeList){
			//System.out.println(morpheme.getMorphemeText());
			if(morpheme.getOriginalForm().equals("ない")){
				denialIndex ++;
			}
		}
		
		//System.out.println(denialIndex);
		
		for(Morpheme morpheme : morphemeList){
			morphemeIndex++;
			
//			if((morpheme.getPartOfSpeech().equals("助詞") || morpheme.getPartOfSpeech().equals("助動詞")) 
//					& !morpheme.getOriginalForm().equals("の")){ break; }
			
			if(morpheme.getPartOfSpeech().equals("助詞") 
					& !morpheme.getOriginalForm().equals("の")){ break; }
			
			if(morpheme.getPartOfSpeech().equals("動詞")){
				
				if(denialIndex % 2 == 1 || morphemeIndex != morphemeList.size()){
					attribute += morpheme.getMorphemeText(); 
				}else{
					//動詞は原形で取得
					attribute += morpheme.getOriginalForm();
				}
				 
			}else{
				attribute += morpheme.getMorphemeText(); 
			}
		}
		
//		if(denialIndex % 2 == 1){
//			attribute += "ない";
//		}
		
		return attribute;
	}

}
