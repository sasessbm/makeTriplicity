package makeTriplicity;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class GetRecordList {

	//プロキシ設定
	private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.nagaokaut.ac.jp", 8080));

	public static  ArrayList<Record> getRecordList(int startRecordNum, int endRecordNum) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		Connection con = null;
		//boolean[] randomNumArray = new boolean[endRecordNum - startRecordNum];
		//randomNumArray = makeRandomNumArray(recordNum, startRecordNum, endRecordNum);
		
		try {
			// JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// MySQLに接続
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobyo_db?useSSL=false", "root", "databasetest86");
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?useSSL=false", "root", "databasetest86");
			System.out.println("MySQLに接続できました。");

			//for(int id=startRecordNum; id<endRecordNum; id++){
				//if(!randomNumArray[id-startRecordNum]){ continue; }
				
				Statement stm = con.createStatement();
				String sql = "";
				Record record;
				Snippet snippet;
				//sql = "select * from tobyo_table_no_double where id = " + id;
				sql = "select * from tobyo_table_no_double where id >=" + startRecordNum + " and id <= " + endRecordNum
						+ " order by id";
				System.out.println(sql);
				ResultSet rs = stm.executeQuery(sql);

				while(rs.next()){
					snippet = new Snippet(rs.getString("snippet"));
					record = new Record(rs.getInt("id"), snippet, rs.getString("medicineName"), 
							rs.getString("diseaseName"), rs.getString("sex"),rs.getString("title_blog"),
							rs.getString("title_blogArticle"),rs.getString("url_blogArticle"),
							rs.getString("age"),rs.getString("blogArticle") );
					recordList.add(record);
				}
			//}
			

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("JDBCドライバのロードに失敗しました。");
		} catch (SQLException e) {
			System.out.println("MySQLに接続できませんでした。" + e);
		} finally {
			if (con != null) {
				try {
					con.close();
					System.out.println("MySQLのクローズに成功しました。");
				} catch (SQLException e) {
					System.out.println("MySQLのクローズに失敗しました。");
				}
			}
		}

		return recordList;

	}

	public static boolean[] makeRandomNumArray(int recordNum, int startRange, int endRange){

		boolean[] randomNumArray = new boolean[endRange-startRange];
		Random rand = new Random();

		// すべての重複判定用配列をfalseにしておく
		for(int i=0; i<endRange-startRange; i++){
			randomNumArray[i] = false;
		}

		//要素数回数をループ
		for(int i=0; i < recordNum; ){
			
			//System.out.println(i);
			int p = rand.nextInt(endRange - startRange);
			//System.out.println(p);
			if(randomNumArray[p] == false){ //まだ使ってない値か判定
				randomNumArray[p] = true; //使った値はtrueにしておく
				i++; //ループ用の値をインクリメント
			}
			//System.out.println(randomNumArray[p]);
		}
		return randomNumArray;
	}


}
