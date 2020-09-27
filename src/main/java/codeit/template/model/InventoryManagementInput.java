package codeit.template.model;

import java.util.ArrayList;

public class InventoryManagementInput {
    String searchItemName;
    ArrayList<String> items;

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
