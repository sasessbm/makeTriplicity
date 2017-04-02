package makeTriplicity;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Preprocessor {
	
	public static String deleteSpace(String sentenceText){
		
		//半角
		sentenceText = sentenceText.replace(" ", "");
		//全角
		sentenceText = sentenceText.replace("　", "");
		
		return sentenceText;
	}

	//文単位に区切る & sentenceオブジェクト生成
	public static ArrayList<String> getSentenceTextList(String snippetText){

		ArrayList<String> sentenceTextList = new ArrayList<String>();
		int indexStart = -1;
		int indexPeriod = -1;
		while (true){
			indexPeriod = snippetText.indexOf("。", indexStart + 1);
			if(indexPeriod == -1){ break; }
			//Sentence sentence = new Sentence(snippetText.substring(indexStart + 1, indexPeriod));
			sentenceTextList.add(snippetText.substring(indexStart + 1, indexPeriod));
			indexStart = indexPeriod;
		}

		return sentenceTextList;
	}

	//薬剤名を"MEDICINE"に置き換える
	public static String replaceMedicineName(String sentenceText, String targetMediceneName, 
														TreeMap<Integer, String> otherMedicineNameMap){

		if(sentenceText.contains(targetMediceneName)){
			sentenceText = sentenceText.replace(targetMediceneName,"TARGETMEDICINE");
		}
		
		for(Entry<Integer, String> map : otherMedicineNameMap.entrySet()){
			if(sentenceText.contains(map.getValue())){
				sentenceText = sentenceText.replace(map.getValue(),"OTHERMEDICINE");
			}
		}

		return sentenceText;
	}
	
	//対象でない薬剤名マップ取得
	public static TreeMap<Integer, String> getOtherMedicineNameMap(String sentenceText, String TargetMedicineName){
		TreeMap<Integer, String> otherMedicineNameMap = new TreeMap<Integer, String>();
		ArrayList<String> medicineNameList = new ArrayList<String>();
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
		
		for(String medicineNameInList : medicineNameList){
			if(medicineNameInList.equals(TargetMedicineName)){ continue; }
			int searchIndex = 0;
			if(sentenceText.contains(medicineNameInList)){
				searchIndex = sentenceText.indexOf(medicineNameInList, searchIndex);
				while(searchIndex != -1){
					otherMedicineNameMap.put(searchIndex, medicineNameInList);
					searchIndex = sentenceText.indexOf(medicineNameInList, searchIndex + 1);
				}
			}
		}
		return otherMedicineNameMap;
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

	//薬剤名を含まない()削除
	public static String deleteParentheses(String sentenceText){

		//半角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"(" , ")");
		//全角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"（" , "）");
		//半角＋全角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"(" , "）");
		//全角＋半角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"（" , ")");

		return sentenceText;

	}

	public static String deleteTextBetweenTwoCharacer(String sentenceText, String firstCharacter, String secondCharacter){

		String textBetweenTwoCharacter = "";
		int indexStart = -1;
		int indexFirstCharacter = -1;
		int indexSecondCharacter = -1;

		while (true){

			indexFirstCharacter = sentenceText.indexOf(firstCharacter, indexStart + 1);
			indexSecondCharacter = sentenceText.indexOf(secondCharacter, indexStart + 1);
			if(indexFirstCharacter == -1 || indexSecondCharacter == -1){ break; }
			if(indexFirstCharacter < indexSecondCharacter){
				textBetweenTwoCharacter = sentenceText.substring(indexFirstCharacter, indexSecondCharacter + 1);
				if(!textBetweenTwoCharacter.contains("MEDICINE")){
					sentenceText = sentenceText.replace(textBetweenTwoCharacter, "");
				}

			}
			indexStart = indexSecondCharacter;
		}
		return sentenceText;	
	}


}
