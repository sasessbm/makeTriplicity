package makeTriplicity;

import java.util.ArrayList;

public class Preprocessor {

	public static ArrayList<Record> preprocessor(ArrayList<Record> recordList){
		
		ArrayList<String> sentenceList = new ArrayList<String>();
		
		for(Record record : recordList){
			
			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String medicineName = record.getMedicineName();
			
			//"。"が無いスニペットは対象としない
			if(!snippetText.contains("。")){ continue; }
			
			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(medicineName)){ continue; }
			
			//前処理
			sentenceList = getSentence(snippetText);
			sentenceList = replaceMedicineName(sentenceList);
			sentenceList = deleteParentheses(sentenceList);
			
			//sentenceListセット
			snippet.setSentenceList(sentenceList);
			
		}
		
		return recordList;
	}
	
	//文単位に区切る
	public static ArrayList<String> getSentence(String snippet){
		
		ArrayList<String> sentenceList = new ArrayList<String>();
		int indexStart = -1;
		int indexPeriod = -1;
		while (true){
			indexPeriod = snippet.indexOf("。", indexStart + 1);
			if(indexPeriod == -1){ break; }
			sentenceList.add(snippet.substring(indexStart + 1, indexPeriod));
			indexStart = indexPeriod;
		}
		return sentenceList;
	}
	
	//薬剤名を"MEDICINE"に置き換える
	public static ArrayList<String> replaceMedicineName(ArrayList<String> sentenceList){
		
		ArrayList<String> medicineNameList = new ArrayList<String>();
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
		String sentence = "";
		
		for(int countSentence = 0; countSentence < sentenceList.size(); countSentence++){
			sentence = sentenceList.get(countSentence);
			for(String medicineNameInList : medicineNameList){
				if(sentence.contains(medicineNameInList)){
					sentence = sentence.replace(medicineNameInList,"MEDICINE");
					sentenceList.set(countSentence, sentence);
				}
			}
		}
		
		return sentenceList;
	}
	
	//薬剤名を含まない()削除
	public static ArrayList<String> deleteParentheses(ArrayList<String> sentenceList){
		
		String sentence = "";
		String textInParentheses = "";
		int indexStart = -1;
		int indexParenthesesLeft = -1;
		int indexParenthesesRight = -1;
		
		for(int countSentence = 0; countSentence < sentenceList.size(); countSentence++){
			sentence = sentenceList.get(countSentence);
			
			//半角
			while (true){
				indexParenthesesLeft = sentence.indexOf("(", indexStart + 1);
				indexParenthesesRight = sentence.indexOf(")", indexStart + 1);
				if(indexParenthesesLeft == -1){ break; }
				if(indexParenthesesLeft < indexParenthesesRight){
					textInParentheses = sentence.substring(indexParenthesesLeft, indexParenthesesRight + 1);
					if(!textInParentheses.contains("MEDICINE")){
						sentence = sentence.replace(textInParentheses, "");
						sentenceList.set(countSentence, sentence);
					}
					indexStart = indexParenthesesRight;
				}
			}
			
			indexStart = -1;
			indexParenthesesLeft = -1;
			indexParenthesesRight = -1;
			
			//全角
			while (true){
				indexParenthesesLeft = sentence.indexOf("（", indexStart + 1);
				indexParenthesesRight = sentence.indexOf("）", indexStart + 1);
				if(indexParenthesesLeft == -1){ break; }
				if(indexParenthesesLeft < indexParenthesesRight){
					textInParentheses = sentence.substring(indexParenthesesLeft, indexParenthesesRight + 1);
					if(!textInParentheses.contains("MEDICINE")){
						sentence = sentence.replace(textInParentheses, "");
						sentenceList.set(countSentence, sentence);
					}
					indexStart = indexParenthesesRight;
				}
			}
			
		}
		
		return sentenceList; 
	}
	

}
