package com.whpe.qrcode.shandong_jining.toolbean;


public class RealTimeBusBean {

    public RealTimeBusBean(String site, String stationId, boolean havebus) {
        this.site = site;
        this.stationId = stationId;
        this.havebus = havebus;
    }

    private String site;
    private String stationId;
    private boolean havebus;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }



    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean isHavebus() {
        return havebus;
    }

    public void setHavebus(boolean havebus) {
        this.havebus = havebus;
    }
}
