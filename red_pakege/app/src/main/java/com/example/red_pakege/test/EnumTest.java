package com.example.red_pakege.test;

public class EnumTest {

    public static void main(String[] args){
       /* int type = APPTYPE.click.ordinal();
        System.out.println(type);
        String value = APPTYPE.values()[1].name();
        System.out.println(value);*/

        int type = ModifyType1.AVATAR.getIndex();
        System.out.println(type);
    }

    public enum APPTYPE {
        install,
        download,
        click
    }

    public enum ModifyType1 {

        AVATAR("头像", 1),
        NICKNAME("昵称", 2),
        USERSIGN("个性签名", 3),
        SEX("性别", 4),
        LOGINPWD("登录密码", 5),
        PAYPWD("支付密码", 6),
        BANKCARD("银行卡", 7);

        private String name;
        private int index;

        ModifyType1(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

}
