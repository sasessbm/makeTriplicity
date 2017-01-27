package makeTriplicity;

import java.util.ArrayList;

public class Preprocessor {

	public static void main(String[] args) throws Exception {
		
		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		recordList = GetRecordList.getRecordList(recordNum);
		for(int countRecord = 0; countRecord < recordList.size(); countRecord++){
			
			String snippet = recordList.get(countRecord).getSnippet();
			if(!snippet.contains("。")){ continue; }
			String medicineName = recordList.get(countRecord).getMedicineName();
			ArrayList<String> sentenceList = new ArrayList<String>();
			sentenceList = getSentence(snippet,medicineName);
			sentenceList = replaceMedicineName(sentenceList);
			for(String sentence : sentenceList){
				System.out.println(sentence);
			}
			
			
			
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
//			System.out.println("Id:" +recordList.get(i).getId());
			//System.out.println("スニペット:" + snippet);
//			System.out.println("薬剤名:" +recordList.get(i).getMedicineName());
//			System.out.println("病名:" +recordList.get(i).getDiseaseName());
//			System.out.println("性別:" +recordList.get(i).getSex());
//			System.out.println("ブログタイトル:" +recordList.get(i).getTitle_blog());
//			System.out.println("ブログ記事タイトル:" +recordList.get(i).getTitle_blogArticle());
//			System.out.println("ブログ記事ＵＲＬ:" +recordList.get(i).getUrl_blogArticle());
//			System.out.println("年齢:" +recordList.get(i).getAge());
			
		}
		

	}
	
	//文単位に区切る
	public static ArrayList<String> getSentence(String snippet, String medicineName){
		
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
			
			while (true){
				indexParenthesesLeft = sentence.indexOf("(", indexStart + 1);
				indexParenthesesRight = sentence.indexOf(")", indexStart + 1);
				if(indexParenthesesLeft < indexParenthesesRight){
					textInParentheses = sentence.substring(indexParenthesesLeft, indexParenthesesRight + 1);
					if(!textInParentheses.contains("MEDICINE")){
						sentence = 
					}
				}
			}
			
//			while (true){
//				if(sentence.contains("(") || sentence.contains(")")){
//					indexParenthesesLeft = sentence.indexOf("(", indexStart + 1);
//					if(indexParenthesesLeft == -1){ break; }
//					indexParenthesesRight = sentence.indexOf(")", indexParenthesesLeft + 1);
//				}
//				break;
//			}
			
		}
		
		return sentenceList; 
	}
	

}
