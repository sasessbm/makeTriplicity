package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetFirst {

	public static TripleSet getTripleSet(TriplePhrase triplePhrase) {
		
		TripleSet tripleSet = new TripleSet();
		
		String medicineName = triplePhrase.getMedicineName();
		Element targetElement = new Element();
		Element effectElement = new Element();
		
		ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
		//ArrayList<Morpheme> effectMorphemeList = new ArrayList<Morpheme>();
		
		for(Phrase phrase : triplePhrase.getTargetPhraseList()){
			for(Morpheme morpheme : phrase.getMorphemeList()){
				targetMorphemeList.add(morpheme);
				//target += morpheme.getMorphemeText();
			}
		}
		
//		for(Phrase phrase : triplePhrase.getEffectPhrase()){
//			for(Morpheme morpheme : phrase.getMorphemeList()){
//				effectMorphemeList.add(morpheme);
//				effect += morpheme.getMorphemeText();
//			}
//		}
		
		targetElement = getElement(targetMorphemeList);
		effectElement.setText(triplePhrase.getEffectPhrase().getPhraseText());
		//effect = getAttribute(effectMorphemeList);
		
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTargetElement(targetElement);
		tripleSet.setEffectElement(effectElement);
		
		
		//tripleSet.setTarget(target.replace("、", ""));
		//tripleSet.setEffect(effect.replace("、", ""));
		
		return tripleSet;
	}
	
	public static Element getElement(ArrayList<Morpheme> morphemeList){
		
		Element element = new Element();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
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
			
			//助詞が出現
			if(morpheme.getPartOfSpeech().equals("助詞") 
					& !morpheme.getOriginalForm().equals("の")){ break; }
			
			if(morpheme.getPartOfSpeech().equals("動詞")){
				
				if(denialIndex % 2 == 1 || morphemeIndex != morphemeList.size()){
					text += morpheme.getMorphemeText(); 
					elementMorphemeList.add(morpheme);
					
				}else{
					//動詞は原形で取得
					text += morpheme.getOriginalForm();
					elementMorphemeList.add(morpheme);
				}
				 
			}else{
				text += morpheme.getMorphemeText(); 
				elementMorphemeList.add(morpheme);
			}
		}
		
//		if(denialIndex % 2 == 1){
//			attribute += "ない";
//		}
		
		
		element.setText(text);
		element.setMorphemeList(elementMorphemeList);
		
		
		return element;
	}

}
