package com.example.a3droplets.beijingnewsdemo.bean;

import java.util.List;

/**
 * Created by Mickey_Ma on 2019/1/8.
 * Description:手动写json解析对应的对象
 */
public class HomeCenterPagerBean {
    private List<DetailPagerData> data;
    private List extend;
    private int retcode;

    public List<DetailPagerData> getData() {
        return data;
    }

    public void setData(List<DetailPagerData> data) {
        this.data = data;
    }

    public List getExtend() {
        return extend;
    }

    public void setExtend(List extend) {
        this.extend = extend;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    @Override
    public String toString() {
        return "HomeCenterPagerBean{" +
                "data=" + data +
                ", extend=" + extend +
                ", retcode=" + retcode +
                '}';
    }

    public static class DetailPagerData {
        private int id;
        private String title;
        private int type;
        private String url;
        private String url1;
        private String dayurl;
        private String excurl;
        private String weekurl;
        private List<ChildrenData> childrenData;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getDayurl() {
            return dayurl;
        }

        public void setDayurl(String dayurl) {
            this.dayurl = dayurl;
        }

        public String getExcurl() {
            return excurl;
        }

        public void setExcurl(String excurl) {
            this.excurl = excurl;
        }

        public String getWeekurl() {
            return weekurl;
        }

        public void setWeekurl(String weekurl) {
            this.weekurl = weekurl;
        }

        public List<ChildrenData> getChildrenData() {
            return childrenData;
        }

        public void setChildrenData(List<ChildrenData> childrenData) {
            this.childrenData = childrenData;
        }

        @Override
        public String toString() {
            return "DetailPagerData{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", dayurl='" + dayurl + '\'' +
                    ", excurl='" + excurl + '\'' +
                    ", weekurl='" + weekurl + '\'' +
                    ", childrenData=" + childrenData +
                    '}';
        }

        public static class ChildrenData {
            private int id;
            private String title;
            private int type;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "ChildrenData{"+
                        "id = "+id+
                        ", title='" +title+'\''+
                        ", type="+type+
                        ",url='" +url+'\'' +
                        "}";
            }
        }
    }


}
