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
		GetTriplePhraseListFirst.phraseList = phraseList;

		for(Phrase phrase : phraseList){

			triplePhrase = new TriplePhrase();
			String phraseText = phrase.getPhraseText();
			if(!phraseText.contains("TARGETMEDICINE")){ continue; }
			//System.out.println(phraseText);

			//対象薬剤名のすぐ後ろに手がかり語があるか探索
			int keywordIndex = isExistKeyword(phrase.getMorphemeList());
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
			int medicineIndex = -1;

			for(int i = 0; i<morphemeList.size(); i++){
				if(!morphemeList.get(i).getMorphemeText().contains("TARGETMEDICINE")){ continue; }
				medicineIndex = i;
				break;
			}

			if(medicineIndex == -1){ continue; }

			//同じ文節内にある
			if(keywordIndex > 0){
				if((keywordIndex - 1) != medicineIndex){ continue; }
				//System.out.println("自身のIDを渡す");
				//自身のIDを渡す
				judgeKeywordPhrase(phrase.getId());
			}

			//同じ文節内にはないが、「対象薬剤名＋助詞」になっている
			else{
				if(morphemeList.size() <= medicineIndex + 1){ continue; }
				//if(!morphemeList.get(medicineIndex + 1).getPartOfSpeech().equals("助詞")){ continue; }
				if(!morphemeList.get(morphemeList.size()-1).getPartOfSpeech().equals("助詞")){ continue; }
				//係り先番号を渡す
				//System.out.println("係り先番号を渡す");
				//System.out.println(phrase.getPhraseText());
				judgeKeywordPhrase(phrase.getDependencyIndex());
			}

			if(triplePhrase.getEffectPhrase() == null || triplePhrase.getTargetPhraseList() == null){
				//System.out.println("continue");
				continue;
			}

			triplePhraseList.add(triplePhrase);
		}
		
		return triplePhraseList;
	}

	//手がかり語の位置を探索
	public static int isExistKeyword(ArrayList<Morpheme> morphemeList){

		int keywordIndex = -1;
		int morphemeIndex = -1;

		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();
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

			if(phrase.getId() != dependencyIndex){ continue; }
			if(isExistKeyword(phrase.getMorphemeList()) != -1){

				//一番最後の文節が、格助詞または接続助詞か確認
				String partOfSpeechDetails = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
				if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
					//phrase.setPhraseType("Keyword");
					//System.out.println("手がかり語存在文節:"+ phrase.getPhraseText());
					judgeEffectPhrase(phrase.getDependencyIndex());
					break;
				}
				break;
			}

		}

	}

	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int dependencyIndex){

		//ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		for(Phrase phrase : phraseList){
			if(phrase.getId() == dependencyIndex){
				//effectPhraseList.add(phrase);
				//triplePhrase.setEffectPhraseList(effectPhraseList);
				triplePhrase.setEffectPhrase(phrase);
				//System.out.println("「効果」要素存在文節:"+ phrase.getPhraseText());
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
			Phrase phrase = phraseList.get(currentIndex);

			if(phraseList.get(currentIndex).getDependencyIndex() == id || findPhrase){

				String lastMorphemeText = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1)
						.getMorphemeText();

				if(findPhrase){
					if(lastMorphemeText.equals("の")){
						targetPhraseList.add(phrase);
						//targetPhraseText = phraseList.get(currentIndex).getPhraseText() + targetPhraseText;
					}else{
						Collections.reverse(targetPhraseList);
						triplePhrase.setTargetPhraseList(targetPhraseList);
						break;
					}
				}else{
					if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") || lastMorphemeText.equals("を")){
						findPhrase = true;
						targetPhraseList.add(phrase);
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
