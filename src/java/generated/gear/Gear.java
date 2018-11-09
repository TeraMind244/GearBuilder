
package generated.gear;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import util.GearUtil;

@XmlRootElement(name="Gear")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Gear", propOrder = {
//    "hashStr",
    "gearName",
    "source",
    "gearUrl",
    "imgUrl",
    "viewPrice",
    "viewType"
})
public class Gear implements Serializable {

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
    
}
