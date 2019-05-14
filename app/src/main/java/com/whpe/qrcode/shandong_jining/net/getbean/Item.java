package com.whpe.qrcode.shandong_jining.net.getbean;

public class Item {

    private int resourceId;
    private String name;
    private String desc;

    public Item(int resourceId, String name, String desc) {
        this.resourceId = resourceId;
        this.name = name;
        this.desc = desc;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
