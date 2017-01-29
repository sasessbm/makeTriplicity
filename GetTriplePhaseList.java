package makeTriplicity;

import java.util.ArrayList;

public class GetTriplePhaseList {
	
	private static ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
	private static ArrayList<String> keywordList = new ArrayList<String>();

	public static void setPhraseList(ArrayList<Phrase> phraseList) {
		GetTriplePhaseList.phraseList = phraseList;
	}

	public static void setKeywordList(ArrayList<String> keywordList) {
		GetTriplePhaseList.keywordList = keywordList;
	}



	public static ArrayList<TriplePhrase> getTriplePhraseList() {
		
		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
		
		//String phraseText = "";
		//int dependencyIndex = -10;
		//boolean targetMedicineFlag = false;
		
		//手がかり語リスト取得
//		ArrayList<String> keywordList = new ArrayList<String>();
//		keywordList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword.txt");
		
		for(Phrase phrase : phraseList){
			if(!phrase.getPhraseText().contains("TARGETMEDICINE")){ continue; }
			phrase.setPhraseType("Medicine");
			serchKeywordPhrase(phrase.getDependencyIndex());
		}
		
		
//		for(Phrase phrase : phraseList){
//
//			phraseText = phrase.getPhraseText();
//
//			if(phrase.getDependencyIndex() == dependencyIndex){
//				if(isExistKeyword(phraseText, keywordList)){
//					phrase.setPhraseType("Keyword");
//				}
//			}
//
//			if(!phraseText.contains("TARGETMEDICINE")){ continue; }
//			//dependencyIndex = phrase.getDependencyIndex();
//			phrase.setPhraseType("Medicine");
//			//targetMedicineFlag = true;
//
//
//
//		}
		
		return triplePhraseList;

	}
	
	public static boolean isExistKeyword(String phrase){
		
		boolean isExist = false;
		
		for(String keyword : keywordList){
			if(phrase.contains(keyword)){
				isExist = true;
			}
		}
		return isExist;
	}
	
	public static void serchKeywordPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				if(isExistKeyword(phrase.getPhraseText())){
					String partOfSpeechDetails = phrase.getMorphemeList()
												.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
					if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
						phrase.setPhraseType("Keyword");
						serchEffectPhrase(phrase.getDependencyIndex());
					}
				}
			}
		}
		
	}
	
	public static void serchEffectPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				phrase.setPhraseType("Effect");
			}
		}
		
	}
	
	public static void serchTargetPhrase(int dependencyIndex){
		
	}

}
