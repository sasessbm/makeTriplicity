package makeTriplicity;

import java.util.ArrayList;

public class GetTripleSetSecond {
	
	private static ArrayList<String> evaldicList = 
			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\EVALDIC_ver1.01");
	
	public static ArrayList<TripleSet> getTripleSetList(TriplePhrase triplePhrase) {
		
		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		
		String medicineName = triplePhrase.getMedicineName();
		String target = "";
		String effect = "";
		
		ArrayList<String> evalWordList = triplePhrase.getEffectPhraseList().get(0).getEvalWordList();
		
		for(Phrase phrase : triplePhrase.getTargetPhraseList()){
			for(Morpheme morpheme : phrase.getMorphemeList()){
				//targetMorphemeList.add(morpheme);
				target += morpheme.getMorphemeText();
			}
		}
		
		for(String evalWord : evalWordList){
			TripleSet tripleSet = new TripleSet();
			
			effect = evalWord;
			
			tripleSet.setMedicineName(medicineName);
			tripleSet.setTarget(target.replace("、", ""));
			tripleSet.setEffect(effect.replace("、", ""));
			tripleSetList.add(tripleSet);
		}
		
		return tripleSetList;
	}

}
