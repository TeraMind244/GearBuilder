
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

public class ADayRoiPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private int lastPage;
    
    public ADayRoiPageCrawler(String url) {
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
                if (line.contains("<nav class=\"Page navigation\">")) {
                    isStart = true;
                }
                if (isStart && line.contains(".product-item_info-text-sale span")) {
                    break;
                }
                if (isStart) {
//                    System.out.println(line);
                    document.append(line.trim());
                }
            }
            staxParserForDocument(document.toString());
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(ADayRoiPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ADayRoiPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    protected void staxParserForDocument(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        String startDocument = "<nav class=\"Page navigation\">";
        String endDocument = "</section><div class=\"content\"><style>";
        if (!document.contains(startDocument)) {
            lastPage = 0;
            System.out.println("URL: " + url + ", Last page: " + lastPage);
            return;
        }
        document = document.trim().substring(document.indexOf(startDocument), document.length() - endDocument.length());
        document = document.replaceAll("&page", "&amp;page").replaceAll("&nbsp;", "");
//        document = document.trim();
        
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
                    Attribute attrAria = startElement.getAttributeByName(new QName("aria-label"));
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    if (attrAria != null) {
                        lastPage = CrawlerUtil.getPage(attrHref.getValue(), "&page=");
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
            Thread dataCrawler = new Thread(new ADayRoiCrawler(pageUrl));
            dataCrawler.start();
        }
        
    }
    
}
