package makeTriplicity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.TreeMap;

public class GetTriplePhraseListFirst {

	public static final String MEDICINE = "MEDICINE";
	private static  ArrayList<Phrase> phraseList;
//	private static ArrayList<String> keywordList = 
//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword_extend.txt");
	private static ArrayList<String> keywordList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword_extend2.txt");
	private static TriplePhrase triplePhrase;

	public static ArrayList<TriplePhrase> getTriplePhrase
										(ArrayList<Phrase> phraseReplaceList, TreeMap<Integer, String> medicineNameMap) {

		GetTriplePhraseListFirst.phraseList = new ArrayList<Phrase>();
		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
		GetTriplePhraseListFirst.phraseList = PostProcessing.restoreMedicineName(phraseReplaceList, medicineNameMap);

		for(Phrase phrase : phraseReplaceList){

			String medicineNameTemp = "";
			triplePhrase = new TriplePhrase();
			String phraseText = phrase.getPhraseText();
			if(!phraseText.contains(MEDICINE)){ continue; }
			//System.out.println(phraseText);

			// 対象薬剤名のすぐ後ろに手がかり語があるか探索
			int keywordPlaceIndex = getKeywordPlaceIndex(phrase.getMorphemeList());
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
			int medicinePlaceIndex = -1;

			// 薬剤名の位置取得
			for(int i = 0; i<morphemeList.size(); i++){
				String morphemeText = morphemeList.get(i).getMorphemeText();
				if(!morphemeText.contains(MEDICINE)){ continue; }
				medicineNameTemp = morphemeText;
				medicinePlaceIndex = i;
				break;
			}

			if(medicinePlaceIndex == -1){ continue; }

			// 同じ文節内にある
			if(keywordPlaceIndex > 0){
				if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } // 隣り合っているか
				//System.out.println("自身のIDを渡す");
				// 自身のIDを渡す
				judgeKeywordPhrase(phrase.getId());
			}

			// 同じ文節内にはないが、「対象薬剤名＋助詞」になっている
			else{
				if(morphemeList.size() <= medicinePlaceIndex + 1){ continue; }
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
			
			triplePhrase.setMedicineName(medicineNameTemp);

			triplePhraseList.add(triplePhrase);
		}
		
		return triplePhraseList;
	}

	// 手がかり語の位置を探索
	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList){

		int keywordPlaceIndex = -1;
		int morphemeIndex = -1;

		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();
			for(String keyword : keywordList){
				if(originalForm.equals(keyword)){
					//System.out.println(keyword);
					keywordPlaceIndex = morphemeIndex;
				}
			}
		}
		return keywordPlaceIndex;
	}

	//「手がかり語」要素存在文節判定
	public static void judgeKeywordPhrase(int dependencyIndex){

		for(Phrase phrase : phraseList){

			if(phrase.getId() != dependencyIndex){ continue; }
			if(getKeywordPlaceIndex(phrase.getMorphemeList()) != -1){

				//一番最後の文節が、格助詞または接続助詞か確認
				String partOfSpeechDetails = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
				if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
					//System.out.println("手がかり語存在文節:"+ phrase.getPhraseText());
					judgeEffectPhrase(phrase.getDependencyIndex(), dependencyIndex);
					break;
				}
				break;
			}
		}
	}

	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int effectId, int keyId){

		int medicineNameIndex = -1;
		int serchIndex = -1;
		//ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		for(Phrase phrase : phraseList){
//			serchIndex = phrase.getPhraseText().indexOf(MEDICINE);
//			while(serchIndex > 0){
//				medicineNameIndex ++;
//				serchIndex = phrase.getPhraseText().indexOf(MEDICINE, serchIndex);
//				serchIndex ++;
//			}
			int phraseId = phrase.getId();
			if(phraseId == effectId){
				//effectPhraseList.add(phrase);
				//triplePhrase.setEffectPhraseList(effectPhraseList);
//				while(phrase.getPhraseText().contains(MEDICINE)){
//					phrase = PostProcessing.restoreMedicineName(phrase, medicineNameIndex);
//				}
				triplePhrase.setEffectPhrase(phrase);
				//System.out.println("「効果」要素存在文節:"+ phrase.getPhraseText());
				//phrase.setPhraseType("Effect");
				judgeTargetPhrase(effectId, keyId);
				break;
			}
		}

	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int effectId, int keyId){

		//String targetPhraseText = "";
		boolean findPhrase = false;
		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();

		// 逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int currentIndex = phraseList.size()-i;
			Phrase phrase = phraseList.get(currentIndex);
			int phraseDependencyIndex = phrase.getDependencyIndex();
			int phraseId = phrase.getId();
			
			//「手がかり語」文節まで到達した時
			if(phraseId == keyId){ 
				if(targetPhraseList.size() != 0){
					Collections.reverse(targetPhraseList);
					triplePhrase.setTargetPhraseList(targetPhraseList);
				}
				break; 
			} 

			if(phraseDependencyIndex == effectId || findPhrase){

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
