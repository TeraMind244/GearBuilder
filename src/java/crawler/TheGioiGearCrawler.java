
package crawler;

import gear.GearDAO;
import generated.gear.Gear;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.AppConstant;
import util.CrawlerUtil;

public class TheGioiGearCrawler extends BaseCrawler implements Runnable {

    private String url;
    private String domain = AppConstant.urlTheGioiGear;
    
    public TheGioiGearCrawler(String url) {
        this.url = url;
    }
    
    public void getHtml(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String line = "";
            StringBuilder document = new StringBuilder("");
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div id=\"pagination\" class=\"\">")) {
                    break;
                }
                if (isStart) {
//                    System.out.println(line);
                    document.append(line.trim());
                }
                if (line.contains("<div class=\"col-md-12 col-sm-12 col-xs-12 content-product-list\">")) {
                    isStart = true;
                }
            }
            staxParserForDocument(document.toString());
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(TheGioiGearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(TheGioiGearCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
        
        String source = "thegioigear.com";
        
        String productUrl = "";
        String imgUrl = "";
        String productName = "";
        int price = 0;
        String type = "";
        
        int endTagMark = 0;
        
        int productStartMark = 0;
        int productNameMark = 0;
        int productPriceMark = 0;
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);
            
            if (event.isStartElement()) {
                endTagMark++;
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("div".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null) {
                        if (attrClass.getValue().equals("product-block product-resize")) {
                            productStartMark = endTagMark;
                        }
                    }
                } else if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    if (attrHref != null && productNameMark > 0) {
                        productUrl = domain + attrHref.getValue();
                    }
                } else if ("img".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                    if (attrClass != null && attrClass.getValue().contains("first-image") && attrSrc != null) {
                        imgUrl = attrSrc.getValue();
                    }
                } else if ("h3".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null && attrClass.getValue().equals("pro-name") && productStartMark > 0) {
                        productNameMark = endTagMark;
                    }
                }  else if ("p".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null && attrClass.getValue().equals("pro-price")) {
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
                    type = CrawlerUtil.categorizeType(productName);
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String tagName = endElement.getName().getLocalPart();
                if ("div".equals(tagName)) {
                    if (endTagMark == productStartMark) {
                        if (price > 0) {
                            int hashStr = CrawlerUtil.hashingString(productName + source);
                            Gear gear = new Gear(hashStr, productName, source, productUrl, imgUrl, price, type);
                            GearDAO.getInstance().saveGear(gear);
//                            System.out.println("Product{name: " + productName + ", imgUrl: " + imgUrl + ", productUrl: " + productUrl + ", price: " + price + "k}");
                        }
                        productStartMark = 0;
                        productUrl = "";
                        imgUrl = "";
                        productName = "";
                        type = "";
                        price = 0;
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
        }
        System.out.println("Finish crawler: " + url);
    }

    @Override
    public void run() {
        getHtml(url);
    }
    
}
