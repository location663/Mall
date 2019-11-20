/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/20
 * Time:14:08
 **/
package com.wangdao.mall.bean;

import java.util.List;
public  class CheckoutDataBean {
        /**
         * grouponPrice : 0
         * grouponRulesId : 0
         * checkedAddress : {"id":66,"name":"123456","userId":1,"provinceId":1,"cityId":32,"areaId":377,"address":"123456","mobile":"17790018794","isDefault":false,"addTime":"2019-10-05 10:30:39","updateTime":"2019-11-19 10:58:17","deleted":true}
         * actualPrice : 1772.7
         * orderTotalPrice : 1772.7
         * couponPrice : 333.0
         * availableCouponLength : 2
         * couponId : 105
         * freightPrice : 0
         * checkedGoodsList : [{"id":1069,"userId":1,"goodsId":1152009,"goodsSn":"1152009","goodsName":"魔兽世界 联盟 护腕 一只","productId":272,"price":29,"number":3,"specifications":["标准"],"checked":true,"picUrl":"http://yanxuan.nosdn.127.net/ae6d41117717387b82dcaf1dfce0cd97.png","addTime":"2019-11-19 22:18:34","updateTime":"2019-11-19 22:44:53","deleted":false},{"id":1074,"userId":1,"goodsId":1181174,"goodsSn":"124354303","goodsName":"男西装裤","productId":756,"price":1999,"number":1,"specifications":["31"],"checked":true,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/9glgghlrfaolsz58jg9l.jpg","addTime":"2019-11-19 22:27:38","updateTime":"2019-11-19 22:44:53","deleted":false},{"id":1076,"userId":1,"goodsId":1181185,"goodsSn":"110","goodsName":"110","productId":767,"price":9.85,"number":2,"specifications":["标准"],"checked":true,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/hat74hv7jercsa6e8xcu.jpg","addTime":"2019-11-19 22:36:08","updateTime":"2019-11-19 22:44:53","deleted":false}]
         * goodsTotalPrice : 2105.7
         * addressId : 66
         */

        private int grouponPrice;
        private int grouponRulesId;
        private AddressDO checkedAddress;
        private double actualPrice;
        private double orderTotalPrice;
        private double couponPrice;
        private int availableCouponLength;
        private int couponId;
        private int freightPrice;
        private double goodsTotalPrice;
        private int addressId;
        private List<CartDO> checkedGoodsList;

        public AddressDO getCheckedAddress() {
            return checkedAddress;
         }

        public void setCheckedAddress(AddressDO checkedAddress) {
        this.checkedAddress = checkedAddress;
        }

    public int getGrouponPrice() {
            return grouponPrice;
        }

        public void setGrouponPrice(int grouponPrice) {
            this.grouponPrice = grouponPrice;
        }

        public int getGrouponRulesId() {
            return grouponRulesId;
        }

        public void setGrouponRulesId(int grouponRulesId) {
            this.grouponRulesId = grouponRulesId;
        }


        public double getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(double actualPrice) {
            this.actualPrice = actualPrice;
        }

        public double getOrderTotalPrice() {
            return orderTotalPrice;
        }

        public void setOrderTotalPrice(double orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
        }

        public double getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(double couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getAvailableCouponLength() {
            return availableCouponLength;
        }

        public void setAvailableCouponLength(int availableCouponLength) {
            this.availableCouponLength = availableCouponLength;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public int getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(int freightPrice) {
            this.freightPrice = freightPrice;
        }

        public double getGoodsTotalPrice() {
            return goodsTotalPrice;
        }

        public void setGoodsTotalPrice(double goodsTotalPrice) {
            this.goodsTotalPrice = goodsTotalPrice;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public List<CartDO> getCheckedGoodsList() {
            return checkedGoodsList;
        }

        public void setCheckedGoodsList(List<CartDO> checkedGoodsList) {
            this.checkedGoodsList = checkedGoodsList;
        }

}

