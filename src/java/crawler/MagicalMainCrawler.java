
package crawler;

import handler.CrawlerDomRef;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import util.AppConstant;

public class MagicalMainCrawler {
    
    public static void main(String[] args) {
        try {
            CrawlerDomRef.readXMLRefFile("web/xml/crawlerRef.xml");
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(MagicalMainCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (String link : AppConstant.ADayRoiLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new ADayRoiPageCrawler(urlAndType[0], urlAndType[1]));
            crawler.start();
        }
        for (String link : AppConstant.TheGioiGearLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new TheGioiGearPageCrawler(urlAndType[0], urlAndType[1]));
            crawler.start();
        }
    }
    
}
