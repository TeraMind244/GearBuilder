
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
import util.constant.ADayRoiConstant;
import util.constant.AppConstant;
import util.constant.PlayZoneConstant;
import util.constant.TheGioiGearConstant;

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
            String type = getTextWithXpath(xpath, link, "@type");
            ADayRoiConstant.ADayRoiLinks.add(link.getTextContent() + ";" + type);
        }
        NodeList theGioiGearLinks = getNodeListWithXpath(xpath, doc, "//page[@name='thegioigear']//link");
        for (int i = 0; i < theGioiGearLinks.getLength(); i++) {
            Node link = theGioiGearLinks.item(i);
            String type = getTextWithXpath(xpath, link, "@type");
            TheGioiGearConstant.TheGioiGearLinks.add(link.getTextContent() + ";" + type);
        }
        NodeList playZoneLinks = getNodeListWithXpath(xpath, doc, "//page[@name='playzone']//link");
        for (int i = 0; i < playZoneLinks.getLength(); i++) {
            Node link = playZoneLinks.item(i);
            String type = getTextWithXpath(xpath, link, "@type");
            PlayZoneConstant.PlayZoneLinks.add(link.getTextContent() + ";" + type);
        }
    }
    
    public static void getCrawlerMarks(XPath xpath, Node doc)
            throws XPathExpressionException {
        ADayRoiConstant.ADayRoiPageCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/startMark");
        ADayRoiConstant.ADayRoiPageCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/endMark");
        ADayRoiConstant.ADayRoiPageCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/startFragMent");
        ADayRoiConstant.ADayRoiPageCrawlerEndFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/lastPageCrawler/endFragment");
        
        ADayRoiConstant.ADayRoiCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/startMark");
        ADayRoiConstant.ADayRoiCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/endMark");
        ADayRoiConstant.ADayRoiCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/dataCrawler/startFragment");
        
        TheGioiGearConstant.TheGioiGearPageCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/lastPageCrawler/startMark");
        TheGioiGearConstant.TheGioiGearPageCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/lastPageCrawler/endMark");
        
        TheGioiGearConstant.TheGioiGearCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/dataCrawler/startMark");
        TheGioiGearConstant.TheGioiGearCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/dataCrawler/endMark");
        
        PlayZoneConstant.PlayZonePageCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='playzone']/lastPageCrawler/startMark");
        PlayZoneConstant.PlayZonePageCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='playzone']/lastPageCrawler/endMark");
        PlayZoneConstant.PlayZonePageCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='playzone']/lastPageCrawler/startFragMent");
        
        PlayZoneConstant.PlayZoneCrawlerStartMark = getTextWithXpath(xpath, doc, "//page[@name='playzone']/dataCrawler/startMark");
        PlayZoneConstant.PlayZoneCrawlerEndMark = getTextWithXpath(xpath, doc, "//page[@name='playzone']/dataCrawler/endMark");
        PlayZoneConstant.PlayZoneCrawlerStartFragment = getTextWithXpath(xpath, doc, "//page[@name='playzone']/dataCrawler/startFragment");
    }
    
    public static void getPageSize(XPath xpath, Node doc)
            throws XPathExpressionException {
        String pageSizeStr = getTextWithXpath(xpath, doc, "//pageSize");
        AppConstant.pageSize = Integer.parseInt(pageSizeStr);
    }
    
    public static void getDomains(XPath xpath, Node doc)
            throws XPathExpressionException {
        ADayRoiConstant.ADayRoiDomain = getTextWithXpath(xpath, doc, "//page[@name='adayroi']/attribute::domain");
        TheGioiGearConstant.TheGioiGearDomain = getTextWithXpath(xpath, doc, "//page[@name='thegioigear']/attribute::domain");
        PlayZoneConstant.PlayZoneDomain = getTextWithXpath(xpath, doc, "//page[@name='playzone']/attribute::domain");
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
            for (String link : ADayRoiConstant.ADayRoiLinks) {
                System.out.println(link);
            }
            for (String link : TheGioiGearConstant.TheGioiGearLinks) {
                System.out.println(link);
            }
            for (String link : PlayZoneConstant.PlayZoneLinks) {
                System.out.println(link);
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(CrawlerDomRef.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
