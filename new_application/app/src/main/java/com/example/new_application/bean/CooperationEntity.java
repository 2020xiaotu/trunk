package com.example.new_application.bean;

public class CooperationEntity {

    /**
     * id : 2
     * title : 专业打手
     * image : upload/images/20210902/1630558990606.png
     * customerServiceId : 3
     * customerServiceName : 客服壹号
     * contactInformation : 788888888
     * remark : 打手
     */

    private String id;
    private String title;
    private String image;
    private String customerServiceId;
    private String customerServiceName;
    private String contactInformation;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    public String getCustomerServiceName() {
        return customerServiceName;
    }

    public void setCustomerServiceName(String customerServiceName) {
        this.customerServiceName = customerServiceName;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
