package makeTriplicity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {
	
	public void domRead(String file) throws SAXException, IOException, ParserConfigurationException {
		
		int rec = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
		while(br.ready()) {
			String line = br.readLine();
			// １行目ならBOM除去メソッドを呼び出す
			if (rec == 0) {
				line = excludeBOMString(line);
			}
			
			rec++;
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		Element root = document.getDocumentElement();

		//ルート要素のノード名を取得する
		System.out.println("ノード名：" +root.getNodeName());

		//ルート要素の属性を取得する
		System.out.println("ルート要素の属性：" + root.getAttribute("name"));

		//ルート要素の子ノードを取得する
		NodeList rootChildren = root.getChildNodes();

		System.out.println("子要素の数：" + rootChildren.getLength());
		System.out.println("------------------");

		for(int i=0; i < rootChildren.getLength(); i++) {
			Node node = rootChildren.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;
				if (element.getNodeName().equals("person")) {
					System.out.println("名前：" + element.getAttribute("name"));
					NodeList personChildren = node.getChildNodes();

					for (int j=0; j < personChildren.getLength(); j++) {
						Node personNode = personChildren.item(j);
						if (personNode.getNodeType() == Node.ELEMENT_NODE) {

							if (personNode.getNodeName().equals("age")) {
								System.out.println("年齢：" + personNode.getTextContent());
							} else if (personNode.getNodeName().equals("interest")) {
								System.out.println("趣味:" + personNode.getTextContent());
							}

						}
					}
					System.out.println("------------------");
				}
			}

		}

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