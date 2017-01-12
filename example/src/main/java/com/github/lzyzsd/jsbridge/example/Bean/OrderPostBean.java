package com.github.lzyzsd.jsbridge.example.Bean;

/**
 * Created by hjl99 on 2017/1/12.
 */

public class OrderPostBean {
    /**
     * result : 0
     * msg : 余额不足
     * data :
     */

    private String result;
    private String msg;
    private String data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


//
//    /**
//     * result : 1
//     * msg :
//     * data : {"order_sn":"17011246974","order_id":"171"}
//     */
//
//    private String result;
//    private String msg;
//    /**
//     * order_sn : 17011246974
//     * order_id : 171
//     */
//
//    private DataBean data;
//
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        private String order_sn;
//        private String order_id;
//
//        public String getOrder_sn() {
//            return order_sn;
//        }
//
//        public void setOrder_sn(String order_sn) {
//            this.order_sn = order_sn;
//        }
//
//        public String getOrder_id() {
//            return order_id;
//        }
//
//        public void setOrder_id(String order_id) {
//            this.order_id = order_id;
//        }
//    }
}
