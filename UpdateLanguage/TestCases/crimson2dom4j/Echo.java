import org.dom4j.io.SAXReader;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import java.util.List;

public class Echo {
	public static void main(String[] args) throws Exception {
        String filename = null;
        boolean validation = false;
        boolean ignoreWhitespace = false;
        boolean ignoreComments = false;
        DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();
        dbf.setValidating(validation);
        dbf.setIgnoringComments(ignoreComments);
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
            System.exit(1);
        }
        //parse the input file
        //org.w3c.dom.Document in crimson 
        //org.dom4j.Document in dom4j 
        Document doc = null;
        try {
            doc = db.parse(new File(filename));
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
        //org.w3c.dom.Element in crimson
        //org.dom4j.Element in dom4j
        Element ele = doc.getDocumentElement();
        NodeList nList = ele.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++)
        	process(nList.item(i));
	}
	
	static void process(Object x) {
		
	}
}
