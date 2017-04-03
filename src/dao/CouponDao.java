package dao;

import model.Coupon;
import unit.MysqlServer;
import static unit.DataUnit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponDao {

    public static void insert(List<Coupon> items) throws Exception {
        Connection conn = MysqlServer.getConnection();
        String insSql;
        String chkSql;
        String updSql;

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();

            for (Coupon item : items) {
                insSql = "insert into m_coupon(coupon_id, need_amt, off_amt, st_dt, end_dt, rmrks) " + "values('"
                        + item.getCouponId() + "', '" + item.getNeedAmt() + "', '" + item.getOffAmt() + "', '"
                        + item.getStDtStr() + "', '" + item.getEndDtStr() + "', '" + item.getRmrks() + "')";

                chkSql = "select coupon_id from m_coupon where coupon_id ='" + item.getCouponId() + "'";

                updSql = "update m_coupon set del_flg = '0', need_amt = '" + item.getNeedAmt() + "', off_amt = '"
                        + item.getOffAmt() + "', st_dt = '" + item.getStDtStr() + "', end_dt = '" + item.getEndDtStr()
                        + "', rmrks = '" + item.getRmrks() + "' where coupon_id = '" + item.getCouponId() + "'";
                ResultSet chkRslt = stmt.executeQuery(chkSql);
                if (!chkRslt.next()) {
                    stmt.executeUpdate(insSql);
                    System.out.print("INS \t");
                    System.out.print(item.getCouponId() + "\t");
                    System.out.print(item.getNeedAmt() + "\t");
                    System.out.print(item.getOffAmt() + "\t");
                    System.out.print(item.getStDtStr() + "\t");
                    System.out.print(item.getEndDtStr() + "\t");
                    System.out.println(item.getRmrks());
                }
                else {
                    stmt.executeUpdate(updSql);
                    System.out.print("UPD \t");
                    System.out.print(item.getCouponId() + "\t");
                    System.out.print(item.getNeedAmt() + "\t");
                    System.out.print(item.getOffAmt() + "\t");
                    System.out.print(item.getStDtStr() + "\t");
                    System.out.print(item.getEndDtStr() + "\t");
                    System.out.println(item.getRmrks());
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

    public static void insert(Coupon coupon) throws Exception {
        String insSql = "insert into m_coupon(coupon_id, need_amt, off_amt, st_dt, end_dt, rmrks) " + "values('"
                + coupon.getCouponId() + "', '" + coupon.getNeedAmt() + "', '" + coupon.getOffAmt() + "', '"
                + coupon.getStDtStr() + "', '" + coupon.getEndDtStr() + "', '" + coupon.getRmrks() + "')";

        String chkSql = "select coupon_id from m_coupon where coupon_id ='" + coupon.getCouponId() + "'";

        Connection conn = MysqlServer.getConnection();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            ResultSet chkRslt = stmt.executeQuery(chkSql);
            if (!chkRslt.next()) {
                stmt.executeUpdate(insSql);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
    }

    @SuppressWarnings("finally")
    public static List<String> searchCoupon() throws Exception {
        System.out.println("----start : searchCoupon-------");
        String srchSql = "select coupon_id from m_coupon where SYSDATE() between st_dt and end_dt"
                + " and link_flg = '0'";
        srchSql = "select coupon_id from m_coupon where del_flg = '0' and link_flg = '0'";

        Connection conn = MysqlServer.getConnection();
        List<String> couponL = new ArrayList<String>();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            ResultSet chkRslt = stmt.executeQuery(srchSql);
            while (chkRslt.next()) {
                couponL.add(chkRslt.getString("coupon_id"));
            }
            ;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
            System.out.println("----end   : searchCoupon--size :" + intToStr(couponL.size()));
            return couponL;
        }
    }

    public static void delAllCoupons() throws SQLException {
        String updSql = "Update m_coupon set del_flg = '1' where del_flg = '0'";

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

    public static void updateLinkFlg(String couponId) throws SQLException {
        String updSql = "update m_coupon set link_flg = '1' where coupon_id = '" + couponId + "'";

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
}
