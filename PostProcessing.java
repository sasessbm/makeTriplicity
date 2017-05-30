package makeTriplicity;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostProcessing {

	public static final String MEDICINE = "MEDICINE";
	//public static TreeMap<Integer, String> medicineNameMap;

//	public static void main(String[] args) throws Exception {
//
//		String text = "))((）（）あ(ああ)あい（）うえお）あ（（（）()))";
//		String text2 = "あいうえおあああwああ";
//		String text3 = "かきくけこ";
//		String text4 = "あ";
//
//		String regex = "\\(|\\)|\\（|\\）";
//
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(text);
//
//		while(m.find()){
//			String matchstr = m.group();
//			text = deleteCharacter(text, matchstr);
//			System.out.println(text);
//		}
//
//		//		System.out.println("text=" + text);
//		//		System.out.println("text2=" + text2);
//		//		System.out.println("text3=" + text3);
//		//		System.out.println("text4=" + text4);
//
//	}

//	//薬剤名を戻す
	public static ArrayList<Phrase> restoreMedicineName
				(ArrayList<Phrase> phraseList, TreeMap<Integer, String> medicineNameMap){
		
		//ArrayList<Phrase> phraseListTemp = new ArrayList<Phrase>();
		
		//phraseListTemp = phraseList;

		for(Integer key : medicineNameMap.keySet()){

			String medicineName = medicineNameMap.get(key);

			for(Phrase phrase : phraseList){
				if(phrase.getPhraseText().contains(MEDICINE)){
					phrase.setPhraseText(phrase.getPhraseText().replaceFirst(MEDICINE, medicineName));
					for(Morpheme morpheme : phrase.getMorphemeList()){
						if(!morpheme.getMorphemeText().equals(MEDICINE)){ continue; }
						morpheme.setMorphemeText(medicineName);
						break;
					}
					break;
				}
			}
		}
		return phraseList;
	}
	
//	//薬剤名を戻す
//		public static ArrayList<Phrase> restoreMedicineName
//					(ArrayList<Phrase> phraseList, ArrayList<String> medicineNameInSentenceList){
//			
//			for(String medicineName : medicineNameInSentenceList){
//				
//				for(Phrase phrase : phraseList){
//					//Phrase restorePhrase = new Phrase();
//					String phraseText = phrase.getPhraseText();
//					
//					if(phraseText.contains(MEDICINE)){
//						phrase.setPhraseText(phraseText.replaceFirst(MEDICINE, medicineName));
//						//phrase.setPhraseText(phraseText.replaceFirst(MEDICINE, medicineName));
//						for(Morpheme morpheme : phrase.getMorphemeList()){
//							if(!morpheme.getMorphemeText().equals(MEDICINE)){ continue; }
//							morpheme.setMorphemeText(medicineName);
//							break;
//						}
//						break;
//					}
//				}
//				
//			}
//
//			return phraseList;
//		}

	//	public static Phrase restoreMedicineName(Phrase phrase, int medicineIndex){
	//		
	//		String phraseText = phrase.getPhraseText();
	//		String medicineName = medicineNameMap.get(medicineIndex);
	//		
	//		phrase.setPhraseText(phraseText.replaceFirst(MEDICINE, medicineName));
	//		
	//		for(Morpheme morpheme : phrase.getMorphemeList()){
	//			if(!morpheme.getMorphemeText().equals(MEDICINE)){ continue; }
	//			morpheme.setMorphemeText(medicineName);
	//			break;
	//		}
	//		
	//		return phrase;
	//	}

	public static TripleSet deleteParentheses(TripleSet tripleSet){

		Element targetElement = tripleSet.getTargetElement();
		Element effectElement = tripleSet.getEffectElement();

		String targetText = tripleSet.getTargetElement().getText();
		String effectText = tripleSet.getEffectElement().getText();

		String regex = "\\(|\\)|\\（|\\）|「|」|『|』";

		Pattern p = Pattern.compile(regex);
		Matcher targetMatcher = p.matcher(targetText);
		Matcher effectMatcher = p.matcher(effectText);

		while(targetMatcher.find()){
			String matchstr = targetMatcher.group();
			targetText = deleteCharacter(targetText, matchstr);
			//System.out.println(targetText);
		}
		while(effectMatcher.find()){
			String matchstr = effectMatcher.group();
			effectText = deleteCharacter(effectText, matchstr);
			//System.out.println(effectText);
		}

		targetElement.setText(targetText);
		effectElement.setText(effectText);

		tripleSet.setTargetElement(targetElement);
		tripleSet.setEffectElement(effectElement);

		return tripleSet;

	}

	//該当文字を文字列の前後から削除
	public static String deleteCharacter(String text, String character){

		while(text.substring(0, 1).equals(character)){
			text = text.substring(1 , text.length());
			if(text.length() == 0){ break; }
		}

		if(text.length() == 0){ return text; }

		while(text.substring(text.length()-1, text.length()).equals(character)){
			text = text.substring(0 , text.length() - 1);
			if(text.length() == 0){ break; }
		}

		return text;

	}

	//	//薬剤名置き換え
	//	public static TriplePhrase replaceMedicineName(TriplePhrase triplePhrase, String medicineName){
	//
	//		ArrayList<Phrase> targetPhraseList = triplePhrase.getTargetPhraseList();
	//		Phrase effectPhrase = triplePhrase.getEffectPhrase();
	//
	//		for(Phrase targetPhrase : targetPhraseList){
	//			if(targetPhrase.getPhraseText().contains(MEDICINE)){
	//				targetPhrase.setPhraseText(targetPhrase.getPhraseText().replace(MEDICINE, medicineName));
	//				for(Morpheme morpheme : targetPhrase.getMorphemeList()){
	//					if(!morpheme.getMorphemeText().contains(MEDICINE)){ continue; }
	//					morpheme.setMorphemeText(morpheme.getMorphemeText().replace(MEDICINE, medicineName));
	//				}
	//			}
	//		}
	//
	//		effectPhrase.setPhraseText(effectPhrase.getPhraseText().replace(MEDICINE, medicineName));
	//		for(Morpheme morpheme : effectPhrase.getMorphemeList()){
	//			if(!morpheme.getMorphemeText().contains(MEDICINE)){ continue; }
	//			morpheme.setMorphemeText(morpheme.getMorphemeText().replace(MEDICINE, medicineName));
	//		}
	//		return triplePhrase;
	//	}

}
