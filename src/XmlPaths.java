import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlPaths {

	public void xmlxpaths() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		final String dir = System.getProperty("user.dir");
		File folder = new File(dir);
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		};
		File[] listfiles = folder.listFiles(textFilter);
		String filename;
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "/Ф0403202/Составитель/@ВидОрг";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		for (int j = 0; j < listfiles.length; j++) {
			Document doc = builder.parse((listfiles[j]));
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			NodeList list = doc.getChildNodes();
			printelements(list, "/");

			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.println(nodeList.item(i).getNodeName() + " " + nodeList.item(i).getTextContent());
			}

		}
	}

	static void printelements(NodeList list, String s) {

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			if (list.item(i).getNodeType() == Node.COMMENT_NODE) {
				System.out.println(list.item(i).getNodeName() + list.item(i).getNodeValue());
				continue;
			}
			System.out.println(s + list.item(i).getNodeName() + list.item(i).getNodeType());
			if (list.item(i).hasAttributes())
				for (int j = 0; j < list.item(i).getAttributes().getLength(); j++) {
					System.out.println(
							s + list.item(i).getNodeName() + "/@" + list.item(i).getAttributes().item(j).getNodeName());
				}

			if (list.item(i).hasChildNodes()) {
				printelements(list.item(i).getChildNodes(), s + list.item(i).getNodeName() + "/");
			}

		}
		return;

	}
}
