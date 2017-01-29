package makeTriplicity;

import java.util.ArrayList;
import java.util.ListIterator;

public class GetTriplePhase {
	
	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<String> keywordList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword.txt");
	private static TriplePhrase triplePhrase;
	//private static ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();

	public static TriplePhrase getTriplePhrase(ArrayList<Phrase> phraseList) {
		
		GetTriplePhase.phraseList = new ArrayList<Phrase>();
		triplePhrase = new TriplePhrase("","","");
		GetTriplePhase.phraseList = phraseList;
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
		
		return triplePhrase;

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
	
	//「手がかり語」要素存在文節探索
	public static void serchKeywordPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				if(isExistKeyword(phrase.getPhraseText())){
					
					//一番最後の文節が、格助詞または接続助詞か確認
					String partOfSpeechDetails = phrase.getMorphemeList()
												.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
					if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
						phrase.setPhraseType("Keyword");
						serchEffectPhrase(phrase.getDependencyIndex());
						break;
					}
				}
			}
		}
		
	}
	
	//「効果」要素存在文節探索
	public static void serchEffectPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				triplePhrase.setEffectPhrase(phrase.getPhraseText());
				//phrase.setPhraseType("Effect");
				serchTargetPhrase(phrase.getId());
				break;
			}
		}
		
	}
	
	//「対象」要素存在文節探索
	public static void serchTargetPhrase(int id){
		
		//逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			if(phraseList.get(phraseList.size()-i).getDependencyIndex() == id){
				String lastMorphemeText = phraseList.get(phraseList.size()-i).getMorphemeList()
										  .get(phraseList.get(phraseList.size()-i).getMorphemeList().size()-1)
										  .getMorphemeText();
				
				if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") || lastMorphemeText.equals("を")){
					triplePhrase.setTargetPhrase(phraseList.get(phraseList.size()-i).getPhraseText());
					//phraseList.get(phraseList.size()-i).setPhraseType("Target");
					break;
				}
			}
		}
		
	}

}
