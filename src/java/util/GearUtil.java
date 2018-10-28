
package util;

import java.io.Serializable;

public class GearUtil implements Serializable {
    
    public static String getViewPrice(int price) {
        int m = price / 1000000;
        int k = (price - m * 1000000) / 1000;
        int remain = price % 1000;
        
        if (price < 1000) {
            return remain + "\u20ab";
        }
        
        if (price < 1000000) {
            return k + "." + String.format("%03d", remain) + "\u20ab";
        }
        
        return m + "." + String.format("%03d", k) + "." + String.format("%03d", remain) + "\u20ab";
    }
    
}
