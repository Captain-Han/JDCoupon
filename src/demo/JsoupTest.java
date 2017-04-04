package demo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dao.CouponDao;
import dao.CouponProductDao;
import dao.ProductPriceDao;
import model.Coupon;
import model.CouponProduct;
import model.ProductPrice;
import static unit.DataUnit.*;

public class JsoupTest {

    public static void main(String[] args) {
        try {
            getCoupons();
            getProductPrices();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getCoupons() throws Exception {
        System.out.println("----start : getCoupons --------");
        delAllCoupons();
        boolean stopFlg = false;
        for (int i = 1; !stopFlg; i++) {
            stopFlg = getCoupons(i);
        }
        System.out.println("----end   : getCoupons---------");
    }

    private static void delAllCoupons() throws SQLException {
        System.out.println("----start : delAllCoupons -----");
        CouponDao.delAllCoupons();
        System.out.println("----end   : delAllCoupons -----");
    }

    private static void getProductPrices() throws Exception {
        System.out.println("----start : getProductPrices---");
        List<String> couponL = CouponDao.searchCoupon();
        for (String couponId : couponL) {
            CouponProductDao.delCouponProducts(couponId);
            getProductPrices(couponId);
            CouponDao.updateLinkFlg(couponId);
            ProductPriceDao.updateDelFlg();
        }
        System.out.println("----end   : getProductPrices---");
    }

    private static void getProductPrices(String couponId) throws Exception {
        boolean stopFlg = false;
        for (int pageId = 1; !stopFlg && pageId < 251; pageId++) {
            stopFlg = getProductPrice(couponId, pageId);
        }
    }

    private static boolean getProductPrice(String couponId, int pageId) throws Exception {
        System.out.println("----Coupon Id : " + couponId + "-Page : " + intToStr(pageId, 3) + "--");
        List<ProductPrice> productPriceList = new ArrayList<ProductPrice>();
        ProductPrice productPrice = null;

        List<CouponProduct> couponProductList = new ArrayList<CouponProduct>();
        CouponProduct couponProduct = null;

        Document doc;
        boolean stopFlg = true;
        int pageSize = 30;
        int stSeq = pageSize * pageId - 29;
        String afterUrlStr = "page=" + String.valueOf(pageId) + "&s=" + String.valueOf(stSeq);
        String urlStr = "https://search.jd.com/s_new.php?coupon_batch=" + couponId + "&rt=1&vt=2&scc=1&scrolling=y&"
                + afterUrlStr;
        try {
            doc = Jsoup.connect(urlStr).get();
            Elements ListDiv = doc.getElementsByTag("li");

            for (Element element : ListDiv) {
                String productId = element.attr("data-sku");
                productPrice = new ProductPrice(productId);
                Element priceE = element.getElementsByTag("strong").first();
                String price = priceE.attr("data-price");
                productPrice.setPrice(Double.valueOf(price));
                productPriceList.add(productPrice);
                couponProduct = new CouponProduct(couponId, productId);
                couponProductList.add(couponProduct);
                stopFlg = false;
            }
            ProductPriceDao.insert(productPriceList);
            CouponProductDao.insert(couponProductList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stopFlg;
    }

    private static boolean getCoupons(int pageId) throws Exception {
        System.out.println("----Coupon Page : " + intToStr(pageId, 3) + "----------");
        Document doc;
        boolean stopFlg = true;
        List<Coupon> couponList = new ArrayList<Coupon>();
        try {
            doc = Jsoup.connect("https://a.jd.com/coupons.html?ct=4&page=" + intToStr(pageId)).get();
            Elements ListDiv = doc.getElementsByClass("quan-item");
            for (Element element : ListDiv) {
                stopFlg = false;
                Element offAmt = element.getElementsByClass("num").first();
                Element needAmtElement = element.getElementsByTag("span").first();
                String needAmtStr = needAmtElement.ownText();
                String[] needAmtl = needAmtStr.split("元");
                Element couponE = element.getElementsByAttribute("coupon-time").first();
                String couponId = couponE.attr("coupon-time");

                Coupon coupon = new Coupon(couponId);
                if (needAmtl.length == 1) {
                    coupon.setNeedAmt(0);
                }
                else {
                    coupon.setNeedAmt(Integer.valueOf(needAmtl[0].substring(1)));
                }
                coupon.setOffAmt(Integer.valueOf(offAmt.ownText()));
                coupon.setRmrks(element.text());
                couponList.add(coupon);
            }
            if (!stopFlg) {
                getCouponTimes(couponList);
                CouponDao.insert(couponList);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stopFlg;
    }

    private static void getCouponTimes(List<Coupon> coupons) throws ParseException, java.text.ParseException {

        String beforeUrl = "https://a.jd.com/batchTime.html?";
        String afterUrl = "";
        for (Coupon coupon : coupons) {
            afterUrl = afterUrl + "batchId=" + coupon.getCouponId() + "&";
        }

        String url = beforeUrl + afterUrl;
        try {
            String body = Jsoup.connect(url).ignoreContentType(true).post().body().text();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);
            for (Coupon coupon : coupons) {
                String dateStr = (String) json.get(coupon.getCouponId());
                String[] dtStr = dateStr.split("-");
                String stStr = dtStr[0];
                String endStr = dtStr[1];

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date stDt = sdf.parse(stStr.replace(".", "-"));
                Date endDt = sdf.parse(endStr.replace(".", "-"));
                coupon.setStDt(stDt);
                coupon.setStDtStr(stStr.replace(".", "-"));
                coupon.setEndDtStr(endStr.replace(".", "-"));
                coupon.setEndDt(endDt);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}