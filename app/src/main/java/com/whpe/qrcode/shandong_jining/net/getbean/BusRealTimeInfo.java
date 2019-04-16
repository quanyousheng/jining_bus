package com.whpe.qrcode.shandong_jining.net.getbean;

import java.util.List;

public class BusRealTimeInfo {

    /**
     * BusPosList : [{"BusName":"120152","ArriveTime":"2019-04-13 11:55:27","BusID":"55130109103225112004","Productid":0,"BusStaStype":0,"BusPostion":{"Latitude":35.406903,"Longitude":116.608887},"StationID":"091120093321240117","ArriveStaInfo":"第二人民医院(上行)","SubRouteID":0,"NextStaInfo":"开往科苑南路(上行)"},{"BusName":"120135","ArriveTime":"2019-04-13 11:55:02","BusID":"55130109104149065009","Productid":0,"BusStaStype":0,"BusPostion":{"Latitude":35.443243,"Longitude":116.638355},"StationID":"55170602201330247081","ArriveStaInfo":"长虹路机电路口（上行）","SubRouteID":0,"NextStaInfo":"开往薛口停车场（上行）"}]
     * StalineCon : ["110105382514450172,3","55110105164040126000,3","110105382525770166,3","091030410905710100,3","55110105163954406000,3","55110105163930609000,3","55110413162602417247,3","55120625145515766032,3","55120330153855455000,3","55120330152602529000,3","55120330152414526000,3","091201141955040103,3","091201141989750106,3","55160523145804330000,3","55170928085936406000,3","091124550690600193,3","55170602201421591081,3","55170602200546355033,3","091124550616490199,3","55171110113916496472,3","091225043861550287,3","110105382571360124,3","091201050905650155,3","091201050924070153,3","55120104151356304120,3","091120093321240117,3","55120104151506132120,3","091120093335020125,3","091120093308000127,3","091122265795530192,3","091122265757920194,3","091125232111680105,3","091125232155330103,3","091201385602030146,3","55170602201330247081,3"]
     * SegmentID : 526502
     * RouteID : 5
     */

    private int SegmentID;
    private int RouteID;
    private List<BusPosListBean> BusPosList;
    private List<String> StalineCon;

    public int getSegmentID() {
        return SegmentID;
    }

    public void setSegmentID(int SegmentID) {
        this.SegmentID = SegmentID;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int RouteID) {
        this.RouteID = RouteID;
    }

    public List<BusPosListBean> getBusPosList() {
        return BusPosList;
    }

    public void setBusPosList(List<BusPosListBean> BusPosList) {
        this.BusPosList = BusPosList;
    }

    public List<String> getStalineCon() {
        return StalineCon;
    }

    public void setStalineCon(List<String> StalineCon) {
        this.StalineCon = StalineCon;
    }

    public static class BusPosListBean {
        /**
         * BusName : 120152
         * ArriveTime : 2019-04-13 11:55:27
         * BusID : 55130109103225112004
         * Productid : 0
         * BusStaStype : 0
         * BusPostion : {"Latitude":35.406903,"Longitude":116.608887}
         * StationID : 091120093321240117
         * ArriveStaInfo : 第二人民医院(上行)
         * SubRouteID : 0
         * NextStaInfo : 开往科苑南路(上行)
         */

        private String BusName;
        private String ArriveTime;
        private String BusID;
        private int Productid;
        private int BusStaStype;
        private BusPostionBean BusPostion;
        private String StationID;
        private String ArriveStaInfo;
        private int SubRouteID;
        private String NextStaInfo;

        public String getBusName() {
            return BusName;
        }

        public void setBusName(String BusName) {
            this.BusName = BusName;
        }

        public String getArriveTime() {
            return ArriveTime;
        }

        public void setArriveTime(String ArriveTime) {
            this.ArriveTime = ArriveTime;
        }

        public String getBusID() {
            return BusID;
        }

        public void setBusID(String BusID) {
            this.BusID = BusID;
        }

        public int getProductid() {
            return Productid;
        }

        public void setProductid(int Productid) {
            this.Productid = Productid;
        }

        public int getBusStaStype() {
            return BusStaStype;
        }

        public void setBusStaStype(int BusStaStype) {
            this.BusStaStype = BusStaStype;
        }

        public BusPostionBean getBusPostion() {
            return BusPostion;
        }

        public void setBusPostion(BusPostionBean BusPostion) {
            this.BusPostion = BusPostion;
        }

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getArriveStaInfo() {
            return ArriveStaInfo;
        }

        public void setArriveStaInfo(String ArriveStaInfo) {
            this.ArriveStaInfo = ArriveStaInfo;
        }

        public int getSubRouteID() {
            return SubRouteID;
        }

        public void setSubRouteID(int SubRouteID) {
            this.SubRouteID = SubRouteID;
        }

        public String getNextStaInfo() {
            return NextStaInfo;
        }

        public void setNextStaInfo(String NextStaInfo) {
            this.NextStaInfo = NextStaInfo;
        }

        public static class BusPostionBean {
            /**
             * Latitude : 35.406903
             * Longitude : 116.608887
             */

            private double Latitude;
            private double Longitude;

            public double getLatitude() {
                return Latitude;
            }

            public void setLatitude(double Latitude) {
                this.Latitude = Latitude;
            }

            public double getLongitude() {
                return Longitude;
            }

            public void setLongitude(double Longitude) {
                this.Longitude = Longitude;
            }
        }
    }
}

