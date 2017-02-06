package makeTriplicity;

import java.util.ArrayList;

import test.CaboChaTest3;

public class MakeTripleSet {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		//int recordNum = 100;
		int startRecordNum = 0;
		int endRecordNum = 1000;
		int getTripleSetNum = 0;

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

			//			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			//System.out.println("Id:" +record.getId());
			//System.out.println("スニペット:" + snippet.getSnippetText());
			//			System.out.println("薬剤名:" +record.getMedicineName());
			//			System.out.println("病名:" +record.getDiseaseName());
			//			System.out.println("性別:" +record.getSex());
			//			System.out.println("ブログタイトル:" +record.getTitle_blog());
			//			System.out.println("ブログ記事タイトル:" +record.getTitle_blogArticle());
			//			System.out.println("ブログ記事ＵＲＬ:" +record.getUrl_blogArticle());
			//			System.out.println("年齢:" +record.getAge());
			//			System.out.println("\r\n以下、1文単位");
			int indexSentence = 0;

			//ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
			String sentenceTextBefore = "";
			//文単位
			for(String sentenceText : sentenceTextList){
				//System.out.println("------------------------------------------------------------------------------------");
				indexSentence++;

				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){
					//System.out.println("\r\n文" + indexSentence + ":null");
					continue;
				}else{
					//System.out.println("\r\n文" + indexSentence + ":" + sentenceText);
				}
				
				sentenceTextBefore = sentenceText;

				//前処理
				sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
				sentenceText = Preprocessor.deleteParentheses(sentenceText);
				
				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){
					//System.out.println("\r\n文" + indexSentence + ":null");
					continue;
				}

				//System.out.println("\r\n文" + indexSentence + ":" + sentenceText);

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//phraseList取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				phraseList = XmlReader.GetPhraseList(xmlList);
				int indexPhrase = -1;
				//System.out.println("\r\n以下、文節単位");

				//文節単位
				//				for(Phrase phrase : phraseList){
				//					System.out.println("---------------------------------------------------");
				//					indexPhrase ++;
				//					System.out.println("\r\n文節" + indexPhrase);
				//					System.out.println(phrase.getPhraseText() + " DIndex:" + phrase.getDependencyIndex());
				//					System.out.println("\r\n以下、形態素単位");
				//					
				//					//形態素単位
				//					for(Morpheme morpheme : phrase.getMorphemeList()){
				//						System.out.println(morpheme.getMorphemeText() + " →→→ " + morpheme.getPartOfSpeech());
				//						
				//					}
				//				}

				//System.out.println("\r\nId:" +record.getId());
				//System.out.println("\r\n文:" + sentenceTextBefore);
				//ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListFirst.getTriplePhrase(phraseList);
				ArrayList<TriplePhrase> triplePhraseList = GetTriplePhraseListSecond.getTriplePhrase(phraseList);
				//TriplePhrase triplePhrase = GetTriplePhaseFirst.getTriplePhrase(phraseList);
				
				for(TriplePhrase triplePhrase : triplePhraseList){
					if(triplePhrase.getTargetPhraseList().size() == 0 || triplePhrase.getEffectPhraseList().size() == 0){
						continue;
					}
					
					//薬剤名セット
					triplePhrase.setMedicineName(medicineName);
					for(Phrase phrase : triplePhrase.getTargetPhraseList()){
						phrase.setPhraseText(phrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
						for(Morpheme morpheme : phrase.getMorphemeList()){
							morpheme.setMorphemeText(morpheme.getMorphemeText().replace("TARGETMEDICINE", medicineName));
						}
					}
					
					for(Phrase phrase : triplePhrase.getEffectPhraseList()){
						phrase.setPhraseText(phrase.getPhraseText().replace("TARGETMEDICINE", medicineName));
						for(Morpheme morpheme : phrase.getMorphemeList()){
							morpheme.setMorphemeText(morpheme.getMorphemeText().replace("TARGETMEDICINE", medicineName));
						}
					}
					//System.out.println("\r\n文:" + sentenceTextBefore);
					TripleSet tripleset = GetTripleSet.getTripleSet(triplePhrase);
					
					System.out.println("------------------------------------------------------------------------------------");
					System.out.print("\r\nId:" +record.getId());
					System.out.print(" | 性別:" +record.getSex());
					System.out.print(" | 年齢:" +record.getAge());
					System.out.println(" | 病名:" +record.getDiseaseName());
					System.out.println("\r\n文: " + sentenceTextBefore);
					System.out.println("薬剤名: " + triplePhrase.getMedicineName());
					System.out.println("対象: " + tripleset.getTarget());
					System.out.println("効果: " + tripleset.getEffect());
					getTripleSetNum++;

					//triplePhraseList.add(triplePhrase);

				}
				
				
			}


		}
		
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("三つ組を" + getTripleSetNum +"個取得できました！！");
		System.out.println("終了！！！");

	}

}
