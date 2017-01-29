package makeTriplicity;

import java.util.ArrayList;

import test.CaboChaTest3;

public class MakeTriplicity {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		
		//recordList取得　(recordの生成)
		recordList = GetRecordList.getRecordList(recordNum);
		
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
			
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			System.out.println("Id:" +record.getId());
			System.out.println("スニペット:" + snippet.getSnippetText());
			System.out.println("薬剤名:" +record.getMedicineName());
			System.out.println("病名:" +record.getDiseaseName());
			System.out.println("性別:" +record.getSex());
			System.out.println("ブログタイトル:" +record.getTitle_blog());
			System.out.println("ブログ記事タイトル:" +record.getTitle_blogArticle());
			System.out.println("ブログ記事ＵＲＬ:" +record.getUrl_blogArticle());
			System.out.println("年齢:" +record.getAge());
			System.out.println("\r\n以下、1文単位");
			int indexSentence = 0;
			
			ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
			
			//文単位
			for(String sentenceText : sentenceTextList){
				System.out.println("------------------------------------------------------------------------------------");
				indexSentence++;
				
				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){
					System.out.println("\r\n文" + indexSentence + ":null");
					continue;
				}else{
					System.out.println("\r\n文" + indexSentence + ":" + sentenceText);
				}
				
				//前処理
				sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
				sentenceText = Preprocessor.deleteParentheses(sentenceText);
				
				System.out.println("\r\n文" + indexSentence + ":" + sentenceText);
				
				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);
				
				//phraseList取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				phraseList = XmlReader.GetPhraseList(xmlList);
				int indexPhrase = -1;
				System.out.println("\r\n以下、文節単位");
				
				//文節単位
				for(Phrase phrase : phraseList){
					System.out.println("---------------------------------------------------");
					indexPhrase ++;
					System.out.println("\r\n文節" + indexPhrase);
					System.out.println(phrase.getPhraseText() + " DIndex:" + phrase.getDependencyIndex());
					System.out.println("\r\n以下、形態素単位");
					
					//形態素単位
					for(Morpheme morpheme : phrase.getMorphemeList()){
						System.out.println(morpheme.getMorphemeText() + " →→→ " + morpheme.getPartOfSpeech());
						
					}
				}
				
				TriplePhrase triplePhrase = GetTriplePhase.getTriplePhrase(phraseList);
				triplePhrase.setMedicineName(medicineName);
				System.out.println("薬剤名:" + triplePhrase.getMedicineName());
				System.out.println("対象要素存在文節:" + triplePhrase.getTargetPhrase());
				System.out.println("効果要素存在文節:" + triplePhrase.getEffectPhrase());
				
				triplePhraseList.add(triplePhrase);
				
			}
			
			
		}

	}

}
