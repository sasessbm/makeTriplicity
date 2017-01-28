package makeTriplicity;

import java.util.ArrayList;

import test.CaboChaTest3;

public class MakeTriplicity {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		
		//recordList取得(recordの生成)
		recordList = GetRecordList.getRecordList(recordNum);
		
		//レコード単位
		for(Record record : recordList){
			
			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String medicineName = record.getMedicineName();
			
			ArrayList<String> sentenceTextList = new ArrayList<String>();
			
			//"。"が無いスニペットは対象としない
			if(!snippetText.contains("。")){ continue; }
			
			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(medicineName)){ continue; }
			
			//SentenceList取得
			sentenceTextList = Preprocessor.getSentenceTextList(snippetText);
			
			//文単位
			for(String sentenceText : sentenceTextList){
				
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				
				//前処理
				sentenceText = Preprocessor.replaceMedicineName(sentenceText);
				sentenceText = Preprocessor.deleteParentheses(sentenceText);
				
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = GetPhraseList.GetSyntaxAnalysisResultXml(sentenceText);
				
				
			}
			
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
			
			if(snippet.getSentenceList() != null){
				for(Sentence sentence : snippet.getSentenceList()){
					System.out.println(sentence.getSentenceText());
				}
			}else{
				System.out.println("null");
			}
			
			
		}

	}

}
