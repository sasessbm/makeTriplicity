package makeTriplicity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import test.CaboChaTest3;

public class Main {
	
	public static final String TARGETMEDICINE = "TARGETMEDICINE";

	public static void main(String[] args) throws Exception {

		ArrayList<TripleSet> tripleSetList = run(0,500);

	}

	public static ArrayList<TripleSet> run(int startRecordNum, int endRecordNum) throws Exception {

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		ArrayList<Record> recordList = new ArrayList<Record>();
		//int recordNum = 100;
		//int startRecordNum = 0;
		//int endRecordNum = 10;
		int tripleSetCount = 0;
		int getSentenceNumOfTriple = 0;

		//recordList取得　(recordの生成)
		recordList = GetRecordList.getRecordList(startRecordNum, endRecordNum);

		//レコード単位
		for(Record record : recordList){

			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String TargetMedicineName = record.getMedicineName();

			//System.out.println(snippetText);

			//"。"が無いスニペットは対象としない
			//if(!snippetText.contains("。")){ continue; }

			//対象薬剤名が無いスニペットは対象としない
			if(!snippetText.contains(TargetMedicineName)){ continue; }

			snippetText = PreProcessing.deleteBothSideDots(snippetText);

			//System.out.println("スニペット: " + snippetText);

			//SentenceList取得
			ArrayList<String> sentenceTextList = new ArrayList<String>();
			sentenceTextList = PreProcessing.getSentenceTextList(snippetText);

			String sentenceTextBefore = "";

			//文単位
			for(String sentenceText : sentenceTextList){

				//System.out.println("\r\n文: " + sentenceText);

				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }

				//対象薬剤名を含まない文は対象としない
				if(!sentenceText.contains(TargetMedicineName)){ continue; }

				sentenceTextBefore = sentenceText;
				//System.out.println("\r\n文: " + sentenceTextBefore);

				//前処理
				TreeMap<Integer, String> otherMedicineNameMap = PreProcessing.getOtherMedicineNameMap(sentenceText,TargetMedicineName);


				sentenceText = PreProcessing.replaceMedicineName(sentenceText, TargetMedicineName, otherMedicineNameMap);
				//System.out.println("\r\n文: " + sentenceText);
				sentenceText = PreProcessing.deleteParentheses(sentenceText);
				sentenceText = PreProcessing.deleteSpace(sentenceText);

				//System.out.println("\r\n文: " + sentenceText);

				//空白の文は対象としない
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }
				//System.out.println("\r\n文: " + sentenceText);

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//phraseList取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				phraseList = XmlReader.GetPhraseList(xmlList);

				//後処理
				//対象でない薬剤名を元に戻す
				phraseList = PostProcessing.restoreOtherMedicineName(phraseList, otherMedicineNameMap);

				ArrayList<TriplePhrase> triplePhraseListFirst = GetTriplePhraseListFirst.getTriplePhrase(phraseList);
				ArrayList<TripleSet> tripleSetListFirst = new ArrayList<TripleSet>();
				ArrayList<TripleSet> tripleSetListSecond = new ArrayList<TripleSet>();

				if(triplePhraseListFirst.size() != 0){
					tripleSetListFirst = getTripleSetList(triplePhraseListFirst, TargetMedicineName);

					if(tripleSetListFirst.size() != 0){
						tripleSetList.addAll(tripleSetListFirst);
						display(record, sentenceTextBefore, tripleSetListFirst);
						System.out.println("\r\n提案手法から取得");
						getSentenceNumOfTriple++;
						tripleSetCount += tripleSetListFirst.size();
						//continue;
					}
				}

				ArrayList<TriplePhrase> triplePhraseListSecond = GetTriplePhraseListSecond.getTriplePhrase(phraseList);
				if(triplePhraseListSecond.size() == 0){
					if(tripleSetListFirst.size() != 0){
						System.out.println("------------------------------------------------------------------------------------");
					}
					continue; 
				}


				tripleSetListSecond = getTripleSetList(triplePhraseListSecond, TargetMedicineName);

				if(tripleSetListSecond.size() != 0){
					tripleSetList.addAll(tripleSetListSecond);
					display(record, sentenceTextBefore, tripleSetListSecond);
					System.out.println("\r\nベースライン２から取得");
					getSentenceNumOfTriple++;
					tripleSetCount += tripleSetListSecond.size();
				}

				if(tripleSetListFirst.size() != 0 || tripleSetListSecond.size() != 0){
					System.out.println("------------------------------------------------------------------------------------");
				}
			}
		}
		//System.out.println("------------------------------------------------------------------------------------");
		System.out.println(getSentenceNumOfTriple + "文から");
		System.out.println("三つ組を" + tripleSetCount +"個取得できました！！");
		System.out.println("終了！！！");

		return tripleSetList;
	}

	//三つ組リスト取得
	public static ArrayList<TripleSet> getTripleSetList(ArrayList<TriplePhrase> triplePhraseListFirst, String medicineName){

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		for(TriplePhrase triplePhrase : triplePhraseListFirst){
			TripleSet tripleSet = makeTripleSet(triplePhrase, medicineName);
			//110番辞書フィルタ
			if(!Filtering.filterTarget(tripleSet)){ continue; }
			PostProcessing.deleteParentheses(tripleSet);
			tripleSetList.add(tripleSet);
		}
		return tripleSetList;
	}

	//三つ組取得
	public static TripleSet makeTripleSet(TriplePhrase triplePhrase, String medicineName){

		//薬剤名セット
		triplePhrase.setMedicineName(medicineName);
		//薬剤名置き換え
		triplePhrase = replaceMedicineName(triplePhrase , medicineName);
		//三つ組取得
		TripleSet tripleSet = GetTripleSet.getTripleSet(triplePhrase);

		return tripleSet;
	}

	//薬剤名置き換え
	public static TriplePhrase replaceMedicineName(TriplePhrase triplePhrase, String medicineName){

		ArrayList<Phrase> targetPhraseList = triplePhrase.getTargetPhraseList();
		Phrase effectPhrase = triplePhrase.getEffectPhrase();

		for(Phrase targetPhrase : targetPhraseList){
			if(targetPhrase.getPhraseText().contains(TARGETMEDICINE)){
				targetPhrase.setPhraseText(targetPhrase.getPhraseText().replace(TARGETMEDICINE, medicineName));
				for(Morpheme morpheme : targetPhrase.getMorphemeList()){
					if(!morpheme.getMorphemeText().contains(TARGETMEDICINE)){ continue; }
					morpheme.setMorphemeText(morpheme.getMorphemeText().replace(TARGETMEDICINE, medicineName));
				}
			}
		}
		effectPhrase.setPhraseText(effectPhrase.getPhraseText().replace(TARGETMEDICINE, medicineName));
		for(Morpheme morpheme : effectPhrase.getMorphemeList()){
			if(!morpheme.getMorphemeText().contains(TARGETMEDICINE)){ continue; }
			morpheme.setMorphemeText(morpheme.getMorphemeText().replace(TARGETMEDICINE, medicineName));
		}
		return triplePhrase;
	}

	//情報表示
	public static void display(Record record, String sentenceTextBefore, ArrayList<TripleSet> tripleSetList){

		//ステータス表示
		//System.out.println("------------------------------------------------------------------------------------");
		System.out.print("\r\nId:" +record.getId());
		System.out.print(" | 性別:" +record.getSex());
		System.out.print(" | 年齢:" +record.getAge());
		System.out.println(" | 病名:" +record.getDiseaseName());
		System.out.println("\r\n文: " + sentenceTextBefore);

		//三つ組表示
		for(TripleSet tripleSet : tripleSetList){
			System.out.println("\r\n薬剤名: " + tripleSet.getMedicineName());
			System.out.println("対象: " + tripleSet.getTargetElement().getText());
			System.out.println("効果: " + tripleSet.getEffectElement().getText());
		}
	}

}
