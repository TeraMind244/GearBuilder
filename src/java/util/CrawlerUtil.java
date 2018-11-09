
package util;

public class CrawlerUtil {
    
//    public static int computeMatchingDensity(String a, String b) {
//        int n = a.length();
//        int m = b.length();
//        int dp[][] = new int[n + 1][m + 1];
//        
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                if (a.charAt(i) == b.charAt(j)) {
//                    dp[i + 1][j + 1] = dp[i][j] + 1;
//                } else {
//                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
//                }
//            }
//        }
//        return dp[n][m];
//    }
    
//    public static double computePercentageMatchingDesity(String a, String b) {
//        return computeMatchingDensity(a, b) * 100.0 / Math.min(a.length(), b.length());
//    }
    
    public static int hashingString(String content) {
        int mod = 1000000004;
        int base = 44449;
        
        int hashValue = 0;
        for (int i = 0; i < content.length(); i++) {
            hashValue = (int)(((long)hashValue * base + (long)content.charAt(i)) % mod);
        }
        return hashValue;
    }
    
    public static String toRawString(String str) {
        str = str
                .replaceAll("[áàạảãăắằặẳẵâấầậẩẫ]", "a")
                .replaceAll("[ÁÀẠẢÃĂẮẰẶẲẴÂẤẦẬẨẪ]", "A")
                .replaceAll("[íìịỉĩ]", "i")
                .replaceAll("[ÍÌỊỈĨ]", "I")
                .replaceAll("[úùụủũưứừựửữ]", "u")
                .replaceAll("[ÚÙỤỦŨƯỨỪỰỬỮ]", "U")
                .replaceAll("[éèẹẻẽêếềệểễ]", "e")
                .replaceAll("[ÉÈẸẺẼÊẾỀỆỂỄ]", "E")
                .replaceAll("[óòọỏõôốồộổỗơớờợởỡ]", "o")
                .replaceAll("[ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ]", "O")
                .replaceAll("[ýỳỵỷỹ]", "y")
                .replaceAll("[ýỳỵỷỹ]", "Y")
                .replaceAll("[đ]", "d")
                .replaceAll("[Đ]", "D")
                .replaceAll("à", "a")
                .replaceAll("í", "i")
                .replaceAll("[̣̀́]", "")
                ;
        
        return str;
    }
    
    public static Integer convertToPrice(String priceStr) {
        StringBuilder newStr = new StringBuilder("");
        for (int i = 0; i < priceStr.length(); i++) {
            if (Character.isDigit(priceStr.charAt(i))) {
                newStr.append(priceStr.charAt(i));
            }
        }
        if (newStr.toString().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(newStr.toString());
    }
    
    public static String getImgUrlFromStyle(String styleStr) {
        String backgroundStr = "background:url(";
        String importantStr = ") !important";
        return styleStr.substring(styleStr.indexOf(backgroundStr) + backgroundStr.length(), styleStr.indexOf(importantStr));
    }
    
    public static int getPage(String url, String pagePrefix) {
        String newStr = url.substring(url.indexOf(pagePrefix) + pagePrefix.length(), url.length());
        return Integer.parseInt(newStr);
    }
    
    public static String categorizeType(String gearName, String defaultType) {
        String mouse = "chuot";
        String keyboard = "ban-phim";
        String pad = "pad";
        String headset = "tai-nghe";
        String other = "other";
        
        gearName = toRawString(gearName.toLowerCase());
        
        if (gearName.contains("combo") || gearName.contains("bo phim chuot") 
                || gearName.contains("tang") || gearName.contains("gamepad")
                || gearName.contains("bo ban phim") || gearName.contains("bang ve")
                || gearName.contains("bang hat sang") || gearName.contains("numpad")
                || gearName.contains("bo chuot")) {
            return other;
        }

        if (gearName.contains("ban phim")) {
            return keyboard;
        }
        if (gearName.contains("pad") || gearName.contains("lot chuot") || gearName.contains("ban di")) {
            return pad;
        }
        if (gearName.contains("chuot") || gearName.contains("mouse") || gearName.contains("optical")) {
            return mouse;
        }
        if (gearName.contains("tai nghe")) {
            return headset;
        }
        
        return defaultType;
    }
    
    public static String getUrl(String url, String domain) {
        if (url.startsWith("//")) {
            return url;
        } else {
            return domain + url;
        }
    }
    
    public static String removeParamFromUrl(String url) {
        if (url.contains("?")) {
            return url.substring(0, url.indexOf("?"));
        } else {
            return url;
        }
    }
    
}
