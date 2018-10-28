
package crawler;

import gear.GearDAO;
import generated.gear.Gear;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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

public class ADayRoiCrawler extends BaseCrawler implements Runnable {
    
    private String url;
    private String domain = AppConstant.urlADayRoi;

    public ADayRoiCrawler(ServletContext context, String url) {
        super(context);
        this.url = url;
    }
    
    public void getHtml(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains(".product-item_info-text-sale span")) {
                    break;
                }
                if (line.contains("<div class=\"product-list__container\">")) {
                    isStart = true;
                }
                if (isStart) {
//                    System.out.println(line);
                    document += line.trim();
                }
            }
            staxParserForDocument(document);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(ADayRoiCrawler.class.getName()).log(Level.SEVERE, url, ex);
        } catch (Exception ex) {
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
        String startDocument = "<div class=\"product-list__container\">";
        document = document.trim().substring(document.indexOf(startDocument) + startDocument.length(), document.length());
        document = document.replaceAll("&", "&amp;").replaceAll("disabled", "").replaceAll("\"a>", "\">");
        
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Iterator<XMLEvent> events = autoAddMissingTag(eventReader);
        
        String source = "adayroi.com";
        
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
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null) {
                        if (attrClass.getValue().equals("product-item__container")) {
                            productStartMark = endTagMark;
                        } else if (attrClass.getValue().equals("product-item__info-price")) {
                            productPriceMark = endTagMark;
                        }
                    }
                } else if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null && productStartMark > 0) {
                        productUrl = domain + attrHref.getValue();
                    }
                    if (attrClass != null && attrClass.getValue().equals("product-item__info-title") && productStartMark > 0) {
                        productTitleMark = endTagMark;
                    }
                } else if ("img".equals(tagName)) {
                    Attribute attrSrc = startElement.getAttributeByName(new QName("data-src"));
                    if (attrSrc != null && productStartMark > 0) {
                        imgUrl = attrSrc.getValue();
                    }
                } else if ("span".equals(tagName)) {
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrClass != null && attrClass.getValue().equals("product-item__info-price-sale")) {
                        productPriceMark = endTagMark;
                    }
                }
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                String text = characters.getData().trim();
                if (productPriceMark > 0) {
                    price = CrawlerUtil.convertToPrice(text);
                } else if (productTitleMark > 0) {
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
                        price = 0;
                    }
                } else if ("span".equals(tagName)) {
                    if (endTagMark == productPriceMark) {
                        productPriceMark = 0;
                    }
                } else if ("a".equals(tagName)) {
                    if (endTagMark == productTitleMark) {
                        productTitleMark = 0;
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
