package org.quick.library.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoreCouponModel implements Serializable {

    /**
     * status : 200
     * msg : ok
     * result : [{"id":12,"gid":50,"name":"滑起来","price":"0.99","min_money":"1.00","fixed_num":100,"use_num":5,"activ_num":0,"activ_receive_num":0,"starttime":1545753600,"endtime":1553529600,"sort":1,"status":1,"linetype":1,"addtime":1546502171,"adminuser":1},{"id":5,"gid":50,"name":"滑石古寨门票","price":"0.99","min_money":"1.00","fixed_num":50,"use_num":13,"activ_num":0,"activ_receive_num":0,"starttime":1543766400,"endtime":1545926400,"sort":1,"status":1,"linetype":1,"addtime":1546502182,"adminuser":1}]
     */

    private int status;
    private String msg;
    private List<ResultBean> result = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * id : 12
         * gid : 50
         * name : 滑起来
         * price : 0.99
         * min_money : 1.00
         * fixed_num : 100
         * use_num : 5
         * activ_num : 0
         * activ_receive_num : 0
         * starttime : 1545753600
         * endtime : 1553529600
         * sort : 1
         * status : 1
         * linetype : 1
         * addtime : 1546502171
         * adminuser : 1
         */

        private int id;
        private int gid;
        private String name;
        private String price;
        private String min_money;
        private int fixed_num;
        private int use_num;
        private int activ_num;
        private int activ_receive_num;
        private long starttime;
        private long endtime;
        private int sort;
        private int status;
        private int linetype;
        private long addtime;
        private long adminuser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMin_money() {
            return min_money;
        }

        public void setMin_money(String min_money) {
            this.min_money = min_money;
        }

        public int getFixed_num() {
            return fixed_num;
        }

        public void setFixed_num(int fixed_num) {
            this.fixed_num = fixed_num;
        }

        public int getUse_num() {
            return use_num;
        }

        public void setUse_num(int use_num) {
            this.use_num = use_num;
        }

        public int getActiv_num() {
            return activ_num;
        }

        public void setActiv_num(int activ_num) {
            this.activ_num = activ_num;
        }

        public int getActiv_receive_num() {
            return activ_receive_num;
        }

        public void setActiv_receive_num(int activ_receive_num) {
            this.activ_receive_num = activ_receive_num;
        }

        public long getStarttime() {
            return starttime;
        }

        public void setStarttime(long starttime) {
            this.starttime = starttime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLinetype() {
            return linetype;
        }

        public void setLinetype(int linetype) {
            this.linetype = linetype;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public long getAdminuser() {
            return adminuser;
        }

        public void setAdminuser(long adminuser) {
            this.adminuser = adminuser;
        }
    }
}
