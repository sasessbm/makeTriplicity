package makeTriplicity;

import java.util.ArrayList;
import java.util.Collections;

public class GetTriplePhraseListSecond {

	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<String> evaldicList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\EVALDIC_ver1.01");
	//private static TriplePhrase triplePhrase;

	public static ArrayList<TriplePhrase> getTriplePhrase(ArrayList<Phrase> phraseList) {

		GetTriplePhraseListSecond.phraseList = new ArrayList<Phrase>();
		GetTriplePhraseListSecond.phraseList = phraseList;

		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
		
		//ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		//ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();

		for(int phraseIndex = 1; phraseIndex < phraseList.size(); phraseIndex++){
			
			TriplePhrase triplePhrase = new TriplePhrase();
			Phrase phrase = phraseList.get(phraseIndex);

			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();

			for(int morphemeIndex = 0; morphemeIndex < morphemeList.size(); morphemeIndex++){
				for(String evalWord : evaldicList){
					
					Morpheme morpheme = morphemeList.get(morphemeIndex);

					//評価表現でない場合
					if(!morpheme.getOriginalForm().equals(evalWord)){ continue; }
					
					//phrase.setEvalWord(morpheme.getMorphemeText());
					//effectPhraseList.add(phrase);
					System.out.println("phrase" + phrase.getPhraseText());
					triplePhrase.setEffectPhrase(phrase);

					judgeTargetPhrase(phrase.getId(), triplePhrase);
					
				}
			}
			
			if(triplePhrase.getTargetPhrase() == null || triplePhrase.getEffectPhrase() == null){
				continue;
			}
			
			triplePhraseList.add(triplePhrase);
			//if(phrase.getEvalWordList().size()!=0){
				//effectPhraseList.add(phrase);
				//triplePhrase.setEffectPhraseList(effectPhraseList);
				//triplePhraseList.add(triplePhrase);
			//}
			
			
		}
		return triplePhraseList;
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int id, TriplePhrase triplePhrase){

		boolean findPhrase = false;
		//ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
		String targetText = "";

		//逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int currentIndex = phraseList.size()-i;
			Phrase phrase = phraseList.get(currentIndex);

			if(phrase.getDependencyIndex() == id || findPhrase){

				String lastMorphemeText = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1)
						.getMorphemeText();

				if(findPhrase){
					if(lastMorphemeText.equals("の")){
						//targetPhraseList.add(phraseList.get(currentIndex));
						targetText = phrase.getPhraseText() + targetText;
					}else{
						//Collections.reverse(targetPhraseList);
						phrase.setPhraseText(targetText);
						//targetPhraseList.add(phrase);
						triplePhrase.setTargetPhrase(phrase);
						break;
					}
				}else{
					if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
							|| lastMorphemeText.equals("を") || lastMorphemeText.equals("も")){
						findPhrase = true;
						//targetPhraseList.add(phraseList.get(currentIndex));
						targetText = phrase.getPhraseText();
					}
				}

			}
		}

	}


}
