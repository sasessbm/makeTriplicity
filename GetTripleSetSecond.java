package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetSecond {

	public static ArrayList<TripleSet> getTripleSetList(TriplePhrase triplePhrase) {

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		TripleSet tripleSet = new TripleSet();
		String medicineName = triplePhrase.getMedicineName();
		String target = "";
		String effect = "";

		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
		targetPhraseList = triplePhrase.getTargetPhraseList();
		for(Phrase targetPhrase : targetPhraseList){
			target += targetPhrase.getPhraseText();
//			for(Morpheme morpheme : targetPhrase.getMorphemeList()){
//				target += morpheme.getMorphemeText();
//			}
		}

		//target = triplePhrase.getTargetPhrase().getPhraseText();
		effect = triplePhrase.getEffectPhrase().getPhraseText();

		tripleSet.setMedicineName(medicineName);
		tripleSet.setTarget(target.replace("、", ""));
		tripleSet.setEffect(effect.replace("、", ""));
		tripleSetList.add(tripleSet);

		return tripleSetList;
	}

}
