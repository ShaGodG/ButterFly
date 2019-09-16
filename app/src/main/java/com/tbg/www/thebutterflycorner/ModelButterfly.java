package com.tbg.www.thebutterflycorner;

public class ModelButterfly {

    String Name;
    String imgName;
    String Desc;

    public ModelButterfly(String name, String imgName, String desc) {
        Name = name;
        this.imgName = imgName;
        Desc = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
