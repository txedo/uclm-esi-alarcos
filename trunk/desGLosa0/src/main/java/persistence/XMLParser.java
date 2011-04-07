package persistence;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.parser.XMLRootNameException;

public class XMLParser {
	
	/*
	 * Example of XML file
	 * -------------------
	 * <?xml version="1.0" encoding="UTF-8"?>
		<metricas>
			<medida_base>
				<nombre>Medida base 1</nombre>
				<metodo_medicion>Metodo 1</metodo_medicion>
				<valor>1</valor>
				<unidad>Unidad del metodo 1</unidad>
			</medida_base>
			<medida_base>
				<nombre>Medida base 2</nombre>
				<metodo_medicion>Metodo 2</metodo_medicion>
				<valor>2</valor>
				<unidad>Unidad del metodo 2</unidad>
			</medida_base>
			<medida_base>
				<nombre>Medida base 3</nombre>
				<metodo_medicion>Metodo 3</metodo_medicion>
				<valor>3</valor>
				<unidad>Unidad del metodo 3</unidad>
			</medida_base>
		</metricas>
	 */

	public static Object[][] parse (String filename, String rootname, String registername, String... fields) throws XMLRootNameException, ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ResourceRetriever.getResourceAsStream(filename));
		doc.getDocumentElement().normalize();
		if (!doc.getDocumentElement().getNodeName().equals(rootname)) throw new XMLRootNameException();
		NodeList nodeLst = doc.getElementsByTagName(registername);
		
		// Bidimensional array: 1st dimension represents registers and 2nd dimension represents fields
		Object [][] res = new Object[nodeLst.getLength()][fields.length];
		
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);
			
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				Element fstElmnt = (Element) fstNode;
				
				for (int f = 0; f < fields.length; f++) {
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(fields[f]);
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					res[s][f] = ((Node) fstNm.item(0)).getNodeValue();
				}
			}
		}
		return res;
	}

}
