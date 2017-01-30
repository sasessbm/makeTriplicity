package makeTriplicity;

import java.util.ArrayList;

public class Preprocessor {

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
	public static String replaceMedicineName(String sentenceText, String targetMediceneName){

		ArrayList<String> medicineNameList = new ArrayList<String>();
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
		
		if(sentenceText.contains(targetMediceneName)){
			sentenceText = sentenceText.replace(targetMediceneName,"TARGETMEDICINE");
		}

		for(String medicineNameInList : medicineNameList){
			if(sentenceText.contains(medicineNameInList)){
				sentenceText = sentenceText.replace(medicineNameInList,"OTHERMEDICINE");
			}
		}
		return sentenceText;
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
