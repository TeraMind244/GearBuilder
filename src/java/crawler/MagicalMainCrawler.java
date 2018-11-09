
package crawler;

import crawler.adayroi.ADayRoiPageCrawler;
import crawler.playzone.PlayZonePageCrawler;
import crawler.thegioigear.TheGioiGearPageCrawler;
import handler.CrawlerDomRef;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import util.constant.ADayRoiConstant;
import util.constant.PlayZoneConstant;
import util.constant.TheGioiGearConstant;

public class MagicalMainCrawler {
    
    public static void main(String[] args) {
        try {
            CrawlerDomRef.readXMLRefFile("web/xml/crawlerRef.xml");
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(MagicalMainCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ExecutorService pool = Executors.newFixedThreadPool(8);
        
        for (String link : ADayRoiConstant.ADayRoiLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new ADayRoiPageCrawler(urlAndType[0], urlAndType[1], pool));
            pool.execute(crawler);
        }
        for (String link : TheGioiGearConstant.TheGioiGearLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new TheGioiGearPageCrawler(urlAndType[0], urlAndType[1], pool));
            pool.execute(crawler);
        }
        for (String link : PlayZoneConstant.PlayZoneLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new PlayZonePageCrawler(urlAndType[0], urlAndType[1], pool));
            pool.execute(crawler);
        }


//        try {
//            ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
//            CountDownLatch latch = new CountDownLatch(2);
//            for (int i = 0; i < 2; i++) {
//                WORKER_THREAD_POOL.submit(() -> {
//
//                    // ...
//                    latch.countDown();
//
//                });
//            }
//
//            // wait for the latch to be decremented by the two remaining threads
//            latch.await();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        
    }
    
}
