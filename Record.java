package makeTriplicity;

import java.util.ArrayList;

public class Record {
	
	private int id;
	private Snippet snippet;
	private String medicineName;
	private String diseaseName;
	private String sex;
	private String title_blog;
	private String title_blogArticle;
	private String url_blogArticle;
	private String age;
	private String blogArticle;
	//private ArrayList<String> sentenceList;
	
	//コンストラクタ
	public Record(int id, Snippet snippet, String medicineName,
			String diseaseName, String sex, String title_blog,
			String title_blogArticle, String url_blogArticle, String age,
			String blogArticle) {
		this.id = id;
		this.snippet = snippet;
		this.medicineName = medicineName;
		this.diseaseName = diseaseName;
		this.sex = sex;
		this.title_blog = title_blog;
		this.title_blogArticle = title_blogArticle;
		this.url_blogArticle = url_blogArticle;
		this.age = age;
		this.blogArticle = blogArticle;
	}

	public int getId() {
		return id;
	}

	public Snippet getSnippet() {
		return snippet;
	}

	public String getMedicineName() {
		return medicineName;
	}
	
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public String getSex() {
		return sex;
	}

	public String getTitle_blog() {
		return title_blog;
	}

	public String getTitle_blogArticle() {
		return title_blogArticle;
	}

	public String getUrl_blogArticle() {
		return url_blogArticle;
	}

	public String getAge() {
		return age;
	}

	public String getBlogArticle() {
		return blogArticle;
	}

//	public void setSentenceList(ArrayList<String> sentenceList) {
//		this.sentenceList = sentenceList;
//	}
//
//	public ArrayList<String> getSentenceList() {
//		return sentenceList;
//	}

}
