package me.aligo.idle.model;

public class ListSection extends BaseModel {
    
    private String title;
    
    public ListSection(String title) {
        this.title = title;
    }
    
    @Override
    public boolean isListSection() {
        return true;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
