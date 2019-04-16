package com.whpe.qrcode.shandong_jining.net.getbean;


import java.util.List;

public class StationInfoList {
    private List<StationInfo> dataList;

    public List<StationInfo> getList() {
        return dataList;
    }

    public void setList(List<StationInfo> list) {
        this.dataList = list;
    }

    public static class StationInfo {

        /**
         * StationMemo :
         * StationName : 置城8号公馆（上行）
         * StationPostion : {"Latitude":35.43673,"Longitude":116.631703}
         * StationID : 55150831193604054079
         * Distance : 473.1
         */

        private String StationMemo;
        private String StationName;
        private StationPostionBean StationPostion;
        private String StationID;
        private double Distance;

        public String getStationMemo() {
            return StationMemo;
        }

        public void setStationMemo(String StationMemo) {
            this.StationMemo = StationMemo;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public StationPostionBean getStationPostion() {
            return StationPostion;
        }

        public void setStationPostion(StationPostionBean StationPostion) {
            this.StationPostion = StationPostion;
        }

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public double getDistance() {
            return Distance;
        }

        public void setDistance(double Distance) {
            this.Distance = Distance;
        }

        public static class StationPostionBean {
            /**
             * Latitude : 35.43673
             * Longitude : 116.631703
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
