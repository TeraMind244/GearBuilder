
package crawler.adayroi;

import crawler.BaseCrawler;
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
import util.CrawlerUtil;
import util.constant.ADayRoiConstant;

public class ADayRoiPageCrawler extends BaseCrawler {

    private String defaultType;
    private int lastPage;
    
    public ADayRoiPageCrawler(String url, String defaultType) {
        super(url);
        this.defaultType = defaultType;
    }

    public void getLastPage(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferReaderForURL(url);
            String fragment = getHtmlFragment(reader, 
                    ADayRoiConstant.ADayRoiPageCrawlerStartMark, 
                    ADayRoiConstant.ADayRoiPageCrawlerEndMark);
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
        String startDocument = ADayRoiConstant.ADayRoiPageCrawlerStartFragment;
        String endDocument = ADayRoiConstant.ADayRoiPageCrawlerEndFragment;
        if (!document.contains(startDocument)) {
            lastPage = 0;
            System.out.println("URL: " + url + ", Last page: " + lastPage);
            return;
        }
        document = document.trim()
                .substring(document.indexOf(startDocument), document.length() - endDocument.length())
                .replaceAll("&page", "&amp;page")
                .replaceAll("&nbsp;", "");
//        System.out.println(document);
        
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Iterator<XMLEvent> events = autoAddMissingTag(eventReader);
        
        while (events.hasNext()) {
            XMLEvent event = (XMLEvent) events.next();
//            System.out.println(event);
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                
                if ("a".equals(tagName)) {
                    if (getAttribute(startElement, "aria-label").length() > 0) {
                        lastPage = CrawlerUtil.getPage(getAttribute(startElement, "href"), "&page=");
                    }
                }
            }
        }
        System.out.println("URL: " + url + ", Last page: " + lastPage);
    }

    @Override
    public void run() {
        createThread();
        getLastPage(url);
        for (int i = 0; i <= lastPage; i++) {
            String pageUrl = url + "?page=" + i;
            Thread dataCrawler = new Thread(new ADayRoiCrawler(pageUrl, defaultType));
            BaseCrawler.getPool().execute(dataCrawler);
        }
        finishPageCrawler();
    }
    
}
