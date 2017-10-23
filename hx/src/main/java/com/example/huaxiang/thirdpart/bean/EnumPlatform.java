package com.example.huaxiang.thirdpart.bean;

public enum EnumPlatform {
    QQ("QQ"), WX("weixin"), ALI("支付宝"), WB("微博");
    String value;

    EnumPlatform(String value) {
    }

    public String getValue() {
        return value;
    }

    public enum Method {
        SHARE_QQ_FRIEND("QQ朋友圈分享") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.QQ;
            }
        },
        SHARE_QQ_ZONE("QQ空间分享") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.QQ;
            }
        },
        SHARE_WX_FRIEND("微信好友分享") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WX;
            }
        },
        SHARE_WX_TIMELINE("微信朋友圈分享") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WX;
            }
        },
        SHARE_WX_FAVORITE("微信收藏") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WX;
            }
        }, SHARE_WB("微博分享") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WB;
            }
        },
        LOGIN_WX("微信登录") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WX;
            }
        },
        LOGIN_QQ("QQ登录") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.QQ;
            }
        },
        PAY_WX("微信支付") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.WX;
            }
        },
        PAY_ALI("支付宝支付") {
            @Override
            public EnumPlatform belong() {
                return EnumPlatform.ALI;
            }
        };
        private String value;

        Method(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public EnumPlatform belong() {
            return null;
        }
    }
}
