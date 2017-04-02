package model;

import java.util.Date;

public class ProductPrice {
    private String productId;
    private double price;
    private Date stDt;
    private String stDtStr;
    private String delFlg;
    
    public ProductPrice(String productId) {
        this.productId = productId;
        this.delFlg = "0";
    }
    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }
    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
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
     * @return the delFlg
     */
    public String getDelFlg() {
        return delFlg;
    }
    /**
     * @param delFlg the delFlg to set
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    
}

