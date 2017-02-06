package makeTriplicity;

import java.util.ArrayList;
import java.util.Collections;

public class GetTriplePhraseListSecond {

	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<String> evaldicList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\EVALDIC_ver1.01");
	private static TriplePhrase triplePhrase;

	public static ArrayList<TriplePhrase> getTriplePhrase(ArrayList<Phrase> phraseList) {

		GetTriplePhraseListSecond.phraseList = new ArrayList<Phrase>();
		GetTriplePhraseListSecond.phraseList = phraseList;

		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
		triplePhrase = new TriplePhrase();
		ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();

		for(int phraseIndex = 1; phraseIndex < phraseList.size(); phraseIndex++){

			ArrayList<Morpheme> morphemeList = phraseList.get(phraseIndex).getMorphemeList();

			for(int morphemeIndex = 0; morphemeIndex < morphemeList.size(); morphemeIndex++){
				for(String evalWord : evaldicList){

					//評価表現でない場合
					if(!morphemeList.get(morphemeIndex).getOriginalForm().contains(evalWord)){ continue; }
					
					phraseList.get(phraseIndex).setEvalWord(morphemeList.get(morphemeIndex).getMorphemeText());

					judgeTargetPhrase(phraseList.get(phraseIndex).getId());

				}

			}
			
			effectPhraseList.add(phraseList.get(phraseIndex));
			triplePhrase.setEffectPhraseList(effectPhraseList);

		}
		triplePhraseList.add(triplePhrase);

		return triplePhraseList;
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int id){

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
					}else{
						Collections.reverse(targetPhraseList);
						triplePhrase.setTargetPhraseList(targetPhraseList);
						break;
					}
				}else{
					if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
							|| lastMorphemeText.equals("を") || lastMorphemeText.equals("も")){
						findPhrase = true;
						targetPhraseList.add(phraseList.get(currentIndex));
					}
				}

			}
		}

	}


}
