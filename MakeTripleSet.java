package makeTriplicity;

import java.util.ArrayList;

import test.CaboChaTest3;

public class MakeTripleSet {

	

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		//int recordNum = 100;
		int startRecordNum = 0;
		int endRecordNum = 1000;
		int tripleSetCount = 0;
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
				//System.out.println("\r\n文: " + sentenceTextBefore);

				//前処理
				sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
				sentenceText = Preprocessor.deleteParentheses(sentenceText);
				sentenceText = Preprocessor.deleteSpace(sentenceText);

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
				ArrayList<TriplePhrase> triplePhraseListFirst = GetTriplePhraseListFirst.getTriplePhrase(phraseList);
				//ArrayList<TriplePhrase> triplePhraseListSecond = GetTriplePhraseListSecond.getTriplePhrase(phraseList);
				//ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListTest.getTriplePhrase(phraseList);
				ArrayList<TripleSet> tripleSetListFirst = new ArrayList<TripleSet>();

				if(triplePhraseListFirst.size() != 0){

					for(TriplePhrase triplePhrase : triplePhraseListFirst){

						TripleSet tripleSet = makeTripleSet(triplePhrase, medicineName);

						//110番辞書フィルタ
						//if(!filterTarget(tripleSet)){ continue; }
						if(!Filtering.filterTarget(tripleSet)){ continue; }

						tripleSetListFirst.add(tripleSet);
					}

					if(tripleSetListFirst.size() != 0){
						display(record, sentenceTextBefore, tripleSetListFirst);
						System.out.println("\r\n提案手法から取得");
						getSentenceNumOfTriple++;
						tripleSetCount += tripleSetListFirst.size();
					}
				}

				ArrayList<TriplePhrase> triplePhraseListSecond = GetTriplePhraseListSecond.getTriplePhrase(phraseList);

				if(triplePhraseListSecond.size() == 0){ continue; }

				ArrayList<TripleSet> tripleSetListSecond = new ArrayList<TripleSet>();

				for(TriplePhrase triplePhrase : triplePhraseListSecond){

					TripleSet tripleSet = makeTripleSet(triplePhrase, medicineName);

					//110番辞書フィルタ
					//if(!filterTarget(tripleSet)){ continue; }
					if(!Filtering.filterTarget(tripleSet)){ continue; }

					tripleSetListSecond.add(tripleSet);
				}

				if(tripleSetListSecond.size() != 0){
					display(record, sentenceTextBefore, tripleSetListSecond);
					System.out.println("\r\nベースライン２から取得");
					getSentenceNumOfTriple++;
					tripleSetCount += tripleSetListSecond.size();
				}

			}
		}

		System.out.println("------------------------------------------------------------------------------------");
		System.out.println(getSentenceNumOfTriple + "文から");
		System.out.println("三つ組を" + tripleSetCount +"個取得できました！！");
		System.out.println("終了！！！");
	}

	//薬剤名置き換え
	public static TriplePhrase replaceMedicineName(TriplePhrase triplePhrase, String medicineName){

		ArrayList<Phrase> targetPhraseList = triplePhrase.getTargetPhraseList();
		Phrase effectPhrase = triplePhrase.getEffectPhrase();
		for(Phrase targetPhrase : targetPhraseList){
			for(Morpheme morpheme : targetPhrase.getMorphemeList()){
				if(!morpheme.getMorphemeText().equals("TARGETMEDICINE")){ continue; }
				morpheme.setMorphemeText(medicineName);
			}
			targetPhrase.setPhraseText(targetPhrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
		}
		effectPhrase.setPhraseText(effectPhrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
		return triplePhrase;
	}

	public static TripleSet makeTripleSet(TriplePhrase triplePhrase, String medicineName){

		//薬剤名セット
		triplePhrase.setMedicineName(medicineName);

		//薬剤名置き換え
		triplePhrase = replaceMedicineName(triplePhrase , medicineName);

		//三つ組取得
		//ArrayList<TripleSet> tripleSetList = GetTripleSetSecond.getTripleSetList(triplePhrase);
		TripleSet tripleSet = GetTripleSetFirst.getTripleSet(triplePhrase);

		return tripleSet;
	}

	//情報表示
	public static void display(Record record, String sentenceTextBefore, ArrayList<TripleSet> tripleSetList){

		//ステータス表示
		System.out.println("------------------------------------------------------------------------------------");
		System.out.print("\r\nId:" +record.getId());
		System.out.print(" | 性別:" +record.getSex());
		System.out.print(" | 年齢:" +record.getAge());
		System.out.println(" | 病名:" +record.getDiseaseName());
		System.out.println("\r\n文: " + sentenceTextBefore);

		//三つ組表示
		for(TripleSet tripleSet : tripleSetList){
			System.out.println("\r\n薬剤名: " + tripleSet.getMedicineName());
			System.out.println("対象: " + tripleSet.getTargetElement().getText());
			System.out.println("効果: " + tripleSet.getEffectElement().getText());
		}
	}
	
}
