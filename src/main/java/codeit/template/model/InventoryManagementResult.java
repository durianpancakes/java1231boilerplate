package codeit.template.model;

import java.util.ArrayList;

public class InventoryManagementResult {
    String searchItemName;
    ArrayList<String> searchResult;

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public ArrayList<String> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(ArrayList<String> searchResult) {
        this.searchResult = searchResult;
    }
}
