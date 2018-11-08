
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
        CrawlerDomRef crawlerDomRefReader = new CrawlerDomRef();
        try {
            crawlerDomRefReader.readXMLRefFile();
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(MagicalMainCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (String link : AppConstant.ADayRoiLinks) {
            Thread crawler = new Thread(new ADayRoiPageCrawler(link));
            crawler.start();
        }
        for (String link : AppConstant.TheGioiGearLinks) {
            Thread crawler = new Thread(new TheGioiGearPageCrawler(link));
            crawler.start();
        }
        
//        String[] cate = {
//            "https://www.adayroi.com/chuot-may-tinh-c378", 
//            "https://www.adayroi.com/ban-phim-c377", 
//            "https://www.adayroi.com/mieng-lot-chuot-c379",
//            "Stringhttps://www.adayroi.com/tai-nghe-may-tinh-c374"
//        };
//        for (String url : cate) {
//            Thread crawler = new Thread(new ADayRoiPageCrawler(url));
//            crawler.start();
//        }

//        String[] cate1 = {
//            "https://thegioigear.com/collections/ban-phim", 
//            "https://thegioigear.com/collections/tai-nghe-choi-game", 
//            "https://thegioigear.com/collections/lot-chuot",
//            "https://thegioigear.com/collections/chuot"
//        };
//        for (String url : cate1) {
//            Thread crawler = new Thread(new TheGioiGearPageCrawler(url));
//            crawler.start();
//        }
    }
    
}
