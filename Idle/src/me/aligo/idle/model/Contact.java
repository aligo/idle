package me.aligo.idle.model;

public class Contact extends BaseModel  {
    
    private String displayName;
    
    public Contact(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    public void setTitle(String displayName) {
        this.displayName = displayName;
    }
}
