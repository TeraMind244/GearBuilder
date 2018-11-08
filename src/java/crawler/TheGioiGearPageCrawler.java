
package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.AppConstant;
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
            String fragment = getHtmlFragment(reader, 
                    AppConstant.TheGioiGearPageCrawlerStartMark, 
                    AppConstant.TheGioiGearPageCrawlerEndMark);
            staxParserForDocument(fragment);
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
                    String attrHref = getAttribute(startElement, "href");
                    if (attrHref.length() > 0 && getAttribute(startElement, "class").length() > 0) {
                        lastPage = CrawlerUtil.getPage(attrHref, "?page=");
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
