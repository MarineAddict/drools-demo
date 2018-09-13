package com.oyo.droolsdemo.entity.drool;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 数据库的drool
 */
@Data
public class DataBaseDroolDrl implements Serializable {
    private String id;

    private String packageDesc;

    private String importDesc;

    private String rule;

    private String whenExp;

    private String thenExp;

    private String drlRoot;

    private String comments;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public DataBaseDroolDrl(String id, String packageDesc, String importDesc, String rule, String whenExp, String thenExp, String drlRoot, String comments, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.packageDesc = packageDesc;
        this.importDesc = importDesc;
        this.rule = rule;
        this.whenExp = whenExp;
        this.thenExp = thenExp;
        this.drlRoot = drlRoot;
        this.comments = comments;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DataBaseDroolDrl() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc == null ? null : packageDesc.trim();
    }

    public String getImportDesc() {
        return importDesc;
    }

    public void setImportDesc(String importDesc) {
        this.importDesc = importDesc == null ? null : importDesc.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public String getWhenExp() {
        return whenExp;
    }

    public void setWhenExp(String whenExp) {
        this.whenExp = whenExp == null ? null : whenExp.trim();
    }

    public String getThenExp() {
        return thenExp;
    }

    public void setThenExp(String thenExp) {
        this.thenExp = thenExp == null ? null : thenExp.trim();
    }

    public String getDrlRoot() {
        return drlRoot;
    }

    public void setDrlRoot(String drlRoot) {
        this.drlRoot = drlRoot == null ? null : drlRoot.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DataBaseDroolDrl other = (DataBaseDroolDrl) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPackageDesc() == null ? other.getPackageDesc() == null : this.getPackageDesc().equals(other.getPackageDesc()))
                && (this.getImportDesc() == null ? other.getImportDesc() == null : this.getImportDesc().equals(other.getImportDesc()))
                && (this.getRule() == null ? other.getRule() == null : this.getRule().equals(other.getRule()))
                && (this.getWhenExp() == null ? other.getWhenExp() == null : this.getWhenExp().equals(other.getWhenExp()))
                && (this.getThenExp() == null ? other.getThenExp() == null : this.getThenExp().equals(other.getThenExp()))
                && (this.getDrlRoot() == null ? other.getDrlRoot() == null : this.getDrlRoot().equals(other.getDrlRoot()))
                && (this.getComments() == null ? other.getComments() == null : this.getComments().equals(other.getComments()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPackageDesc() == null) ? 0 : getPackageDesc().hashCode());
        result = prime * result + ((getImportDesc() == null) ? 0 : getImportDesc().hashCode());
        result = prime * result + ((getRule() == null) ? 0 : getRule().hashCode());
        result = prime * result + ((getWhenExp() == null) ? 0 : getWhenExp().hashCode());
        result = prime * result + ((getThenExp() == null) ? 0 : getThenExp().hashCode());
        result = prime * result + ((getDrlRoot() == null) ? 0 : getDrlRoot().hashCode());
        result = prime * result + ((getComments() == null) ? 0 : getComments().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }


}