package model;


public class CouponProduct {
    private String couponId;
    private String productId;
    private String delFlg;
    
    public CouponProduct(String couponId, String productId) {
        this.couponId = couponId;
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
    
}

