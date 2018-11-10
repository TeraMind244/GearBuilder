
package gear.builder;

import generated.gear.Gear;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import util.GearUtil;


@XmlRootElement(name="GearSet")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GearSet", propOrder = {
    "mouse",
    "keyBoard",
    "pad",
    "headset",
    "viewValue"
})
public class GearSet implements Serializable {
    
    @XmlElement(name="Mouse")
    private Gear mouse;
    
    @XmlElement(name="KeyBoard")
    private Gear keyBoard;
    
    @XmlElement(name="Pad")
    private Gear pad;
    
    @XmlElement(name="HeadSet")
    private Gear headset;

    public GearSet() {
    }

    public GearSet(Gear mouse, Gear keyBoard, Gear pad, Gear headset) {
        this.mouse = mouse;
        this.keyBoard = keyBoard;
        this.pad = pad;
        this.headset = headset;
    }

    public Gear getMouse() {
        return mouse;
    }

    public void setMouse(Gear mouse) {
        this.mouse = mouse;
    }

    public Gear getKeyBoard() {
        return keyBoard;
    }

    public void setKeyBoard(Gear keyBoard) {
        this.keyBoard = keyBoard;
    }

    public Gear getPad() {
        return pad;
    }

    public void setPad(Gear pad) {
        this.pad = pad;
    }

    public Gear getHeadset() {
        return headset;
    }

    public void setHeadset(Gear headset) {
        this.headset = headset;
    }
    
    public int getValue() {
        return mouse.getPrice() + keyBoard.getPrice() + pad.getPrice() + headset.getPrice();
    }
    
    @XmlElement(name="Value")
    public String getViewValue() {
        return GearUtil.getViewPrice(getValue());
    }
    
    public static void main(String[] args) {
        try {
            Gear gear1 = new Gear(3103778, "Razer FireFly", "https://www.playzone.vn/", 
                    "https://thegioigear.com/collections/lot-chuot/products/firefly", 
                    "//hstatic.net/809/1000037809/1/2015/9-18/brand-product-page-hero_firefly_06-01_large.png", 
                    1590000, "chuot");
            Gear gear2 = new Gear(295452230, "Bàn di cảm ứng Apple Magic Trackpad 2", "https://www.adayroi.com/", 
                    "https://www.adayroi.com/ban-di-cam-ung-apple-magic-trackpad-2-p-PRI849280", 
                    "https://media-static-adayroi.cdn.vccloud.vn/240_240/80/h45/hac/9344985399326.jpg", 
                    3190000, "ban-phim");
            Gear gear3 = new Gear(475891342, "Bàn di chuột + Sạc không dây MM1000 Qi® Wireless Charging", "https://www.playzone.vn/", 
                    "https://thegioigear.com/collections/lot-chuot/products/firefly", 
                    "//hstatic.net/809/1000037809/1/2015/9-18/brand-product-page-hero_firefly_06-01_large.png", 
                    1590000, "pad");
            Gear gear4 = new Gear(965779971, "Bàn Di Chuột Alienware Natrix RGB V2", "https://www.playzone.vn/", 
                    "https://thegioigear.com/collections/lot-chuot/products/firefly", 
                    "//hstatic.net/809/1000037809/1/2015/9-18/brand-product-page-hero_firefly_06-01_large.png", 
                    1590000, "tai-nghe");
            
            JAXBContext jaxb = JAXBContext.newInstance(GearSet.class);
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            
            GearSet gearSet = new GearSet(gear1, gear2, gear3, gear4);
            
            StringWriter sw = new StringWriter();
            marshaller.marshal(gearSet, sw);
            
            XMLEventReader eventReader = parseStringToXMLEventReader(sw.toString());
            
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                System.out.println(event);
            }
        } catch (UnsupportedEncodingException | XMLStreamException | JAXBException ex) {
            Logger.getLogger(GearSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static XMLEventReader parseStringToXMLEventReader(String xmlSection)
            throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        return reader;
    }

}
