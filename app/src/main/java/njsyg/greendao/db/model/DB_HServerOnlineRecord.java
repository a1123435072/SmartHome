package njsyg.greendao.db.model;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "DB__HSERVER_ONLINE_RECORD".
 */
@Entity
public class DB_HServerOnlineRecord {

    @Id
    private Long id;
    private String hserver_sn;
    private Integer hserver_onlineState;
    private java.util.Date updatetime;
    private String remark;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public DB_HServerOnlineRecord() {
    }

    public DB_HServerOnlineRecord(Long id) {
        this.id = id;
    }

    @Generated
    public DB_HServerOnlineRecord(Long id, String hserver_sn, Integer hserver_onlineState, java.util.Date updatetime, String remark) {
        this.id = id;
        this.hserver_sn = hserver_sn;
        this.hserver_onlineState = hserver_onlineState;
        this.updatetime = updatetime;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHserver_sn() {
        return hserver_sn;
    }

    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }

    public Integer getHserver_onlineState() {
        return hserver_onlineState;
    }

    public void setHserver_onlineState(Integer hserver_onlineState) {
        this.hserver_onlineState = hserver_onlineState;
    }

    public java.util.Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
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