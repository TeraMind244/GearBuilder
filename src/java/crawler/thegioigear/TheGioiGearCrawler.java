
package crawler.thegioigear;

import crawler.BaseCrawler;
import crawler.BaseThread;
import crawler.adayroi.ADayRoiCrawler;
import gear.GearDAO;
import gear.generated.Gear;
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
import util.XMLUtil;
import util.constant.TheGioiGearConstant;

public class TheGioiGearCrawler extends BaseCrawler {

    private String defaultType;
    private String domain = TheGioiGearConstant.TheGioiGearDomain;
    
    public TheGioiGearCrawler(String url, String defaultType) {
        super(url);
        this.defaultType = defaultType;
    }
    
    public void getHtml(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String fragment = getHtmlFragment(reader, 
                    TheGioiGearConstant.TheGioiGearCrawlerStartMark, 
                    TheGioiGearConstant.TheGioiGearCrawlerEndMark);
            staxParserForDocument(fragment);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(TheGioiGearCrawler.class.getName()).log(Level.SEVERE, url, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(TheGioiGearCrawler.class.getName()).log(Level.SEVERE, url, ex);
                }
            }
        }
    }

    @Override
    protected void staxParserForDocument(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
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
        int productNameMark = 0;
        int productPriceMark = 0;
        boolean soldOut = false;
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);
            
            if (event.isStartElement()) {
                endTagMark++;
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                
                if ("div".equals(tagName)) {
                    String attrClass = getAttribute(startElement, "class");
                    
                    if (attrClass.equals("product-block product-resize")) {
                        productStartMark = endTagMark;
                    } else if (attrClass.equals("sold-out")) {
                        soldOut = true;
                    }
                } else if ("a".equals(tagName)) {
                    String attrHref = getAttribute(startElement, "href");
                    
                    if (attrHref.length() > 0 && productNameMark > 0) {
                        productUrl = CrawlerUtil.removeParamFromUrl(domain + attrHref);
                    }
                } else if ("img".equals(tagName)) {
                    String attrClass = getAttribute(startElement, "class");
                    String attrSrc = getAttribute(startElement, "src");
                    
                    if (attrClass.length() > 0 && attrClass.contains("first-image") && attrSrc.length() > 0) {
                        imgUrl = attrSrc;
                    }
                } else if ("h3".equals(tagName)) {
                    String attrClass = getAttribute(startElement, "class");
                    
                    if (attrClass.length() > 0 && attrClass.equals("pro-name") && productStartMark > 0) {
                        productNameMark = endTagMark;
                    }
                }  else if ("p".equals(tagName)) {
                    String attrClass = getAttribute(startElement, "class");
                    
                    if (attrClass.length() > 0 && attrClass.equals("pro-price")) {
                        productPriceMark = endTagMark;
                    }
                }
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                String text = characters.getData().trim();
                
                if (productPriceMark > 0) {
                    price = CrawlerUtil.convertToPrice(text);
                } else if (productNameMark > 0) {
                    productName = text;
                    type = CrawlerUtil.categorizeType(productName, defaultType);
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String tagName = endElement.getName().getLocalPart();
                
                if ("div".equals(tagName)) {
                    if (endTagMark == productStartMark) {
                        if (price > 0 && !soldOut) {
                            int hashStr = CrawlerUtil.hashingString(productUrl);
                            Gear gear = new Gear(hashStr, productName, source, productUrl, imgUrl, price, type);
                            if (XMLUtil.validateWithSchema(gear, schemaFilePath)) {
                                GearDAO.getInstance().saveGear(gear);
                            }
//                            GearDAO.getInstance().saveGear(gear);
//                            System.out.println("Product{name: " + productName + ", imgUrl: " + imgUrl + ", productUrl: " + productUrl + ", price: " + price + "k}");
                        }
                        productStartMark = 0;
                        productUrl = "";
                        imgUrl = "";
                        productName = "";
                        type = "";
                        price = 0;
                        soldOut = false;
                    }
                } else if ("h3".equals(tagName)) {
                    if (endTagMark == productNameMark) {
                        productNameMark = 0;
                    }
                } else if ("p".equals(tagName)) {
                    if (endTagMark == productPriceMark) {
                        productPriceMark = 0;
                    }
                }
                endTagMark--;
            }
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    try {
                        BaseThread.getInstance().wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ADayRoiCrawler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("Finish crawler: " + url);
    }

    @Override
    public void run() {
        createThread();
        getHtml(url);
        finishCrawl();
    }
    
}
