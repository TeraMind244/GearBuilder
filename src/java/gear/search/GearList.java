
package gear.search;

import gear.generated.Gear;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GearList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GearList", propOrder = {
    "gearList",
})
public class GearList implements Serializable {
    
    @XmlElement(name="Gear")
    private List<Gear> gearList;

    public GearList() {
    }

    public GearList(List<Gear> gearList) {
        this.gearList = gearList;
    }

    public List<Gear> getGearList() {
        return gearList;
    }

    public void setGearList(List<Gear> gearList) {
        this.gearList = gearList;
    }
    
}
