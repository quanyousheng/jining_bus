package com.whpe.qrcode.shandong_jining.net.getbean;

import java.util.List;

public class StationRealTimeInfoList {
    private List<StationRealTimeInfo> dataList;

    public List<StationRealTimeInfo> getList() {
        return dataList;
    }

    public void setList(List<StationRealTimeInfo> list) {
        this.dataList = list;
    }

    public static class StationRealTimeInfo {

        /**
         * StationName : 远东工业园(下行)
         * FirstTime : 0001-01-01 00:00:00
         * RoutePrice :
         * FirtLastShiftInfo : 首末班：00:00--00:00
         * RouteName : 26路
         * RealtimeInfoList : [{"ArriveTime":"2019-04-11 17:13:59","BusID":"55120401111723179003","SpaceNum":5,"RunTime":10,"ISLast":0,"Productid":0,"BusPostion":{"Latitude":35.436005,"Longitude":116.608008},"StationID":"091122181146230015","BusName":"120028","ForeCastInfo2":"还有 5 站","ForeCastInfo1":"32 秒之前离开","LeaveOrStop":0,"ArriveStaName":"金宇装饰城(下行)","SubRouteID":0},{"ArriveTime":"2019-04-11 17:13:53","BusID":"55120428102808336002","SpaceNum":9,"RunTime":10,"ISLast":0,"Productid":0,"BusPostion":{"Latitude":35.420114,"Longitude":116.602488},"StationID":"091202462123330051","BusName":"120084","ForeCastInfo2":"还有 9 站","ForeCastInfo1":"38 秒之前离开","LeaveOrStop":0,"ArriveStaName":"青少年宫(下行)","SubRouteID":0}]
         * EndStaInfo : 豪德商贸城(下行)-->济宁科技馆（下行）
         * RouteID : 26
         * StationID : 091202462148280077
         * LastTime : 0001-01-01 00:00:00
         */

        private String StationName;
        private String FirstTime;
        private String RoutePrice;
        private String FirtLastShiftInfo;
        private String RouteName;
        private String EndStaInfo;
        private int RouteID;
        private String StationID;
        private String LastTime;
        private List<RealtimeInfoListBean> RealtimeInfoList;

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getFirstTime() {
            return FirstTime;
        }

        public void setFirstTime(String FirstTime) {
            this.FirstTime = FirstTime;
        }

        public String getRoutePrice() {
            return RoutePrice;
        }

        public void setRoutePrice(String RoutePrice) {
            this.RoutePrice = RoutePrice;
        }

        public String getFirtLastShiftInfo() {
            return FirtLastShiftInfo;
        }

        public void setFirtLastShiftInfo(String FirtLastShiftInfo) {
            this.FirtLastShiftInfo = FirtLastShiftInfo;
        }

        public String getRouteName() {
            return RouteName;
        }

        public void setRouteName(String RouteName) {
            this.RouteName = RouteName;
        }

        public String getEndStaInfo() {
            return EndStaInfo;
        }

        public void setEndStaInfo(String EndStaInfo) {
            this.EndStaInfo = EndStaInfo;
        }

        public int getRouteID() {
            return RouteID;
        }

        public void setRouteID(int RouteID) {
            this.RouteID = RouteID;
        }

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getLastTime() {
            return LastTime;
        }

        public void setLastTime(String LastTime) {
            this.LastTime = LastTime;
        }

        public List<RealtimeInfoListBean> getRealtimeInfoList() {
            return RealtimeInfoList;
        }

        public void setRealtimeInfoList(List<RealtimeInfoListBean> RealtimeInfoList) {
            this.RealtimeInfoList = RealtimeInfoList;
        }

        public static class RealtimeInfoListBean {
            /**
             * ArriveTime : 2019-04-11 17:13:59
             * BusID : 55120401111723179003
             * SpaceNum : 5
             * RunTime : 10
             * ISLast : 0
             * Productid : 0
             * BusPostion : {"Latitude":35.436005,"Longitude":116.608008}
             * StationID : 091122181146230015
             * BusName : 120028
             * ForeCastInfo2 : 还有 5 站
             * ForeCastInfo1 : 32 秒之前离开
             * LeaveOrStop : 0
             * ArriveStaName : 金宇装饰城(下行)
             * SubRouteID : 0
             */

            private String ArriveTime;
            private String BusID;
            private int SpaceNum;
            private int RunTime;
            private int ISLast;
            private int Productid;
            private BusPostionBean BusPostion;
            private String StationID;
            private String BusName;
            private String ForeCastInfo2;
            private String ForeCastInfo1;
            private int LeaveOrStop;
            private String ArriveStaName;
            private int SubRouteID;

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

            public int getSpaceNum() {
                return SpaceNum;
            }

            public void setSpaceNum(int SpaceNum) {
                this.SpaceNum = SpaceNum;
            }

            public int getRunTime() {
                return RunTime;
            }

            public void setRunTime(int RunTime) {
                this.RunTime = RunTime;
            }

            public int getISLast() {
                return ISLast;
            }

            public void setISLast(int ISLast) {
                this.ISLast = ISLast;
            }

            public int getProductid() {
                return Productid;
            }

            public void setProductid(int Productid) {
                this.Productid = Productid;
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

            public String getBusName() {
                return BusName;
            }

            public void setBusName(String BusName) {
                this.BusName = BusName;
            }

            public String getForeCastInfo2() {
                return ForeCastInfo2;
            }

            public void setForeCastInfo2(String ForeCastInfo2) {
                this.ForeCastInfo2 = ForeCastInfo2;
            }

            public String getForeCastInfo1() {
                return ForeCastInfo1;
            }

            public void setForeCastInfo1(String ForeCastInfo1) {
                this.ForeCastInfo1 = ForeCastInfo1;
            }

            public int getLeaveOrStop() {
                return LeaveOrStop;
            }

            public void setLeaveOrStop(int LeaveOrStop) {
                this.LeaveOrStop = LeaveOrStop;
            }

            public String getArriveStaName() {
                return ArriveStaName;
            }

            public void setArriveStaName(String ArriveStaName) {
                this.ArriveStaName = ArriveStaName;
            }

            public int getSubRouteID() {
                return SubRouteID;
            }

            public void setSubRouteID(int SubRouteID) {
                this.SubRouteID = SubRouteID;
            }

            public static class BusPostionBean {
                /**
                 * Latitude : 35.436005
                 * Longitude : 116.608008
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
}

