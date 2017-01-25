package makeTriplicity;

import java.util.ArrayList;

public class Preprocessor {

	public static void main(String[] args) throws Exception {
		String snippet = "...なくてぽよんとした感じです。 抜けないんです。 味覚障害は、１－２日ははっきりと、２－３日はちょっとねの不具合を感じます。 "
				+ "なので、タキソール後の食欲はやはりおちます。 病気して嗜好が変ったのがフルーツです。季節を感じたいと強く求めるようになりました。 上は宮崎のマンゴー"
				+ "......利用してしまいます。 今は暑いので、特に、すぐ乗りますね。 当初、よく病院ー駅ー会社を 徒歩＋電車で移動していたと信じられません。 "
				+ "タキソールは30％OFFで 元気は30%Plusで 記事URL コメント ペタ &amp;laquo; 夕張メロンの思い出 | 記事一覧 | カイシャのオモイヤ･･･ "
				+ "&amp;raquo; コメント ...";
		
		ArrayList<Record> recordList = new ArrayList<Record>();
		int recordNum = 10;
		recordList = GetRecordList.getRecordList(recordNum);
		for(int i = 0; i < recordList.size(); i++){
			
			System.out.println("-----------------------------------------------------------------------------------------------------------------------");
			System.out.println("Id:" +recordList.get(i).getId());
			System.out.println("スニペット:" + recordList.get(i).getSnippet());
			System.out.println("薬剤名:" +recordList.get(i).getMedicineName());
			System.out.println("病名:" +recordList.get(i).getDiseaseName());
			System.out.println("性別:" +recordList.get(i).getSex());
			System.out.println("ブログタイトル:" +recordList.get(i).getTitle_blog());
			System.out.println("ブログ記事タイトル:" +recordList.get(i).getTitle_blogArticle());
			System.out.println("ブログ記事ＵＲＬ:" +recordList.get(i).getUrl_blogArticle());
			System.out.println("年齢:" +recordList.get(i).getAge());
		}
		

	}

}
