package org.example.db_work_back.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * C端用户登录
 */
@ApiModel(description = "客户登录时传递的数据模型")
public class CustomerLoginDTO implements Serializable {

    @ApiModelProperty("用户ID")
    private Integer customerID;

    @ApiModelProperty("密码")
    private String password;

    // 默认构造方法
    public CustomerLoginDTO() {}

    // Getter 和 Setter 方法
    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString 方法
    @Override
    public String toString() {
        return "CustomerLoginDTO{" +
                "customerID=" + customerID +
                ", password='" + password + '\'' +
                '}';
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerLoginDTO that = (CustomerLoginDTO) o;
        return customerID != null && customerID.equals(that.customerID) &&
                password != null && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return 31 * (customerID != null ? customerID.hashCode() : 0) +
                (password != null ? password.hashCode() : 0);
    }
}
