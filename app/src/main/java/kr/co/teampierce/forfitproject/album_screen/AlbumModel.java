package kr.co.teampierce.forfitproject.album_screen;

/**
 * Created by banggijin on 2017. 1. 20..
 */

public class AlbumModel {

    private String image_title;
    private Integer image_id;
    //private static boolean SelectMode=false;
    private boolean isSelected = false;
    private boolean selectMode=false;

    public String getImage_title() {

        return image_title;

    }


    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public boolean isSelectMode(){
        return selectMode;
    }
    public void setSelectMode(boolean mode){
        selectMode=mode;
    }
}