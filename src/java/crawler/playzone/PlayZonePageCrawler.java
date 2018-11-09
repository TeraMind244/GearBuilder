
package crawler.playzone;

import crawler.BaseCrawler;
import crawler.adayroi.ADayRoiPageCrawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.CrawlerUtil;

public class PlayZonePageCrawler extends BaseCrawler implements Runnable {

    private String defaultType;
    private int lastPage;
    private ExecutorService pool;
    
    public PlayZonePageCrawler(String url, String defaultType, ExecutorService pool) {
        super(url);
        this.defaultType = defaultType;
        this.pool = pool;
    }
    
    public void getLastPage(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String fragment = getHtmlFragment(reader, 
                    "<div class=\"bottom_buttons pagination_holder\">", 
                    "// Equal height on product items");
            staxParserForDocument(fragment);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(ADayRoiPageCrawler.class.getName()).log(Level.SEVERE, url, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ADayRoiPageCrawler.class.getName()).log(Level.SEVERE, url, ex);
                }
            }
        }
    }
    
    @Override
    protected void staxParserForDocument(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        String startDocument = "<div class=\"bottom_buttons pagination_holder\">";
        document = document.trim()
                .substring(document.indexOf(startDocument), document.length())
                .replaceAll("&page", "&amp;page")
                .replaceAll("&nbsp;", "");
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Iterator<XMLEvent> events = autoAddMissingTag(eventReader);
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                
                if ("a".equals(tagName)) {
                    lastPage = CrawlerUtil.getPage(getAttribute(startElement, "href"), "?page=");
                }
            }
        }
        System.out.println("URL: " + url + ", Last page: " + lastPage);
    }

    @Override
    public void run() {
        getLastPage(url);
        for (int i = 0; i < lastPage; i++) {
            String pageUrl = url + "?page=" + (i + 1);
            Thread dataCrawler = new Thread(new PlayZoneCrawler(pageUrl, defaultType));
            pool.execute(dataCrawler);
        }
    }
    
}
