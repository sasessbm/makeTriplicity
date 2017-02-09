package makeTriplicity;

import java.util.ArrayList;

import test.CaboChaTest3;

public class MakeTripleSet {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		//int recordNum = 100;
		int startRecordNum = 830;
		int endRecordNum = 830;
		int getTripleSetNum = 0;
		int getSentenceNumOfTriple = 0;

		//recordList取得　(recordの生成)
		recordList = GetRecordList.getRecordList(startRecordNum, endRecordNum);

		//レコード単位
		for(Record record : recordList){

			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String medicineName = record.getMedicineName();

			//"。"が無いスニペットは対象としない
			if(!snippetText.contains("。")){ continue; }

			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(medicineName)){ continue; }

			//SentenceList取得
			ArrayList<String> sentenceTextList = new ArrayList<String>();
			sentenceTextList = Preprocessor.getSentenceTextList(snippetText);
			int indexSentence = 0;

			String sentenceTextBefore = "";
			//文単位
			for(String sentenceText : sentenceTextList){
				indexSentence++;

				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }
				
				//対象薬剤名を含まない文は対象としない
				if(!sentenceText.contains(medicineName)){ continue; }
				
				sentenceTextBefore = sentenceText;
				System.out.println("\r\n文: " + sentenceTextBefore);

				//前処理
				sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
				sentenceText = Preprocessor.deleteParentheses(sentenceText);
				
				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//phraseList取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				phraseList = XmlReader.GetPhraseList(xmlList);
				int indexPhrase = -1;
				//System.out.println("\r\n以下、文節単位");

//				//文節単位
//								for(Phrase phrase : phraseList){
//									System.out.println("---------------------------------------------------");
//									indexPhrase ++;
//									System.out.println("\r\n文節" + indexPhrase);
//									System.out.println(phrase.getPhraseText() + " DIndex:" + phrase.getDependencyIndex());
//									System.out.println("\r\n以下、形態素単位");
//									
//									//形態素単位
//									for(Morpheme morpheme : phrase.getMorphemeList()){
//										System.out.println(morpheme.getMorphemeText() + " →→→ " + morpheme.getPartOfSpeech());
//										
//									}
//								}

				//System.out.println("\r\nId:" +record.getId());
				//System.out.println("\r\n文:" + sentenceTextBefore);
				ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListFirst.getTriplePhrase(phraseList);
				//ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListSecond.getTriplePhrase(phraseList);
				//ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListTest.getTriplePhrase(phraseList);
				
				if(triplePhraseList.size() == 0){ continue; }
				getSentenceNumOfTriple++;
				
					System.out.println("------------------------------------------------------------------------------------");
					System.out.print("\r\nId:" +record.getId());
					System.out.print(" | 性別:" +record.getSex());
					System.out.print(" | 年齢:" +record.getAge());
					System.out.println(" | 病名:" +record.getDiseaseName());
					System.out.println("\r\n文: " + sentenceTextBefore);
				
				for(TriplePhrase triplePhrase : triplePhraseList){
					
					ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
					targetPhraseList = triplePhrase.getTargetPhraseList();
					
					//薬剤名セット
					triplePhrase.setMedicineName(medicineName);
					Phrase effectPhrase = triplePhrase.getEffectPhrase();
					for(Phrase targetPhrase : targetPhraseList){
						targetPhrase.setPhraseText(targetPhrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
					}
					
					effectPhrase.setPhraseText(effectPhrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
					ArrayList<TripleSet> tripleSetList = GetTripleSetSecond.getTripleSetList(triplePhrase);
					for(TripleSet tripleSet : tripleSetList){
						System.out.println("薬剤名: " + triplePhrase.getMedicineName());
						System.out.println("対象: " + tripleSet.getTarget());
						System.out.println("効果: " + tripleSet.getEffect());
						getTripleSetNum++;
					}
				}
				
			}
		}
		
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println(getSentenceNumOfTriple + "文から");
		System.out.println("三つ組を" + getTripleSetNum +"個取得できました！！");
		System.out.println("終了！！！");

	}

}
