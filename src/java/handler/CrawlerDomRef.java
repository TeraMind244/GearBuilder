
package handler;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.AppConstant;

public class CrawlerDomRef {
    
    public static String getTextWithXpath(XPath xpath, Node node, String expression)
            throws XPathExpressionException {
        String text = (String) xpath.evaluate(expression, node, XPathConstants.STRING);
        return text == null ? "" : text.trim();
    }
    
    public static Node getNodeWithXpath(XPath xpath, Node node, String expression) 
            throws XPathExpressionException {
        return (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
    }
    
    public static NodeList getNodeListWithXpath(XPath xpath, Node node, String expression) 
            throws XPathExpressionException {
        return (NodeList) xpath.evaluate(expression, node, XPathConstants.NODESET);
    }
    
    public static void getCrawlerLinks(XPath xpath, Node doc)
            throws XPathExpressionException {
        NodeList aDayRoiLinks = getNodeListWithXpath(xpath, doc, "//page[@name='adayroi']//link");
        for (int i = 0; i < aDayRoiLinks.getLength(); i++) {
            Node link = aDayRoiLinks.item(i);
            AppConstant.ADayRoiLinks.add(link.getTextContent());
        }
        NodeList theGioiGearLinks = getNodeListWithXpath(xpath, doc, "//page[@name='thegioigear']//link");
        for (int i = 0; i < theGioiGearLinks.getLength(); i++) {
            Node link = theGioiGearLinks.item(i);
            AppConstant.TheGioiGearLinks.add(link.getTextContent());
        }
    }
    
    public static void getCrawlerMarks(XPath xpath, Node doc)
            throws XPathExpressionException {
        AppConstant.ADayRoiPageCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/startMark");
        AppConstant.ADayRoiPageCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/endMark");
        AppConstant.ADayRoiPageCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/startFragMent");
        AppConstant.ADayRoiPageCrawlerEndFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/endFragment");
        
        AppConstant.ADayRoiCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/startMark");
        AppConstant.ADayRoiCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/endMark");
        AppConstant.ADayRoiCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/startFragment");
        
        AppConstant.TheGioiGearPageCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/lastPageCrawler/startMark");
        AppConstant.TheGioiGearPageCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/lastPageCrawler/endMark");
        
        AppConstant.TheGioiGearCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/dataCrawler/startMark");
        AppConstant.TheGioiGearCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/dataCrawler/endMark");
    }
    
    public static void getPageSize(XPath xpath, Node doc)
            throws XPathExpressionException {
        String pageSizeStr = getTextWithXpath(xpath, doc, "//pageSize");
        AppConstant.pageSize = Integer.parseInt(pageSizeStr);
    }
    
    public static void getDomains(XPath xpath, Node doc)
            throws XPathExpressionException {
        AppConstant.ADayRoiDomain = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/attribute::domain");
        AppConstant.TheGioiGearDomain = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/attribute::domain");
    }
    
    public static void readXMLRefFile(String refFilePath)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File f = new File(refFilePath);
        Document doc = builder.parse(f);

        XPathFactory xpFactory = XPathFactory.newInstance();
        XPath xpath = xpFactory.newXPath();

        getCrawlerLinks(xpath, doc);
        getCrawlerMarks(xpath, doc);
        getPageSize(xpath, doc);
        getDomains(xpath, doc);
    }
    
    public static void main(String[] args) {
        try {
            readXMLRefFile("web/xml/crawlerRef.xml");
            System.out.println(AppConstant.pageSize);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(CrawlerDomRef.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
