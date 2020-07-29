package io.nuite.modules.system.entity.online_sale;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * 分销平台权限
 */
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_UserJurisdiction")
public class OnlineUserJurisdictionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId(value = "Seq")
    private Integer seq;

    @TableField(value = "User_Seq")
    private Integer userSeq;

    @TableField(value = "CreateUser_Seq")
    private Integer createUserSeq;

    @TableField(value = "EffectiveDate")
    private Date effectiveDate;

    @TableField(value = "IsDisable")
    private int isDisable;

    @TableField(value = "IsAdministrator")
    private int isAdministrator;

    @TableLogic
    @TableField(value = "Del")
    private int del;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }

    public Integer getCreateUserSeq() {
        return createUserSeq;
    }

    public void setCreateUserSeq(Integer createUserSeq) {
        this.createUserSeq = createUserSeq;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public int getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }

    public int getIsAdministrator() {
        return isAdministrator;
    }

    public void setIsAdministrator(int isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }
}
