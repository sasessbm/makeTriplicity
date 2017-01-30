package makeTriplicity;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws Exception {
//		ArrayList<String> medicineNameList = new ArrayList<String>();
//		boolean isExsist = false;
//		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
//		for(String medicineNameInList : medicineNameList){
//			System.out.println(medicineNameInList);
//			if(medicineNameInList.equals("アバスチン")){isExsist = true;}
//			//if(medicineNameInList.equals("リオレサール")){isExsist = true;}
//		}
//		System.out.println(isExsist);
		
//		ArrayList<String> keywordList = new ArrayList<String>();
//		boolean isExsist = false;
//		keywordList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\keyword.txt");
//		for(String keyword : keywordList){
//			System.out.println(keyword);
//			if(keyword.equals("")){isExsist = true;}
//			//if(medicineNameInList.equals("リオレサール")){isExsist = true;}
//		}
//		System.out.println(isExsist);
		
//		String sentence = "トリプルネガティブ乳がん財団（TNBCF）の掲示板をチェックしたら、"
//				+ "あの有名な経済誌Forbesにbevacizumab(アバスチンAvastin)関する記事があった";
//		
//		sentence = Preprocessor.replaceMedicineName(sentence);
//		System.out.println("削除前:" + sentence);
//		
//		sentence = Preprocessor.deleteParentheses(sentence);
//		
//		System.out.println("削除後:" + sentence);
		
		String snippetText = "... 2007.07.22 このブログを購読する アバスチン [ 薬 ] 　日経ビジネス（2007.6.11)からの抜粋です。 "
				+ "「アバスチン」は、スイスの製薬会社ロシュの子会社で、米バイオベンチャー大手のジェネンテックが開発。 　"
				+ "２００７年４月中旬、ロシュの子会社である中外製薬が厚生労働省から認可......抗がん剤は、ガン細胞を直接攻撃し増殖を"
				+ "抑制するが、アバスチンは、ガン細胞を直接は、攻撃しない。 ガン細胞は、分裂を繰り返して増殖する過程で、自身の周囲に血管を作り出す。"
				+ " 増殖に必要な栄養や酸素を得る為である。 アバスチンは、この栄養や酸素の供給源となる血管の育成を阻止す...";
		
//		//SentenceList取得
//		ArrayList<String> sentenceTextList = new ArrayList<String>();
//		sentenceTextList = Preprocessor.getSentenceTextList(snippetText);
//		String medicineName = "アバスチン";
//		
//		//前処理
//		
//		for(String sentenceText : sentenceTextList){
//			System.out.println("文:" + sentenceText);
//			sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
//			System.out.println("文:" + sentenceText);
//			sentenceText = Preprocessor.deleteParentheses(sentenceText);
//			System.out.println("文:" + sentenceText);
//		}
		
		String medicineName = "アバスチン";
		String sentenceText="...（お借りしました）W先生から、アバスチンのハナシが出たので、お勉強";
		System.out.println("文:" + sentenceText);
		sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
		System.out.println("文:" + sentenceText);
		sentenceText = Preprocessor.deleteParentheses(sentenceText);
		System.out.println("文:" + sentenceText);
		
	}

}
