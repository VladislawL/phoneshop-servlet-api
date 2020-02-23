package com.es.phoneshop.demodata;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;

public class DemoDataContextServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDao = ArrayListProductDao.getInstance();

        generateSampleProductsList(productDao);

        Product product = productDao.getProduct(1L).get();
        product.setPreviousPrices(generateSamplePriceHistory());
    }

    private void generateSampleProductsList(ProductDao productDao) {
        Currency usd = Currency.getInstance("USD");

        productDao.save(new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product(null, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product(null, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product(null, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product(null, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product(null, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product(null, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product(null, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product(null, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product(null, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product(null, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product(null, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product(null, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

    }

    private Map<Date, BigDecimal> generateSamplePriceHistory() {
        Map<Date, BigDecimal> previousPrices = new HashMap<>();
        Calendar datePrice1 = new GregorianCalendar(2019, 5, 23);
        Calendar datePrice2 = new GregorianCalendar(2016, 9, 7);
        previousPrices.put(datePrice1.getTime(), new BigDecimal(150));
        previousPrices.put(datePrice2.getTime(), new BigDecimal(200));
        return previousPrices;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
