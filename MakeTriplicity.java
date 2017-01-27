package makeTriplicity;

import java.util.ArrayList;

public class MakeTriplicity {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		
		//recordList取得(recordの生成)
		recordList = GetRecordList.getRecordList(recordNum);
		
		for(Record record : recordList){
			
			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String medicineName = record.getMedicineName();
			
			ArrayList<String> sentenceList = new ArrayList<String>();
			
			//"。"が無いスニペットは対象としない
			if(!snippetText.contains("。")){ continue; }
			
			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(medicineName)){ continue; }
			
			//SentenceList取得＆前処理
			sentenceList = Preprocessor.getSentenceList(snippetText);
			sentenceList = Preprocessor.replaceMedicineName(sentenceList);
			sentenceList = Preprocessor.deleteParentheses(sentenceList);
			
			//SentenceListセット
			snippet.setSentenceList(sentenceList);
			
			for(Record record : recordList){
				
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
				for(String sentence : record.getSnippet().getSentenceList()){
					System.out.println(sentence);
				}
			}else{
				System.out.println("null");
			}
			
			
		}

	}

}
