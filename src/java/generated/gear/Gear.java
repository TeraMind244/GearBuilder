
package generated.gear;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import util.GearUtil;

@XmlRootElement(name="Gear")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Gear", propOrder = {
    "gearName",
    "source",
    "gearUrl",
    "imgUrl",
    "viewPrice",
    "viewType"
})
public class Gear implements Serializable {
    
    @XmlAttribute(name="xmlns")
    private String xmlns = "http://www.netbeans.org/schema/Gear";
    
    @XmlAttribute(name="xmlns:xsi")
    private String xsi = "http://www.w3.org/2001/XMLSchema-instance";
    
    @XmlAttribute(name="xsi:schemaLocation")
    private String schemaLocation = "http://www.netbeans.org/schema/Gear web/schema/gear.xsd";

    @XmlAttribute(name="HashStr")
    private int hashStr;
    
    @XmlElement(name="GearName")
    private String gearName;
    
    @XmlElement(name="Source")
    private String source;
    
    @XmlElement(name="GearUrl")
    private String gearUrl;
    
    @XmlElement(name="ImgUrl")
    private String imgUrl;
    
    @XmlTransient
    private Integer price;
    
    @XmlTransient
    private String type;

    public Gear() {
    }

    public Gear(int hashStr) {
        this.hashStr = hashStr;
    }

    public Gear(int hashStr, String gearName, String source, String gearUrl, String imgUrl, Integer price, String type) {
        this.hashStr = hashStr;
        this.gearName = gearName;
        this.source = source;
        this.gearUrl = gearUrl;
        this.imgUrl = imgUrl;
        this.price = price;
        this.type = type;
    }

    public int getHashStr() {
        return this.hashStr;
    }

    public void setHashStr(int hashStr) {
        this.hashStr = hashStr;
    }
    
    public String getGearName() {
        return this.gearName;
    }

    public void setGearName(String gearName) {
        this.gearName = gearName;
    }
    
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    public String getGearUrl() {
        return this.gearUrl;
    }

    public void setGearUrl(String gearUrl) {
        this.gearUrl = gearUrl;
    }
    
    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @XmlElement(name="Price")
    public String getViewPrice() {
        return GearUtil.getViewPrice(this.price);
    }
    
    @XmlElement(name="Type")
    public String getViewType() {
        switch (type) {
            case "chuot":
                return "Chuột";
            case "ban-phim":
                return "Bàn Phím";
            case "pad":
                return "Pad Chuột";
            case "tai-nghe":
                return "Tai nghe";
            default:
                return "Khác";
        }
    }
    
//    public static void main(String[] args) {
//        try {
//            Gear gear = new Gear(3103778, "Razer FireFly", "thegioigear.com", 
//                    "https://thegioigear.com/collections/lot-chuot/products/firefly", 
//                    "//hstatic.net/809/1000037809/1/2015/9-18/brand-product-page-hero_firefly_06-01_large.png", 
//                    1590000, "other");
//            
//            JAXBContext jaxb = JAXBContext.newInstance(Gear.class);
//            Marshaller marshaller = jaxb.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            
//            StringWriter sw = new StringWriter();
//            marshaller.marshal(gear, sw);
//            
//            XMLEventReader eventReader = parseStringToXMLEventReader(sw.toString());
//            
//            while (eventReader.hasNext()) {
//                XMLEvent event = (XMLEvent) eventReader.next();
//                System.out.println(event);
//            }
//        } catch (JAXBException ex) {
//            ex.printStackTrace();
//        } catch (UnsupportedEncodingException | XMLStreamException ex) {
//            Logger.getLogger(Gear.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public static XMLEventReader parseStringToXMLEventReader(String xmlSection)
//            throws UnsupportedEncodingException, XMLStreamException {
//        byte[] byteArray = xmlSection.getBytes("UTF-8");
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
//        XMLInputFactory factory = XMLInputFactory.newInstance();
//        XMLEventReader reader = factory.createXMLEventReader(inputStream);
//        return reader;
//    }
    
}
