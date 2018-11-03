
package crawler;

public class MagicalMainCrawler {
    
    public static void main(String[] args) {
        String[] cate = {
            "https://www.adayroi.com/chuot-may-tinh-c378", 
            "https://www.adayroi.com/ban-phim-c377", 
            "https://www.adayroi.com/mieng-lot-chuot-c379",
            "https://www.adayroi.com/tai-nghe-may-tinh-c374"
        };
        for (String url : cate) {
            Thread crawler = new Thread(new ADayRoiPageCrawler(url));
            crawler.start();
        }

        String[] cate1 = {
            "https://thegioigear.com/collections/ban-phim", 
            "https://thegioigear.com/collections/tai-nghe-choi-game", 
            "https://thegioigear.com/collections/lot-chuot",
            "https://thegioigear.com/collections/chuot"
        };
        for (String url : cate1) {
            Thread crawler = new Thread(new TheGioiGearPageCrawler(url));
            crawler.start();
        }
    }
    
}
