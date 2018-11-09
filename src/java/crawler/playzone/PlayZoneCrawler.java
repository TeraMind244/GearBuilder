
package crawler.playzone;

import crawler.BaseCrawler;
import crawler.adayroi.ADayRoiCrawler;
import gear.GearDAO;
import generated.gear.Gear;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.CrawlerUtil;

public class PlayZoneCrawler extends BaseCrawler {
    
    private String defaultType;
    private String domain = "https://www.playzone.vn";
    
    public PlayZoneCrawler(String url, String defaultType) {
        super(url);
        this.defaultType = defaultType;
    }
    
    public void getHtml(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String fragment = getHtmlFragment(reader, 
                    "<div id=\"main\"", 
                    "// Equal height on product items");
            staxParserForDocument(fragment);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(ADayRoiCrawler.class.getName()).log(Level.SEVERE, url, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ADayRoiCrawler.class.getName()).log(Level.SEVERE, url, ex);
                }
            }
        }
    }

    @Override
    protected void staxParserForDocument(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        String startDocument = "<div id=\"main\"";
        
        document = document.trim()
                .substring(document.indexOf(startDocument), document.length())
                .replaceAll("&nbsp;", "");
        
//        System.out.println(document);
        
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Iterator<XMLEvent> events = autoAddMissingTag(eventReader);
        
        String source = domain + "/";
        
        String productUrl = "";
        String imgUrl = "";
        String productName = "";
        int price = 0;
        String type = "";
        
        int endTagMark = 0;
        
        int productStartMark = 0;
        int productTitleMark = 0;
        int productPriceMark = 0;
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);

            if (event.isStartElement()) {
                endTagMark++;
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                
                if ("div".equals(tagName)) {
                    String attrClass = getAttribute(startElement, "class");
                    
                    if (attrClass.equals("item product-layout")) {
                        productStartMark = endTagMark;
                    } else if (attrClass.equals("name")) {
                        productTitleMark = endTagMark;
                    } else if (attrClass.equals("price")) {
                        productPriceMark = endTagMark;
                    }
                } else if ("img".equals(tagName)) {
                    String attrSrc = getAttribute(startElement, "src");
                    
                    if (attrSrc.length() > 0 && productStartMark > 0) {
                        imgUrl = attrSrc;
                    }
                } else if ("a".equals(tagName)) {
                    if (productTitleMark > 0) {
                        String attrHref = getAttribute(startElement, "href");
                        productUrl = attrHref;
                    }
                }
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                String text = characters.getData().trim();
                
                if (productTitleMark > 0) {
                    productName = text;
                    type = CrawlerUtil.categorizeType(productName, defaultType);
                } else if (productPriceMark > 0) {
                    price = CrawlerUtil.convertToPrice(text);
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String tagName = endElement.getName().getLocalPart();
                
                if ("div".equals(tagName)) {
                    if (endTagMark == productStartMark) {
                        if (price > 0) {
                            int hashStr = CrawlerUtil.hashingString(productUrl);
                            Gear gear = new Gear(hashStr, productName, source, productUrl, imgUrl, price, type);
                            GearDAO.getInstance().saveGear(gear);
//                            System.out.println("Product{name: " + productName + ", imgUrl: " + imgUrl + ", productUrl: " + productUrl + ", price: " + price + "}");
                        }
                        productStartMark = 0;
                        productUrl = "";
                        imgUrl = "";
                        productName = "";
                        price = 0;
                        type = "";
                    } else if (endTagMark == productPriceMark) {
                        productPriceMark = 0;
                    } else if (endTagMark == productTitleMark) {
                        productTitleMark = 0;
                    }
                }
                endTagMark--;
            }
        }
    }

    @Override
    public void run() {
        createThread();
        getHtml(url);
        finishCrawl();
    }
    
}
