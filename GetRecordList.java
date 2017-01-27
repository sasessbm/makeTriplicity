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

public class GetRecordList {

	//プロキシ設定
	private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.nagaokaut.ac.jp", 8080));

	public static  ArrayList<Record> getRecordList(int recordNum) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		Connection con = null;
		int idCount = 0;
		try {
			// JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// MySQLに接続
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobyo_db?useSSL=false", "root", "databasetest86");
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?useSSL=false", "root", "databasetest86");
			System.out.println("MySQLに接続できました。");

			Statement stm = con.createStatement();

			String sql = "";


			//とりあえず最初の recordNum 個を取得
			for(int id =1; id <= recordNum; id++){
				Record record;
				Snippet snippet;
				//sql = "select snippet from testTable where id = " + id;
				sql = "select * from tobyo_table where id = " + id;
				ResultSet rs = stm.executeQuery(sql);

				while(rs.next()){
					snippet = new Snippet(rs.getString("snippet"));
					record = new Record(rs.getInt("id"), snippet, rs.getString("medicineName"), 
							rs.getString("diseaseName"), rs.getString("sex"),rs.getString("title_blog"),
							rs.getString("title_blogArticle"),rs.getString("url_blogArticle"),
							rs.getString("age"),rs.getString("blogArticle") );
					recordList.add(record);
				}
			}

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("JDBCドライバのロードに失敗しました。");
		} catch (SQLException e) {
			System.out.println("MySQLに接続できませんでした。" + e);
		} finally {
			System.out.println("test");
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


}
