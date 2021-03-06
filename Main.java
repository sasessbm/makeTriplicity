package makeTriplicity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import test.CaboChaTest3;

public class Main {

	public static final String MEDICINE = "MEDICINE";
	public static final String DIVISION_LINE = "--------------------------------------------------------"
												 + "--------------------------------------------------------";
	
	private static ArrayList<String> medicineNameList 
									= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

	public static void main(String[] args) throws Exception {

		ArrayList<TripleSet> tripleSetList = run(0,3000);
	}

	public static ArrayList<TripleSet> run(int startRecordNum, int endRecordNum) throws Exception {

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		ArrayList<Record> recordList = new ArrayList<Record>();
		int countGetSnippet = 0;
		int countGetSentence = 0;
		int countTripleSet = 0;

		//recordList取得　(recordの生成)
		recordList = GetRecordList.getRecordList(startRecordNum, endRecordNum);

		//レコード単位
		for(Record record : recordList){

			boolean writerStatusHasDisplayed = false;
			Snippet snippet = record.getSnippet();
			String snippetText = snippet.getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			String sentenceTextBefore = "";
			
			//System.out.println(snippetText);

			//if(!snippetText.contains("。")){ continue; }	//"。"が無いスニペットは対象としない
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			ArrayList<String> sentenceTextList = new ArrayList<String>();
			sentenceTextList = PreProcessing.getSentenceTextList(snippetText);

			//文単位
			for(String sentenceText : sentenceTextList){
				
				boolean sentenceHasDisplayed = false;

				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }	//空白の文は対象としない
				if(!sentenceText.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない
				sentenceTextBefore = sentenceText;

				//前処理
				TreeMap<Integer, String> medicineNameMap = 
								PreProcessing.getMedicineNameMap(sentenceText,medicineNameList); //薬剤名取得
//				ArrayList<String> medicineNameInSentenceList = 
//						PreProcessing.getMedicineNameInSentenceList(sentenceText, medicineNameList);
				
				//PostProcessing.medicineNameMap = medicineNameMap; // 後処理クラスに代入
				sentenceText = 
						PreProcessing.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え
				sentenceText = PreProcessing.deleteParentheses(sentenceText);	//括弧削除
				sentenceText = PreProcessing.deleteSpace(sentenceText);	//スペース削除

				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }	//空白の文は対象としない

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//phraseList取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
				phraseList = XmlReader.GetPhraseList(xmlList);

				//三つ組取得
//				ArrayList<TriplePhrase> triplePhraseListFirst 
//										= GetTriplePhraseListFirst.getTriplePhrase(phraseList, phraseRestoreList);
				ArrayList<TripleSetInfo> tripleSetInfoList = SearchElementPhrase.getTripleSetInfoList(phraseList);
				phraseList = PostProcessing.restoreMedicineName(phraseList, medicineNameMap);
				
				ArrayList<TripleSet> tripleSetListFirst = new ArrayList<TripleSet>();
				tripleSetListFirst = GetTripleSetList.getTripleSetList(tripleSetInfoList, phraseList, medicineNameMap);
				
				//ArrayList<TriplePhrase> triplePhraseListSecond = GetTriplePhraseListSecond.getTriplePhrase(phraseList);
				//ArrayList<TripleSet> tripleSetListFirst = new ArrayList<TripleSet>();
				ArrayList<TripleSet> tripleSetListSecond = new ArrayList<TripleSet>();

//				if(tripleSetListFirst.size() != 0){
//					tripleSetListFirst = getTripleSetList(triplePhraseListFirst);
//				}
				
//				if(triplePhraseListSecond.size() != 0){
//					tripleSetListSecond = getTripleSetList(triplePhraseListSecond, TargetMedicineName);
//				}
				
				//表示
				if(tripleSetListFirst.size() != 0 || tripleSetListSecond.size() != 0){
					
					if(writerStatusHasDisplayed == false){
						countGetSnippet++;
						displayWriterStatus(record, sentenceTextBefore);
						writerStatusHasDisplayed = true;
					}
					
					if(sentenceHasDisplayed == false){
						countGetSentence++;
						System.out.println("\r\n\r\n文: " + sentenceTextBefore);
						sentenceHasDisplayed = true;
					}
					
					countTripleSet += tripleSetListFirst.size();
					countTripleSet += tripleSetListSecond.size();
					tripleSetList.addAll(tripleSetListFirst);
					tripleSetList.addAll(tripleSetListSecond);
					displayTripleSet(tripleSetListFirst, "\r\n提案手法から取得");
					displayTripleSet(tripleSetListSecond, "\r\nベースライン２から取得");
				}
			}
		}
		System.out.println(DIVISION_LINE);
		System.out.println(countGetSnippet + "スニペット中の");
		System.out.println(countGetSentence + "文から");
		System.out.println("三つ組を" + countTripleSet +"個取得できました！！");
		System.out.println("終了！！！");

		return tripleSetList;
	}

	//三つ組リスト取得
	public static ArrayList<TripleSet> getTripleSetList(ArrayList<TriplePhrase> triplePhraseListFirst){
		
		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		//triplePhraseListFirst = PostProcessing.restoreMedicineName(triplePhraseListFirst , medicineNameMap); //薬剤名置き換え
		
		for(TriplePhrase triplePhrase : triplePhraseListFirst){
			TripleSet tripleSet = makeTripleSet(triplePhrase, triplePhrase.getMedicineName());
			//if(!Filtering.filterTarget(tripleSet)){ continue; }	//110番辞書フィルタ
			PostProcessing.deleteParentheses(tripleSet);
			tripleSetList.add(tripleSet);
		}
		
		return tripleSetList;
	}

	//三つ組取得
	public static TripleSet makeTripleSet(TriplePhrase triplePhrase, String medicineName){
		
		//triplePhrase.setMedicineName(medicineName);	//薬剤名セット
		//triplePhrase = PostProcessing.restoreMedicineName(triplePhrase , medicineNameMap); //薬剤名置き換え
		TripleSet tripleSet = GetTripleSetBefore.getTripleSet(triplePhrase);	//三つ組取得
		return tripleSet;
	}

	//ステータス表示
	public static void displayWriterStatus(Record record, String sentenceTextBefore){
		
		System.out.println(DIVISION_LINE);
		System.out.print("Id:" +record.getId());
		System.out.print(" | 性別:" +record.getSex());
		System.out.print(" | 年齢:" +record.getAge());
		System.out.println(" | 病名:" +record.getDiseaseName());
	}
	
	//三つ組表示
	public static void displayTripleSet(ArrayList<TripleSet> tripleSetList, String gainType){

		if(tripleSetList.size() == 0){ return; }
		for(TripleSet tripleSet : tripleSetList){
			System.out.println("\r\n薬剤名: " + tripleSet.getMedicineName());
			System.out.println("対象: " + tripleSet.getTargetElement().getText());
			System.out.println("効果: " + tripleSet.getEffectElement().getText());
		}
		System.out.println(gainType);
	}
}
