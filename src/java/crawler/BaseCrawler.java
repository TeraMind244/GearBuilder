
package crawler;

import com.sun.xml.internal.fastinfoset.stax.events.EndElementEvent;
import com.sun.xml.internal.stream.events.CharacterEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import util.constant.AppConstant;

public abstract class BaseCrawler implements Runnable {
    
    protected String url;
    protected static String schemaFilePath;
    private static ExecutorService pool;
    private static int THREAD_POOL_COUNT = 0;
    private final static Object LOCK = new Object();
    private static boolean isAllFinished = false;
    
    static {
        pool = Executors.newFixedThreadPool(AppConstant.threadPool);
    }
    
    public BaseCrawler(String url) {
        this.url = url;
    }

    public synchronized static ExecutorService getPool() {
        if (pool == null) {
            pool = Executors.newFixedThreadPool(AppConstant.threadPool);
        }
        return pool;
    }

    public static void setSchemaFilePath(String schemaFilePath) {
        BaseCrawler.schemaFilePath = schemaFilePath;
    }
    
    protected BufferedReader getBufferReaderForURL(String urlString)
            throws MalformedURLException, IOException, UnsupportedEncodingException {
        URL urlLink = new URL(urlString);
        URLConnection connection = urlLink.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Window NT 6.1; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }
    
    protected XMLEventReader parseStringToXMLEventReader(String xmlSection)
            throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        return reader;
    }
    
    protected abstract void staxParserForDocument(String document) 
            throws UnsupportedEncodingException, XMLStreamException;
    
    protected Iterator<XMLEvent> autoAddMissingTag(XMLEventReader reader) {
        List<XMLEvent> events = new ArrayList<>();
        int endTagMarker = 0;
        
        while (endTagMarker >= 0) {
            XMLEvent event = null;
            try {
                event = reader.nextEvent();
//                System.out.println(event);
            } catch (XMLStreamException ex) {
                String msg = ex.getMessage();
                String msgErrorString = "The element type \"";
                if (msg.contains(msgErrorString)) {
                    String missingTagName = msg.substring(msg.indexOf(msgErrorString) + msgErrorString.length()
                                                        , msg.indexOf("\" must be terminated"));
                    EndElement missingTag = new EndElementEvent(new QName(missingTagName));
                    event = missingTag;
                } else {
                    break;
                }
            } catch (Exception ex) {
                break;
            }
            
            if (event != null) {
                if (event.isStartElement()) {
                    endTagMarker++;
                } else if (event.isEndElement()) {
                    endTagMarker--;
                } else if (event.isCharacters()) {
                    String str = event.asCharacters().getData().trim();
                    if (str.length() == 0) {
                        continue;
                    } else {
                        event = new CharacterEvent(str);
                    }
                }
                if (endTagMarker >= 0) {
                    events.add(event);
                }
            }
        }
        return events.iterator();
    }
    
    public String getAttribute(StartElement startElement, String name) {
        Attribute attribute = startElement.getAttributeByName(new QName(name));
        if (attribute == null) {
            return "";
        } else {
            return attribute.getValue().trim();
        }
    }
    
    public String getHtmlFragment(BufferedReader reader, String startFragmentMark, String endFragmentMark)
            throws IOException {
        String line = "";
        StringBuilder document = new StringBuilder("");
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (line.contains(startFragmentMark)) {
                isStart = true;
            }
            if (isStart && line.contains(endFragmentMark)) {
                break;
            }
            if (isStart) {
//                    System.out.println(line);
                document.append(line.trim());
            }
        }
        return document.toString();
    }
    
    public void createThread() {
        synchronized (LOCK) {
            isAllFinished = false;
            THREAD_POOL_COUNT++;
        }
    }
    
    public void finishPageCrawler() {
        synchronized (LOCK) {
            THREAD_POOL_COUNT--;
        }
    }
    
    public void finishCrawl() {
        synchronized (LOCK) {
            THREAD_POOL_COUNT--;
            if (THREAD_POOL_COUNT == 0) {
                System.out.println("Finish all crawler!!!");
                isAllFinished = true;
            }
        }
    }
    
    public static void shutdownAndAwaitTermination() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static boolean isAllFinished() {
        return isAllFinished;
    }

    public static void setIsAllFinished(boolean isAllFinished) {
        BaseCrawler.isAllFinished = isAllFinished;
    }
    
}
