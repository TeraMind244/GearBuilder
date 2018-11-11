
package listener;

import handler.CrawlerDomRef;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.xml.sax.SAXException;
import util.HibernateUtil;

public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.out.println("App is started!!!");
            String pathToXML = sce.getServletContext().getRealPath("/") + "xml/crawlerRef.xml";
            CrawlerDomRef.readXMLRefFile(pathToXML);
            System.out.println("Data reference is loaded!!!");
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(AppListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.close();
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(!sessionFactory.isClosed()){
            sessionFactory.close();
        }
    }
}
