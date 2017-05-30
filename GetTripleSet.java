package makeTriplicity;

import java.util.ArrayList;
import java.util.Collections;

public class GetTripleSet {
	
	public static ArrayList<TripleSet> getTripleSetList
				(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList) {

		ArrayList<TripleSet> tripleSetlist = new ArrayList<TripleSet>();
		
		for(TripleSetInfo tripleSetInfo : tripleSetInfoList){
			TripleSet tripleSet = new TripleSet();
			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
			int effectPhraseId = tripleSetInfo.getEffectPhraseId();
			
			ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
			ArrayList<Morpheme> effectMorphemeList = new ArrayList<Morpheme>();
			Element targetElement = new Element();
			Element effectElement = new Element();
			int searchIndex = 0;
			
			for(Morpheme morpheme : phraseList.get(medicinePhraseId).getMorphemeList()){
				if(!morpheme.getPartOfSpeech().equals("名詞")){ continue; }
				for(String medicineNameInList : medicineNameList){
					if(morpheme.getMorphemeText().equals(medicineNameInList)){
						
					}
				}
			}
			
			// 対象要素の形態素リスト取得
			Phrase Phrase = phraseList.get(targetPhraseId - searchIndex);
			ArrayList<Morpheme> morphemeList = Phrase.getMorphemeList();
			while(true){
				Collections.reverse(morphemeList);
				targetMorphemeList.addAll(morphemeList);
				searchIndex++;
				Phrase = phraseList.get(targetPhraseId - searchIndex);
				morphemeList = Phrase.getMorphemeList();
				if(!morphemeList.get(morphemeList.size()-1).equals("の")){ break; }
			}
			Collections.reverse(targetMorphemeList);
			
			// 効果要素の形態素リスト取得
			for(Morpheme morpheme : phraseList.get(effectPhraseId).getMorphemeList()){
				effectMorphemeList.add(morpheme);
			}
			
			// 要素取得
			targetElement = getElement(targetMorphemeList, 1);
			effectElement = getElement(effectMorphemeList, 2);
			
			tripleSet.setMedicineName(medicineName);
			tripleSet.setTargetElement(targetElement);
			tripleSet.setEffectElement(effectElement);
			tripleSetlist.add(tripleSet);
		}
		
		return tripleSetlist;
		
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
