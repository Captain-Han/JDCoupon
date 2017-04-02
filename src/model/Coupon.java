package model;

import java.util.Date;

public class Coupon {
    private String couponId;
    private int needAmt;
    private int offAmt;
    private Date stDt;
    private Date endDt;
    private String stDtStr;
    private String endDtStr;
    private String rmrks;

    public Coupon(String couponId) {
        this.couponId = couponId;
    }
    /**
     * @return the couponId
     */
    public String getCouponId() {
        return couponId;
    }
    /**
     * @param couponId the couponId to set
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
    /**
     * @return the needAmt
     */
    public int getNeedAmt() {
        return needAmt;
    }
    /**
     * @param needAmt the needAmt to set
     */
    public void setNeedAmt(int needAmt) {
        this.needAmt = needAmt;
    }
    /**
     * @return the offAmt
     */
    public int getOffAmt() {
        return offAmt;
    }
    /**
     * @param offAmt the offAmt to set
     */
    public void setOffAmt(int offAmt) {
        this.offAmt = offAmt;
    }
    /**
     * @return the stDt
     */
    public Date getStDt() {
        return stDt;
    }
    /**
     * @param stDt the stDt to set
     */
    public void setStDt(Date stDt) {
        this.stDt = stDt;
    }
    /**
     * @return the endDt
     */
    public Date getEndDt() {
        return endDt;
    }
    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }
    /**
     * @return the rmrks
     */
    public String getRmrks() {
        return rmrks;
    }
    /**
     * @param rmrks the rmrks to set
     */
    public void setRmrks(String rmrks) {
        this.rmrks = rmrks;
    }
    /**
     * @return the stDtStr
     */
    public String getStDtStr() {
        return stDtStr;
    }
    /**
     * @param stDtStr the stDtStr to set
     */
    public void setStDtStr(String stDtStr) {
        this.stDtStr = stDtStr;
    }
    /**
     * @return the endDtStr
     */
    public String getEndDtStr() {
        return endDtStr;
    }
    /**
     * @param endDtStr the endDtStr to set
     */
    public void setEndDtStr(String endDtStr) {
        this.endDtStr = endDtStr;
    }
}