package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetSecond {
	
	public static ArrayList<TripleSet> getTripleSetList(TriplePhrase triplePhrase) {
		
		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		TripleSet tripleSet = new TripleSet();
		String medicineName = triplePhrase.getMedicineName();
		String target = "";
		String effect = "";
		
		target = triplePhrase.getTargetPhrase().getPhraseText();
		effect = triplePhrase.getEffectPhrase().getPhraseText();
		
		//ArrayList<String> evalWordList = triplePhrase.getEffectPhraseList().get(0).getEvalWordList();
		
//		for(Phrase phrase : triplePhrase.getTargetPhraseList()){
//			
//			target = phrase.getPhraseText();
////			for(Morpheme morpheme : phrase.getMorphemeList()){
////				//targetMorphemeList.add(morpheme);
////				target += morpheme.getMorphemeText();
////			}
//		}
		
//		for(Phrase phrase : triplePhrase.getEffectPhraseList()){
//			TripleSet tripleSet = new TripleSet();
//			effect = phrase.getPhraseText();
//			
//			tripleSet.setMedicineName(medicineName);
//			tripleSet.setTarget(target.replace("、", ""));
//			tripleSet.setEffect(effect.replace("、", ""));
//			tripleSetList.add(tripleSet);
//		}
		
//		for(String evalWord : evalWordList){
//			TripleSet tripleSet = new TripleSet();
//			
//			effect = evalWord;
//			
//			tripleSet.setMedicineName(medicineName);
//			tripleSet.setTarget(target.replace("、", ""));
//			tripleSet.setEffect(effect.replace("、", ""));
//			tripleSetList.add(tripleSet);
//		}
		
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTarget(target.replace("、", ""));
		tripleSet.setEffect(effect.replace("、", ""));
		tripleSetList.add(tripleSet);
		
		return tripleSetList;
	}

}
