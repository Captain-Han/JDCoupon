package dao;

import model.CouponProduct;
import unit.MysqlServer;

import static unit.DataUnit.intToStr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponProductDao {

    public static void insert(List<CouponProduct> items) throws Exception {
        Connection conn = MysqlServer.getConnection();
        String insSql;
        String chkSql;
        String updSql;

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();

            for (CouponProduct item : items) {
                insSql = "insert into t_coupon_product(coupon_id, product_id, del_flg) " + "values('"
                        + item.getCouponId() + "', '" + item.getProductId() + "', '" + item.getDelFlg() + "')";

                chkSql = "select coupon_id from t_coupon_product where product_id ='" + item.getProductId()
                        + "' and coupon_id = '" + item.getCouponId() + "'";

                updSql = "update t_coupon_product set del_flg = '0' where product_id ='" + item.getProductId()
                        + "' and coupon_id = '" + item.getCouponId() + "'";

                ResultSet chkRslt = stmt.executeQuery(chkSql);
                if (!chkRslt.next()) {
                    stmt.executeUpdate(insSql);
                    System.out.print("INS \t");
                    System.out.print(item.getCouponId() + "\t");
                    System.out.println(item.getProductId());
                }
                else {
                    stmt.executeUpdate(updSql);
                    System.out.print("UPD \t");
                    System.out.print(item.getCouponId() + "\t");
                    System.out.println(item.getProductId());
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
    }

    public static void insert(CouponProduct item) throws Exception {
        String insSql = "insert into t_coupon_product(coupon_id, product_id, del_flg) " + "values('"
                + item.getCouponId() + "', '" + item.getProductId() + "', '" + item.getDelFlg() + "')";

        String chkSql = "select coupon_id from t_coupon_product where product_id ='" + item.getProductId()
                + "' and coupon_id = '" + item.getCouponId() + "'";

        String updSql = "update t_coupon_product set del_flg = '0' where product_id ='" + item.getProductId()
                + "' and coupon_id = '" + item.getCouponId() + "'";

        Connection conn = MysqlServer.getConnection();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            ResultSet chkRslt = stmt.executeQuery(chkSql);
            if (!chkRslt.next()) {
                stmt.executeUpdate(insSql);
                System.out.print("INS \t");
                System.out.print(item.getCouponId() + "\t");
                System.out.println(item.getProductId());
            }
            else {
                stmt.executeUpdate(updSql);
                System.out.print("UPD \t");
                System.out.print(item.getCouponId() + "\t");
                System.out.println(item.getProductId());
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
    }

    public static void delCouponProducts(String couponId) throws SQLException {
        String updSql = "Update t_coupon_product set del_flg = '1' where coupon_id = '" + couponId
                + "' and del_flg = '0'";

        Connection conn = MysqlServer.getConnection();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updSql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
    }

    @SuppressWarnings("finally")
    public static List<String> searchProducts(String couponId) throws Exception {
        System.out.println("----start : searchProducts-----");

        Connection conn = MysqlServer.getConnection();
        List<String> productL = new ArrayList<String>();
        String srchSql = "select poroduct_id from t_coupon_product where coupon_id = '" + couponId + "'";

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            ResultSet chkRslt = stmt.executeQuery(srchSql);
            while (chkRslt.next()) {
                productL.add(chkRslt.getString("product_id"));
            };

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            conn.close();
            System.out.println("----end   : searchCoupon--size :" + intToStr(productL.size()));
            return productL;
        }
    }
}
