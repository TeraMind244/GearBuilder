package generated.gear;

import java.io.Serializable;

public class Gear implements Serializable {

    private int hashStr;
    private String gearName;
    private String source;
    private String gearUrl;
    private String imgUrl;
    private Integer price;
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

}
