package org.quick.library.model;

public class UserInfoModel{

    /**
     * status : 200
     * msg : ok
     * result : {"id":11,"mobile":"","roleid":0,"exp":0,"really":1,"status":1,"last_time":1551594358,"last_ip":"113.251.49.51","login_time":1551594358,"login_ip":"113.251.49.51","source":4,"addtime":1551594355,"top_uid":0,"sign_last_time":"","sign_count_days":0,"distr_id":0,"vip_time":0,"nickname":"猫探11","sex":1,"age":0,"birthday":"","qq":"","email":"","imageId":0,"imageUrl":"","pictureid":"","province":0,"city":0,"area":0,"really_name":"","really_card":"","really_addtime":"","capital":{"id":11,"uid":11,"money":"0.00","coupon":"0.00","integral":"0.00","consumer":"0.00","recharge":"0.00","status":1},"pictureid_img":""}
     */

    private int status;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 11
         * mobile :
         * roleid : 0
         * exp : 0
         * really : 1
         * status : 1
         * last_time : 1551594358
         * last_ip : 113.251.49.51
         * login_time : 1551594358
         * login_ip : 113.251.49.51
         * source : 4
         * addtime : 1551594355
         * top_uid : 0
         * sign_last_time :
         * sign_count_days : 0
         * distr_id : 0
         * vip_time : 0
         * nickname : 猫探11
         * sex : 1
         * age : 0
         * birthday :
         * qq :
         * email :
         * imageId : 0
         * imageUrl :
         * pictureid :
         * province : 0
         * city : 0
         * area : 0
         * really_name :
         * really_card :
         * really_addtime :
         * capital : {"id":11,"uid":11,"money":"0.00","coupon":"0.00","integral":"0.00","consumer":"0.00","recharge":"0.00","status":1}
         * pictureid_img :
         */

        private int id;
        private String mobile;
        private int roleid;
        private int exp;
        private int really;
        private int status;
        private int last_time;
        private String last_ip;
        private int login_time;
        private String login_ip;
        private int source;
        private int addtime;
        private int top_uid;
        private String sign_last_time;
        private int sign_count_days;
        private int distr_id;
        private int vip_time;
        private String nickname;
        private int sex;
        private int age;
        private String birthday;
        private String qq;
        private String email;
        private int imageId;
        private String imageUrl;
        private String pictureid;
        private int province;
        private int city;
        private int area;
        private String really_name;
        private String really_card;
        private String really_addtime;
        private CapitalBean capital;
        private String pictureid_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getRoleid() {
            return roleid;
        }

        public void setRoleid(int roleid) {
            this.roleid = roleid;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getReally() {
            return really;
        }

        public void setReally(int really) {
            this.really = really;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLast_time() {
            return last_time;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public String getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(String last_ip) {
            this.last_ip = last_ip;
        }

        public int getLogin_time() {
            return login_time;
        }

        public void setLogin_time(int login_time) {
            this.login_time = login_time;
        }

        public String getLogin_ip() {
            return login_ip;
        }

        public void setLogin_ip(String login_ip) {
            this.login_ip = login_ip;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getTop_uid() {
            return top_uid;
        }

        public void setTop_uid(int top_uid) {
            this.top_uid = top_uid;
        }

        public String getSign_last_time() {
            return sign_last_time;
        }

        public void setSign_last_time(String sign_last_time) {
            this.sign_last_time = sign_last_time;
        }

        public int getSign_count_days() {
            return sign_count_days;
        }

        public void setSign_count_days(int sign_count_days) {
            this.sign_count_days = sign_count_days;
        }

        public int getDistr_id() {
            return distr_id;
        }

        public void setDistr_id(int distr_id) {
            this.distr_id = distr_id;
        }

        public int getVip_time() {
            return vip_time;
        }

        public void setVip_time(int vip_time) {
            this.vip_time = vip_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getPictureid() {
            return pictureid;
        }

        public void setPictureid(String pictureid) {
            this.pictureid = pictureid;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getReally_name() {
            return really_name;
        }

        public void setReally_name(String really_name) {
            this.really_name = really_name;
        }

        public String getReally_card() {
            return really_card;
        }

        public void setReally_card(String really_card) {
            this.really_card = really_card;
        }

        public String getReally_addtime() {
            return really_addtime;
        }

        public void setReally_addtime(String really_addtime) {
            this.really_addtime = really_addtime;
        }

        public CapitalBean getCapital() {
            return capital;
        }

        public void setCapital(CapitalBean capital) {
            this.capital = capital;
        }

        public String getPictureid_img() {
            return pictureid_img;
        }

        public void setPictureid_img(String pictureid_img) {
            this.pictureid_img = pictureid_img;
        }

        public static class CapitalBean {
            /**
             * id : 11
             * uid : 11
             * money : 0.00
             * coupon : 0.00
             * integral : 0.00
             * consumer : 0.00
             * recharge : 0.00
             * status : 1
             */

            private int id;
            private int uid;
            private String money;
            private String coupon;
            private String integral;
            private String consumer;
            private String recharge;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getConsumer() {
                return consumer;
            }

            public void setConsumer(String consumer) {
                this.consumer = consumer;
            }

            public String getRecharge() {
                return recharge;
            }

            public void setRecharge(String recharge) {
                this.recharge = recharge;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
