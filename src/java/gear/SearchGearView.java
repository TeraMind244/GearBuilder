
package gear;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import util.constant.AppConstant;


@XmlRootElement(name = "SearchGearView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchGearView", propOrder = {
    "gears",
    "currentPage",
    "resultCount",
    "maxPage",
})
public class SearchGearView implements Serializable {
    
    @XmlElement(name="GearList")
    private GearList gears;
    
    @XmlElement(name="CurrentPage")
    private int currentPage; // zero based
    
    @XmlElement(name="ResultCount")
    private int resultCount;

    public SearchGearView() {
        gears = null;
        currentPage = 0;
    }

    public SearchGearView(GearList gears, int currentPage, int resultCount) {
        this.gears = gears;
        this.currentPage = currentPage;
        this.resultCount = resultCount;
    }

    public GearList getGears() {
        return gears;
    }

    public void setGears(GearList gears) {
        this.gears = gears;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
    
    @XmlElement(name="MaxPage")
    public int getMaxPage() {
        if (resultCount == 0) {
            return 0;
        }
        return (resultCount - 1) / AppConstant.pageSize;
    }
    
//    public static void main(String[] args) {
//        try {
//            Gear gear1 = new Gear(3103778, "Razer FireFly", "thegioigear.com", 
//                    "https://thegioigear.com/collections/lot-chuot/products/firefly", 
//                    "//hstatic.net/809/1000037809/1/2015/9-18/brand-product-page-hero_firefly_06-01_large.png", 
//                    1590000, "other");
//            Gear gear2 = new Gear(3396323, "Bàn phím cơ Corsair K95 Platinum RGB MX Speed CH-9127014-NA", "adayroi.com", 
//                    "https://www.adayroi.com/ban-phim-co-corsair-k95-platinum-rgb-mx-speed-ch-9127014-na-p-1349286?offer=1349286_EEC", 
//                    "https://media-static-adayroi.cdn.vccloud.vn/240_240/80/h32/h28/14814317051934.jpg", 
//                    4990000, "ban-phim");
//            
//            List<Gear> gears = new ArrayList<>();
//            gears.add(gear1);
//            gears.add(gear2);
//            
//            GearList gearList = new GearList(gears);
//            
//            SearchGearView gear = new SearchGearView(gearList, 5, 347);
//            
//            JAXBContext jaxb = JAXBContext.newInstance(SearchGearView.class);
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
