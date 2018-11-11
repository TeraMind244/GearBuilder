
package restful.services;

import crawler.BaseCrawler;
import crawler.adayroi.ADayRoiPageCrawler;
import crawler.playzone.PlayZonePageCrawler;
import crawler.thegioigear.TheGioiGearPageCrawler;
import handler.CrawlerDomRef;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import listener.AppListener;
import org.xml.sax.SAXException;
import util.constant.ADayRoiConstant;
import util.constant.AppConstant;
import util.constant.PlayZoneConstant;
import util.constant.TheGioiGearConstant;

@Path("admin")
public class AdminResource {

    @Context
    private UriInfo context;
    
    @Context
    private ServletContext servletContext;

    public AdminResource() {
    }

    @Path("/getDataRef")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDataRef() {
        try {
            String pathToXML = servletContext.getRealPath("/") + "xml/crawlerRef.xml";
            CrawlerDomRef.readXMLRefFile(pathToXML);
            return "Data reference is refreshed!";
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(AppListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error! Read data reference failed! Something went wrong!";
    }
    
    @Path("/startCrawler")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String startCrawler() {
        if (!AppConstant.isRead) {
            return "Please read reference data before start crawling data!";
        }
        
        String realPath = servletContext.getRealPath("/");
        BaseCrawler.setSchemaFilePath(realPath + "schema/gear.xsd");
        
        ExecutorService pool = BaseCrawler.getPool();
        for (String link : ADayRoiConstant.ADayRoiLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new ADayRoiPageCrawler(urlAndType[0], urlAndType[1]));
            pool.execute(crawler);
        }
        for (String link : TheGioiGearConstant.TheGioiGearLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new TheGioiGearPageCrawler(urlAndType[0], urlAndType[1]));
            pool.execute(crawler);
        }
        for (String link : PlayZoneConstant.PlayZoneLinks) {
            String[] urlAndType = link.split(";");
            Thread crawler = new Thread(new PlayZonePageCrawler(urlAndType[0], urlAndType[1]));
            pool.execute(crawler);
        }
        
        long timeOut = AppConstant.crawlerTimeout * 60 * 1000;
        
        while (!BaseCrawler.isAllFinished() && timeOut > 0) { // Recheck every second
            try {
                Thread.sleep(1000);
                timeOut -= 1000;
            } catch (InterruptedException ex) {
                Logger.getLogger(AdminResource.class.getName()).log(Level.SEVERE, null, ex);
                return "Error! Something went wrong!";
            }
        }
        if (timeOut > 0) {
            return "Crawlers're Finished!";
        } else {
            return "Error! Crawlers're timeout!";
        }
        
    }

}
