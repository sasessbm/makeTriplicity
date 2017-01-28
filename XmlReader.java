package makeTriplicity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlReader {
	
	public static ArrayList<Phrase> GetPhraseList (ArrayList<String> xmlList) throws SAXException, IOException, ParserConfigurationException {
		
		ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
		
		
		int rec = 0;
		String xmlTextAll = "";
		for(String xmlText : xmlList){
			if (rec == 0) {
				xmlText = excludeBOMString(xmlText);
			}
			
			xmlTextAll += xmlText + "\r";
			rec++;
		}
		//System.out.println(xmlTextAll);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(xmlTextAll));
		Document document = documentBuilder.parse(inputSource);

		Element root = document.getDocumentElement();

		//ルート要素のノード名を取得する
		//System.out.println("ノード名：" +root.getNodeName());

		//ルート要素の子ノードを取得する
		NodeList rootChildren = root.getChildNodes();

		//System.out.println("子要素の数：" + rootChildren.getLength());
		//System.out.println("------------------");

		for(int i=0; i < rootChildren.getLength(); i++) {
			Node node = rootChildren.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				
				
				
				Element element = (Element)node;
				if (element.getNodeName().equals("chunk")) {
					int dependencyIndex = -10;
					String phraseText = "";
					//System.out.println("chunkId：" + element.getAttribute("id"));
					//System.out.println("link：" + element.getAttribute("link"));
					
					dependencyIndex = Integer.parseInt(element.getAttribute("link"));
					
					NodeList sentenceChildren = node.getChildNodes();
					
					ArrayList<Morpheme> morphemeList = new ArrayList<Morpheme>();
					
					for (int j=0; j < sentenceChildren.getLength(); j++) {
						
						
						
						String morphemeText = "";
						String feature = "";
						
						
						Node sentenceNode = sentenceChildren.item(j);
						if (sentenceNode.getNodeType() == Node.ELEMENT_NODE) {
							Element element2 = (Element)sentenceNode;
							if (element2.getNodeName().equals("tok")) {
								
								morphemeText = element2.getTextContent();
								feature = element2.getAttribute("feature");
								
								Morpheme morpheme = new Morpheme(morphemeText, feature);
								morphemeList.add(morpheme);
								
//								System.out.println("tokId：" + element2.getAttribute("id"));
//								System.out.println("形態素：" + element2.getTextContent());
//								System.out.println("feature：" + element2.getAttribute("feature"));
								
							}
						}
					}
					
					for(Morpheme morpheme : morphemeList){
						phraseText += morpheme.getMorphemeText();
					}
					
					Phrase phrase = new Phrase(phraseText,dependencyIndex);
					phraseList.add(phrase);
					
					System.out.println("------------------");
				}
			}

		}
		
		
		
		return phraseList;

	}
	
	private static String excludeBOMString(String original_str) {
		if (original_str != null) {
			char c = original_str.charAt(0);
			if (Integer.toHexString(c).equals("feff")) {
				StringBuilder sb = new StringBuilder();
				for (int i=1; i < original_str.length(); i++) {
					sb.append(original_str.charAt(i));
				}
				return sb.toString();
			} else {
				return original_str;
			}
		} else {
			return "";
		}
	}
	
	
	
	
}