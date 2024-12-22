package org.example.db_work_back.vo;

import io.swagger.annotations.ApiOperation;

@ApiOperation("用户登录返回值")
public class CustomerLoginVO {

    private Integer customerID;
    private String customername;

    // 默认构造方法
    public CustomerLoginVO() {}

    // Getter 和 Setter 方法
    public Integer getId() {
        return customerID;
    }

    public void setId(Integer id) {
        this.customerID = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    // toString 方法
    @Override
    public String toString() {
        return "CustomerLoginVO{" +
                "customerID=" + customerID +
                ", customername='" + customername + '\'' +
                '}';
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerLoginVO that = (CustomerLoginVO) o;
        return customerID != null && customerID.equals(that.customerID) &&
                customername != null && customername.equals(that.customername);
    }

    @Override
    public int hashCode() {
        return 31 * (customerID != null ? customerID.hashCode() : 0) +
                (customername != null ? customername.hashCode() : 0);
    }
}
