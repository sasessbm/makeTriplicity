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
	public static String replaceMedicineName(String sentenceText){

		ArrayList<String> medicineNameList = new ArrayList<String>();
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

		for(String medicineNameInList : medicineNameList){
			if(sentenceText.contains(medicineNameInList)){
				sentenceText = sentenceText.replace(medicineNameInList,"MEDICINE");
			}
		}
		return sentenceText;
	}

	//薬剤名を含まない()削除
	public static String deleteParentheses(String sentenceText){

		String textInParentheses = "";
		int indexStart = -1;
		int indexParenthesesLeft = -1;
		int indexParenthesesRight = -1;

		//半角
		while (true){
			indexParenthesesLeft = sentenceText.indexOf("(", indexStart + 1);
			indexParenthesesRight = sentenceText.indexOf(")", indexStart + 1);
			if(indexParenthesesLeft == -1){ break; }
			if(indexParenthesesLeft < indexParenthesesRight){
				textInParentheses = sentenceText.substring(indexParenthesesLeft, indexParenthesesRight + 1);

				if(!textInParentheses.contains("MEDICINE")){
					sentenceText = sentenceText.replace(textInParentheses, "");
				}
				indexStart = indexParenthesesRight;
			}
		}
		
		textInParentheses = "";
		indexStart = -1;
		indexParenthesesLeft = -1;
		indexParenthesesRight = -1;

		//全角
		while (true){
			indexParenthesesLeft = sentenceText.indexOf("（", indexStart + 1);
			indexParenthesesRight = sentenceText.indexOf("）", indexStart + 1);
			if(indexParenthesesLeft == -1){ break; }
			if(indexParenthesesLeft < indexParenthesesRight){
				textInParentheses = sentenceText.substring(indexParenthesesLeft, indexParenthesesRight + 1);

				if(!textInParentheses.contains("MEDICINE")){
					sentenceText = sentenceText.replace(textInParentheses, "");
				}
				indexStart = indexParenthesesRight;
			}
		}
		
		return sentenceText;

	}


}
