package makeTriplicity;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostProcessing {
	
	public static void main(String[] args) throws Exception {
		
		String text = "))((）（）あ(ああ)あい（）うえお）あ（（（）()))";
		String text2 = "あいうえおあああwああ";
		String text3 = "かきくけこ";
		String text4 = "あ";
		
		String regex = "\\(|\\)|\\（|\\）";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		
		while(m.find()){
			String matchstr = m.group();
			text = deleteCharacter(text, matchstr);
			System.out.println(text);
		}
		
//		System.out.println("text=" + text);
//		System.out.println("text2=" + text2);
//		System.out.println("text3=" + text3);
//		System.out.println("text4=" + text4);
		
	}

	//対象でない薬剤名を戻す
	public static ArrayList<Phrase> restoreOtherMedicineName(ArrayList<Phrase> phraseList, 
			TreeMap<Integer, String> otherMedicineNameMap){

		for(Integer key : otherMedicineNameMap.keySet()){

			String otherMedicineName = otherMedicineNameMap.get(key);

			for(Phrase phrase : phraseList){
				if(phrase.getPhraseText().contains("OTHERMEDICINE")){
					phrase.setPhraseText(phrase.getPhraseText().replaceFirst("OTHERMEDICINE", otherMedicineName));
					for(Morpheme morpheme : phrase.getMorphemeList()){
						if(!morpheme.getMorphemeText().equals("OTHERMEDICINE")){ continue; }
						morpheme.setMorphemeText(otherMedicineName);
						break;
					}
					break;
				}
			}
		}
		return phraseList;
	}

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

}
