
package gear;

import generated.gear.Gear;
import java.io.Serializable;
import java.util.List;
import util.AppConstant;

public class SearchGearView implements Serializable {
    
    private List<Gear> gears;
    private int currentPage; // zero based
    private int resultCount;
    private int maxPage; //zero based

    public SearchGearView() {
        gears = null;
        currentPage = 0;
    }

    public SearchGearView(List<Gear> gears, int currentPage, int resultCount) {
        this.gears = gears;
        this.currentPage = currentPage;
        this.resultCount = resultCount;
    }

    public List<Gear> getGears() {
        return gears;
    }

    public void setGears(List<Gear> gears) {
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

    public int getMaxPage() {
        if (resultCount == 0) {
            return 0;
        }
        return (resultCount - 1) / AppConstant.pageSize;
    }
    
}
