
package gear;

import generated.gear.Gear;
import java.io.Serializable;
import util.GearUtil;

public class GearSet implements Serializable {
    
    private Gear mouse;
    private Gear pad;
    private Gear keyBoard;
    private Gear headset;

    public GearSet() {
    }

    public GearSet(Gear mouse, Gear pad, Gear keyBoard, Gear headset) {
        this.mouse = mouse;
        this.pad = pad;
        this.keyBoard = keyBoard;
        this.headset = headset;
    }

    public Gear getMouse() {
        return mouse;
    }

    public void setMouse(Gear mouse) {
        this.mouse = mouse;
    }

    public Gear getPad() {
        return pad;
    }

    public void setPad(Gear pad) {
        this.pad = pad;
    }

    public Gear getKeyBoard() {
        return keyBoard;
    }

    public void setKeyBoard(Gear keyBoard) {
        this.keyBoard = keyBoard;
    }

    public Gear getHeadset() {
        return headset;
    }

    public void setHeadset(Gear headset) {
        this.headset = headset;
    }
    
    public int getValue() {
        return mouse.getPrice() + pad.getPrice() + keyBoard.getPrice() + headset.getPrice();
    }
    
    public String getViewValue() {
        return GearUtil.getViewPrice(getValue());
    }

}
