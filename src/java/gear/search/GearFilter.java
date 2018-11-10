
package gear.search;

public class GearFilter {
    
    private String name;
    private String type;
    private String sortBy;

    public GearFilter() {
    }

    public GearFilter(String name, String type, String sortBy) {
        this.name = name;
        this.type = type;
        this.sortBy = sortBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getQueryStatement() {
        String query = "";
        
        String sortStr = "ORDER BY ";
        switch (sortBy) {
            case "nameAsc":
                sortStr += "GearName ASC";
                break;
            case "nameDesc":
                sortStr += "GearName DESC";
                break;
            case "priceAsc":
                sortStr += "Price ASC";
                break;
            case "priceDesc":
                sortStr += "Price DESC";
                break;
            default:
                sortStr += "GearName ASC";
        }
        
        if (name.isEmpty() && type.equals("all")) {
            return sortStr;
        } else {
            query = "WHERE ";
        }
        
        String nameStr = "";
        String typeStr = "";
        String and = "";
        
        if (name.isEmpty()) {
            nameStr = " ";
        } else {
            nameStr = "GearName LIKE :name ";
        }
        
        if (!name.isEmpty() && !type.equals("all")) {
            and = "AND ";
        }
        
        if (type.equals("all")) {
            typeStr = " ";
        } else {
            typeStr = "Type = :type ";
        }
        
        query += nameStr + and + typeStr + sortStr;
        
        return query;
    }
    
}
