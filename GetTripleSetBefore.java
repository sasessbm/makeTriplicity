package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetBefore {

	public static TripleSet getTripleSet(TriplePhrase triplePhrase) {

		TripleSet tripleSet = new TripleSet();

		String medicineName = triplePhrase.getMedicineName();
		Element targetElement = new Element();
		Element effectElement = new Element();

		ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
		ArrayList<Morpheme> effectMorphemeList = new ArrayList<Morpheme>();

		for(Phrase phrase : triplePhrase.getTargetPhraseList()){
			for(Morpheme morpheme : phrase.getMorphemeList()){
				targetMorphemeList.add(morpheme);
			}
		}

		for(Morpheme morpheme : triplePhrase.getEffectPhrase().getMorphemeList()){
			effectMorphemeList.add(morpheme);
		}

		targetElement = getElement(targetMorphemeList, 1);
		effectElement = getElement(effectMorphemeList, 2);
		//		String targetText = "";
		//		for(Morpheme targetMorpheme : targetMorphemeList){
		//			targetText += targetMorpheme.getMorphemeText();
		//		}
		//		targetElement.setText(targetText.replace("、", ""));
		//		targetElement.setMorphemeList(targetMorphemeList);
		//		effectElement.setText(triplePhrase.getEffectPhrase().getPhraseText().replace("、", ""));
		//		effectElement.setMorphemeList(effectMorphemeList);
		//		
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTargetElement(targetElement);
		tripleSet.setEffectElement(effectElement);

		return tripleSet;
	}

	public static Element getElement(ArrayList<Morpheme> morphemeList, int elementType){

		Element element = new Element();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		//int denialIndex = 0;
		boolean isVerb = false;


		//		for(Morpheme morpheme : morphemeList){
		//			//System.out.println(morpheme.getMorphemeText());
		//			if(morpheme.getOriginalForm().equals("ない")){
		//				denialIndex ++;
		//			}
		//		}

		for(Morpheme morpheme : morphemeList){

			//助詞が出現("の"以外) 
			if(morpheme.getPartOfSpeech().equals("助詞") & !morpheme.getOriginalForm().equals("の") ){ break; }

			if(morpheme.getOriginalForm().equals("、") || morpheme.getOriginalForm().equals("。")){ break; }
			//System.out.println(morpheme.getMorphemeText() + "→" + morpheme.getPartOfSpeechDetails());

			if(morpheme.getPartOfSpeech().equals("動詞")){

				//if(denialIndex % 2 == 1 || morphemeIndex != morphemeList.size()){
				//text += morpheme.getMorphemeText(); 
				//elementMorphemeList.add(morpheme);
				//}

				isVerb = true;

				elementMorphemeList.add(morpheme);

			}else{
				isVerb = false;
				elementMorphemeList.add(morpheme);
			}
		}

		for(int i = 0; i < elementMorphemeList.size(); i++){

			if(isVerb && i == elementMorphemeList.size() - 1 && elementType == 2){
				text += elementMorphemeList.get(i).getOriginalForm(); //「効果」要素で、最後が動詞だった時
			}else{
				text += elementMorphemeList.get(i).getMorphemeText();
			}
		}

		//		if(denialIndex % 2 == 1){
		//			text += "ない";
		//			//elementMorphemeList.add(morpheme);
		//		}

		element.setText(text);
		element.setMorphemeList(elementMorphemeList);

		return element;
	}

}
