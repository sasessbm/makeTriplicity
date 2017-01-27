package makeTriplicity;

import java.util.ArrayList;

public class MakeTriplicity {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		
		//recordList取得(recordの生成)
		recordList = GetRecordList.getRecordList(recordNum);
		
		//リスト単位
		for(Record record : recordList){
			
			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String medicineName = record.getMedicineName();
			
			//"。"が無いスニペットは対象としない
			if(!snippetText.contains("。")){ continue; }
			
			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(medicineName)){ continue; }
			
			ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
			
			//SentenceList取得＆前処理
			sentenceList = Preprocessor.getSentenceList(snippetText);
			
			//文単位
			for(Sentence sentence : sentenceList){
				Preprocessor.replaceMedicineName(sentence);
				Preprocessor.deleteParentheses(sentence);
			}
			
			//SentenceListセット
			snippet.setSentenceList(sentenceList);
			
			
			for(Sentence sentence : sentenceList){
				//続きはここから
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
				for(Sentence sentence : sentenceList){
					System.out.println(sentence.getSentenceText());
				}
			}else{
				System.out.println("null");
			}
			
			
		}

	}

}
