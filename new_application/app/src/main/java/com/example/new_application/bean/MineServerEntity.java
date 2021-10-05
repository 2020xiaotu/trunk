package com.example.new_application.bean;

public class MineServerEntity {

    /**
     * twoLevelClassificationId : 15
     * memberId : 1376427347512148008
     * twoLevelClassificationName : 测试rr
     * oneLevelClassificationId : 4
     */

    private String twoLevelClassificationId;
    private String memberId;
    private String twoLevelClassificationName;
    private String oneLevelClassificationId;
    private String isGuarantee;//是否允许担保 0 否 1是
    boolean isCheck =false;
    int priceType;//价格类型(1：一口价，2：范围价格，3：议价)
    int guaranteeType;//交易模式(0：自行交易,1：担保交易)
    int commissionType;//佣金类型(0买家付,1卖家付,2各付一半)(交易模式为 担保交易 必选)

    public int getCommissionType() {
        return commissionType;
    }

    public String getIsGuarantee() {
        return isGuarantee;
    }

    public void setIsGuarantee(String isGuarantee) {
        this.isGuarantee = isGuarantee;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(int guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTwoLevelClassificationId() {
        return twoLevelClassificationId;
    }

    public void setTwoLevelClassificationId(String twoLevelClassificationId) {
        this.twoLevelClassificationId = twoLevelClassificationId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTwoLevelClassificationName() {
        return twoLevelClassificationName;
    }

    public void setTwoLevelClassificationName(String twoLevelClassificationName) {
        this.twoLevelClassificationName = twoLevelClassificationName;
    }

    public String getOneLevelClassificationId() {
        return oneLevelClassificationId;
    }

    public void setOneLevelClassificationId(String oneLevelClassificationId) {
        this.oneLevelClassificationId = oneLevelClassificationId;
    }
}
