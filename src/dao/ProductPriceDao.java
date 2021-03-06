package dao;

import model.ProductPrice;
import unit.MysqlServer;

import static unit.DataUnit.intToStr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceDao {

    public static void insert(List<ProductPrice> items) throws Exception {
        Connection conn = MysqlServer.getConnection();
        String insSql;
        String chkSql;

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            
            for (ProductPrice item : items) {
                insSql = "insert into m_product_price(product_id, price) "
                        + "values('" + item.getProductId() + "', '" + item.getPrice() + "')";

                chkSql = "select product_id from m_product_price where product_id ='" + item.getProductId()
                + "' and price = '" + item.getPrice() + "' and del_flg = '0'";
                
                ResultSet chkRslt = stmt.executeQuery(chkSql);
                if (!chkRslt.next()) {
                    stmt.executeUpdate(insSql);
                    System.out.print("INS \t");
                    System.out.print(item.getProductId() + "\t");
                    System.out.println(item.getPrice());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
    
    public static void insert(ProductPrice productPrice) throws Exception {
        String insSql = "insert into m_product_price(product_id, price) "
                + "values('" + productPrice.getProductId() + "', '" + productPrice.getPrice() + "')";

        String chkSql = "select product_id from m_product_price where product_id ='" + productPrice.getProductId()
                + "' and price = '" + productPrice.getPrice() + "' and del_flg = '0'";

        Connection conn = MysqlServer.getConnection();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            ResultSet chkRslt = stmt.executeQuery(chkSql);
            if (!chkRslt.next()) {
                stmt.executeUpdate(insSql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public static void updateDelFlg() throws SQLException {
        String updSql = "update m_product_price m inner join m_product_price m1 on m.product_id = m1.product_id "
                + "set m.del_flg = '1' where m.del_flg = '0' and m.st_dt < m1.st_dt";
        
        Connection conn = MysqlServer.getConnection();

        try {
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updSql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @SuppressWarnings("finally")
    public static List<String> searchProducts() throws Exception {
        System.out.println("----start : searchProducts-----");

        Connection conn = MysqlServer.getConnection();
        List<String> productL = new ArrayList<String>();
        String srchSql = "select product_id from m_product_price where del_flg = '0'";

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
