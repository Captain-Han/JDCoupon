package demo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import dao.ProductPriceDao;
import model.ProductPrice;
import static unit.DataUnit.*;

public class UpdatePrice {
    static List<String> productList = new ArrayList<String>();

    public static void main(String[] args) {
        try {
            getProducts();
            updProductPrices();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updProductPrices() throws Exception {
        String mainUrl = "https://p.3.cn/prices/mgets?skuIds=";
        String paramUrl = "";
        int nowNum = 1;
        int stNum = 1;
        String jStr = "J_";

        for (String productId : productList) {
            paramUrl = paramUrl.concat(jStr.concat(productId).concat(","));
            if (nowNum % 100 == 0) {
                String url = mainUrl.concat(paramUrl);
                System.out.println("----url--------------");
                System.out.println(url);
                System.out.println("----" + intToStr(stNum, 6) + "~" + intToStr(nowNum, 6) + "--------------");
                getProductPrices(url);
                paramUrl = "";
                stNum = nowNum + 1;
            }
            nowNum++;
        }

        if (paramUrl.length() != 0) {
            String url = mainUrl.concat(paramUrl);
            System.out.println("----" + intToStr(stNum, 6) + "~" + intToStr(nowNum, 6) + "--------------");
            getProductPrices(url);
        }
    }

    private static void getProductPrices(String url) {
        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();
        try {
            String body = Jsoup.connect(url).ignoreContentType(true).post().body().text();
            String productStr = body.replace('[', ' ').replace(']', ' ').replace("},{", "};{");
            String[] priceList = productStr.trim().split(";");
            for (String price : priceList) {
                productPrices.add(getProductPrice(price));
            }
            ProductPriceDao.insert(productPrices);
            ProductPriceDao.updateDelFlg();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ProductPrice getProductPrice(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) parser.parse(jsonStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        String jProductId = (String) json.get("id");
        String productId = jProductId.substring(2);
        ProductPrice productPrice = new ProductPrice(productId);
        String priceStr = (String)json.get("p");
        double price = Double.parseDouble(priceStr);
        productPrice.setPrice(price);

        return productPrice;
    }

    private static void getProducts() {
        try {
            productList = ProductPriceDao.searchProducts();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
