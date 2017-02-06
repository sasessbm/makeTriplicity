package makeTriplicity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class GetTriplePhraseListFirst {
	
	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<String> keywordList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword.txt");
	private static TriplePhrase triplePhrase;

	public static ArrayList<TriplePhrase> getTriplePhrase(ArrayList<Phrase> phraseList) {
		
		GetTriplePhraseListFirst.phraseList = new ArrayList<Phrase>();
		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
		
		triplePhrase = new TriplePhrase();
		GetTriplePhraseListFirst.phraseList = phraseList;
		
		for(Phrase phrase : phraseList){
			String phraseText = phrase.getPhraseText();
			if(!phraseText.contains("TARGETMEDICINE")){ continue; }
			phrase.setPhraseType("Medicine");
			
			//対象薬剤名のすぐ後ろに手がかり語があるか探索
			int keywordIndex = isExistKeyword(phrase.getMorphemeList());
//			System.out.println(keywordIndex);
//			System.out.println(phraseText);
//			System.out.println("keywordIndex"+keywordIndex);
			if(keywordIndex > 0){
				//System.out.println(phrase.getMorphemeList().get(keywordIndex-1).getMorphemeText());
				if(phrase.getMorphemeList().get(keywordIndex-1).getMorphemeText().equals("TARGETMEDICINE")){
					//System.out.println("自身のIDを渡す");
					//自身のIDを渡す
					judgeKeywordPhrase(phrase.getId());
				}
				
			}
			//係り先番号を渡す
			judgeKeywordPhrase(phrase.getDependencyIndex());
		}
		
		triplePhraseList.add(triplePhrase);
		return triplePhraseList;
	}
	
	//手がかり語の位置を探索
		public static int isExistKeyword(ArrayList<Morpheme> morphemeList){
			
			int keywordIndex = -1;
			int morphemeIndex = -1;
			
			for(Morpheme morpheme : morphemeList){
				morphemeIndex ++;
				String originalForm = morpheme.getOriginalForm();
				//System.out.println("originalForm:" + originalForm);
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
		
		ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				effectPhraseList.add(phrase);
				triplePhrase.setEffectPhraseList(effectPhraseList);
				//phrase.setPhraseType("Effect");
				judgeTargetPhrase(phrase.getId());
				break;
			}
		}
		
	}
	
	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int id){
		
		//String targetPhraseText = "";
		boolean findPhrase = false;
		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
		
		//逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int currentIndex = phraseList.size()-i;
			
			if(phraseList.get(currentIndex).getDependencyIndex() == id || findPhrase){
				
				String lastMorphemeText = phraseList.get(currentIndex).getMorphemeList()
										  .get(phraseList.get(currentIndex).getMorphemeList().size()-1)
										  .getMorphemeText();
				
				if(findPhrase){
					if(lastMorphemeText.equals("の")){
						targetPhraseList.add(phraseList.get(currentIndex));
						//targetPhraseText = phraseList.get(currentIndex).getPhraseText() + targetPhraseText;
					}else{
						Collections.reverse(targetPhraseList);
						triplePhrase.setTargetPhraseList(targetPhraseList);
						break;
					}
				}else{
					if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") || lastMorphemeText.equals("を")){
						findPhrase = true;
						targetPhraseList.add(phraseList.get(currentIndex));
						//targetPhraseText = phraseList.get(currentIndex).getPhraseText();
						
						//triplePhrase.setTargetPhrase(phraseList.get(currentIndex).getPhraseText());
						//phraseList.get(currentIndex).setPhraseType("Target");
						//break;
					}
				}
				
			}
		}
		
	}

}
