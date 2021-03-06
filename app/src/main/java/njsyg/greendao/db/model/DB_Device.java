package njsyg.greendao.db.model;

import org.greenrobot.greendao.annotation.*;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "DB__DEVICE".
 */
@Entity
public class DB_Device implements Serializable{

    @Id
    private Long device_id;
    private String device_sn;
    private String hserver_sn;
    private String device_ip;
    private Integer device_type;
    private String device_installPlace;
    private String device_nickName;
    private String remark;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public DB_Device() {
    }

    public DB_Device(Long device_id) {
        this.device_id = device_id;
    }

    @Generated
    public DB_Device(Long device_id, String device_sn, String hserver_sn, String device_ip, Integer device_type, String device_installPlace, String device_nickName, String remark) {
        this.device_id = device_id;
        this.device_sn = device_sn;
        this.hserver_sn = hserver_sn;
        this.device_ip = device_ip;
        this.device_type = device_type;
        this.device_installPlace = device_installPlace;
        this.device_nickName = device_nickName;
        this.remark = remark;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getHserver_sn() {
        return hserver_sn;
    }

    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public String getDevice_installPlace() {
        return device_installPlace;
    }

    public void setDevice_installPlace(String device_installPlace) {
        this.device_installPlace = device_installPlace;
    }

    public String getDevice_nickName() {
        return device_nickName;
    }

    public void setDevice_nickName(String device_nickName) {
        this.device_nickName = device_nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
