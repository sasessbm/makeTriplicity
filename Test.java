package makeTriplicity;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws Exception {
		
//		String snippetText = "... 2007.07.22 このブログを購読する アバスチン [ 薬 ] 　日経ビジネス（2007.6.11)からの抜粋です。 "
//				+ "「アバスチン」は、スイスの製薬会社ロシュの子会社で、米バイオベンチャー大手のジェネンテックが開発。 　"
//				+ "２００７年４月中旬、ロシュの子会社である中外製薬が厚生労働省から認可......抗がん剤は、ガン細胞を直接攻撃し増殖を"
//				+ "抑制するが、アバスチンは、ガン細胞を直接は、攻撃しない。 ガン細胞は、分裂を繰り返して増殖する過程で、自身の周囲に血管を作り出す。"
//				+ " 増殖に必要な栄養や酸素を得る為である。 アバスチンは、この栄養や酸素の供給源となる血管の育成を阻止す...";
//		
//		String medicineName = "アバスチン";
//		String sentenceText="...（お借りしました）W先生から、アバスチンのハナシが出たので、お勉強";
//		System.out.println("文:" + sentenceText);
//		sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
//		System.out.println("文:" + sentenceText);
//		sentenceText = Preprocessor.deleteParentheses(sentenceText);
//		System.out.println("文:" + sentenceText);
		
		String sentenceText = "リウマトレックスを減らすと肝機能が改善";
		String medicineName = "リウマトレックス";
		
		//前処理
		sentenceText = Preprocessor.replaceMedicineName(sentenceText, medicineName);
		sentenceText = Preprocessor.deleteParentheses(sentenceText);
		
		//構文解析結果をXml形式で取得
		ArrayList<String> xmlList = new ArrayList<String>();
		xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

		//phraseList取得　(phrase,morphemeの生成)
		ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
		phraseList = XmlReader.GetPhraseList(xmlList);
		
		TriplePhrase triplePhrase = GetTriplePhraseListFirst.getTriplePhrase(phraseList);
		
		triplePhrase.setMedicineName(medicineName);
		
		System.out.println("薬剤名:" + triplePhrase.getMedicineName());
		//System.out.println("対象要素存在文節:" + triplePhrase.getTargetPhrase());
		//System.out.println("効果要素存在文節:" + triplePhrase.getEffectPhrase());

		
	}

}
