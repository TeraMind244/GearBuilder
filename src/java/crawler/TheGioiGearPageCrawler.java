
package crawler;

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
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.CrawlerUtil;

public class TheGioiGearPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private int lastPage;
    
    public TheGioiGearPageCrawler(String url) {
        this.url = url;
    }
    
    public void getLastPage(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String line = "";
            StringBuilder document = new StringBuilder("");
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<div class=\"col-lg-8 col-md-8 col-sm-6 col-xs-12 text-center\">")) {
                    isStart = true;
                }
                if (isStart && line.contains("<div class=\"col-lg-2 col-md-2 col-sm-3 hidden-xs\">")) {
                    break;
                }
                if (isStart) {
//                    System.out.println(line);
                    document.append(line.trim());
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
        
        document = document.trim().replaceAll("&hellip;", "");
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Iterator<XMLEvent> events = autoAddMissingTag(eventReader);
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null && attrClass != null) {
                        lastPage = CrawlerUtil.getPage(attrHref.getValue(), "?page=");
                    }
                }
            }
        }
        System.out.println("URL: " + url + ", Last page: " + lastPage);
    }

    @Override
    public void run() {
        getLastPage(url);
        for (int i = 0; i <= lastPage; i++) {
            String pageUrl = url + "?page=" + i;
            Thread dataCrawler = new Thread(new TheGioiGearCrawler(pageUrl));
            dataCrawler.start();
        }
    }
    
}
