package makeTriplicity;

import java.util.ArrayList;
import java.util.ListIterator;

public class GetTriplePhase {
	
	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<String> keywordList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword.txt");
	private static TriplePhrase triplePhrase;

	public static TriplePhrase getTriplePhrase(ArrayList<Phrase> phraseList) {
		
		GetTriplePhase.phraseList = new ArrayList<Phrase>();
		triplePhrase = new TriplePhrase("","","");
		GetTriplePhase.phraseList = phraseList;
		
		for(Phrase phrase : phraseList){
			String phraseText = phrase.getPhraseText();
			if(!phraseText.contains("TARGETMEDICINE")){ continue; }
			phrase.setPhraseType("Medicine");
			
			//対象薬剤名のすぐ後ろに手がかり語があるか探索
			int keywordIndex = isExistKeyword(phrase.getMorphemeList());
			if(keywordIndex != -1){
				if(phrase.getMorphemeList().get(keywordIndex-1).equals("TARGETMEDICINE")){
					//自身のIDを渡す
					judgeKeywordPhrase(phrase.getId());
				}
				
			}
			//係り先番号を渡す
			judgeKeywordPhrase(phrase.getDependencyIndex());
		}
		
		return triplePhrase;
	}
	
	//手がかり語の位置を探索
		public static int isExistKeyword(ArrayList<Morpheme> morphemeList){
			
			int keywordIndex = -1;
			int morphemeIndex = -1;
			
			for(Morpheme morpheme : morphemeList){
				morphemeIndex ++;
				String originalForm = morpheme.getOriginalForm();
				System.out.println("originalForm:" + originalForm);
				for(String keyword : keywordList){
					if(originalForm.equals(keyword)){
						keywordIndex = morphemeIndex;
					}
				}
			}
			return keywordIndex;
		}
	
	//「手がかり語」要素存在文節判定
	public static void judgeKeywordPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				if(isExistKeyword(phrase.getMorphemeList()) != -1){
					
					//一番最後の文節が、格助詞または接続助詞か確認
					String partOfSpeechDetails = phrase.getMorphemeList()
												.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
					if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
						//phrase.setPhraseType("Keyword");
						judgeEffectPhrase(phrase.getDependencyIndex());
						break;
					}
					break;
				}
			}
		}
		
	}
	
	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int dependencyIndex){
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				triplePhrase.setEffectPhrase(phrase.getPhraseText());
				//phrase.setPhraseType("Effect");
				judgeTargetPhrase(phrase.getId());
				break;
			}
		}
		
	}
	
	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int id){
		
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
