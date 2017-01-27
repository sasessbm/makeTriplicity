package makeTriplicity;

import java.util.ArrayList;

public class MakeTriplicity {

	public static void main(String[] args) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		
		//recordList取得
		recordList = GetRecordList.getRecordList(recordNum);
		
		//前処理
		recordList = Preprocessor.preprocessor(recordList);
		
		for(Record record : recordList){
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			System.out.println("Id:" +record.getId());
			System.out.println("スニペット:" + record.getSnippet());
			System.out.println("薬剤名:" +record.getMedicineName());
			System.out.println("病名:" +record.getDiseaseName());
			System.out.println("性別:" +record.getSex());
			System.out.println("ブログタイトル:" +record.getTitle_blog());
			System.out.println("ブログ記事タイトル:" +record.getTitle_blogArticle());
			System.out.println("ブログ記事ＵＲＬ:" +record.getUrl_blogArticle());
			System.out.println("年齢:" +record.getAge());
			
			if(record.getSnippet().getSentenceList() != null){
				for(String sentence : record.getSnippet().getSentenceList()){
					System.out.println(sentence);
				}
			}else{
				System.out.println("null");
			}
			
			
		}

	}

}
