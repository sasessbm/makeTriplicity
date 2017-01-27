package makeTriplicity;

import java.util.ArrayList;

public class Preprocessor {

	//文単位に区切る & sentenceオブジェクト生成
	public static ArrayList<Sentence> getSentenceList(String snippetText){

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		int indexStart = -1;
		int indexPeriod = -1;
		while (true){
			indexPeriod = snippetText.indexOf("。", indexStart + 1);
			if(indexPeriod == -1){ break; }
			Sentence sentence = new Sentence(snippetText.substring(indexStart + 1, indexPeriod));
			sentenceList.add(sentence);
			indexStart = indexPeriod;
		}
		return sentenceList;
	}

	//薬剤名を"MEDICINE"に置き換える
	public static void replaceMedicineName(Sentence sentence){

		ArrayList<String> medicineNameList = new ArrayList<String>();
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

		String sentenceText = sentence.getSentenceText();

		for(String medicineNameInList : medicineNameList){
			if(sentenceText.contains(medicineNameInList)){
				sentenceText = sentenceText.replace(medicineNameInList,"MEDICINE");
				sentence.setSentenceText(sentenceText);
			}
		}
	}

	//薬剤名を含まない()削除
	public static void deleteParentheses(Sentence sentence){

		String textInParentheses = "";
		int indexStart = -1;
		int indexParenthesesLeft = -1;
		int indexParenthesesRight = -1;
		String sentenceText = sentence.getSentenceText();

		//半角
		while (true){
			indexParenthesesLeft = sentenceText.indexOf("(", indexStart + 1);
			indexParenthesesRight = sentenceText.indexOf(")", indexStart + 1);
			if(indexParenthesesLeft == -1){ break; }
			if(indexParenthesesLeft < indexParenthesesRight){
				textInParentheses = sentenceText.substring(indexParenthesesLeft, indexParenthesesRight + 1);

				if(!textInParentheses.contains("MEDICINE")){
					sentenceText = sentenceText.replace(textInParentheses, "");
					sentence.setSentenceText(sentenceText);
				}
				indexStart = indexParenthesesRight;
			}
		}

		//全角
		while (true){
			indexParenthesesLeft = sentenceText.indexOf("（", indexStart + 1);
			indexParenthesesRight = sentenceText.indexOf("）", indexStart + 1);
			if(indexParenthesesLeft == -1){ break; }
			if(indexParenthesesLeft < indexParenthesesRight){
				textInParentheses = sentenceText.substring(indexParenthesesLeft, indexParenthesesRight + 1);

				if(!textInParentheses.contains("MEDICINE")){
					sentenceText = sentenceText.replace(textInParentheses, "");
					sentence.setSentenceText(sentenceText);
				}
				indexStart = indexParenthesesRight;
			}
		}

	}


}
