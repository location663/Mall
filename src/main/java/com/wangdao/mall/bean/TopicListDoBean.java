/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:23:05
 **/
package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class TopicListDoBean {
        private int count;
        private int currentPage;
        private List<DataBean> data;
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * userInfo : {"nickName":"dr lan","avatarUrl":""}
             * addTime : 2019-10-07 05:51:28
             * picList : ["http://192.168.2.100:8081/wx/storage/fetch/o05g89l63ywg0dhfqsc6.jpg"]
             * content : 还不错
             */

            private UserInfoBean userInfo;
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
            private Date addTime;
            private String content;
            private List<String> picList;

            public DataBean(UserInfoBean userInfo, Date addTime, String content, List<String> picList) {
                this.userInfo = userInfo;
                this.addTime = addTime;
                this.content = content;
                this.picList = picList;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public Date getAddTime() {
                return addTime;
            }

            public void setAddTime(Date addTime) {
                this.addTime = addTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<String> getPicList() {
                return picList;
            }

            public void setPicList(List<String> picList) {
                this.picList = picList;
            }

            public static class UserInfoBean {
                /**
                 * nickName : dr lan
                 * avatarUrl :
                 */

                private String nickName;
                private String avatarUrl;

                public UserInfoBean(String nickName, String avatarUrl) {
                    this.nickName = nickName;
                    this.avatarUrl = avatarUrl;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }
            }
        }
}

