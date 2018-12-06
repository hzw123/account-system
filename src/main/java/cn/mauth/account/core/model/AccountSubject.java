package cn.mauth.account.core.model;

import java.io.Serializable;

/**
 * 账户科目
 */
public class AccountSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 科目编号
     */
    private String subjectNo;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 科目类型 1.资产 2.负债 3.权益 4.损益
     */
    private String subjectType;

    /**
     * 借贷标识位
     */
    private String drCrFlag;

    /**
     * 科目级别
     */
    private String subjectLevel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 获取科目编号
     *
     * @return subject_no - 科目编号
     */
    public String getSubjectNo() {
        return subjectNo;
    }

    /**
     * 设置科目编号
     *
     * @param subjectNo 科目编号
     */
    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    /**
     * 获取科目名称
     *
     * @return subject_name - 科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置科目名称
     *
     * @param subjectName 科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * 获取科目类型 1.资产 2.负债 3.权益 4.损益
     *
     * @return subject_type - 科目类型 1.资产 2.负债 3.权益 4.损益
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 设置科目类型 1.资产 2.负债 3.权益 4.损益
     *
     * @param subjectType 科目类型 1.资产 2.负债 3.权益 4.损益
     */
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 获取借贷标识位
     *
     * @return dr_cr_flag - 借贷标识位
     */
    public String getDrCrFlag() {
        return drCrFlag;
    }

    /**
     * 设置借贷标识位
     *
     * @param drCrFlag 借贷标识位
     */
    public void setDrCrFlag(String drCrFlag) {
        this.drCrFlag = drCrFlag;
    }

    /**
     * 获取科目级别
     *
     * @return subject_level - 科目级别
     */
    public String getSubjectLevel() {
        return subjectLevel;
    }

    /**
     * 设置科目级别
     *
     * @param subjectLevel 科目级别
     */
    public void setSubjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}