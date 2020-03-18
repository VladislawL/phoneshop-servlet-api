package com.es.phoneshop.services.productservice;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.SearchProductResult;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.utils.ProductUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private static DefaultProductService productService;

    private DefaultProductService() {
    }

    public synchronized static DefaultProductService getInstance() {
        if (productService == null) {
            productService = new DefaultProductService();
        }
        return productService;
    }

    @Override
    public List<Product> findProducts(String description, String field, String order) {
        SortField sortField = sortField(field);
        SortOrder sortOrder = sortOrder(order);
        Comparator<Product> comparator = ProductUtils.getComparator(sortField, sortOrder);

        List<SearchProductResult> products = productDao.findProducts(description);

        if (description == null || "".equals(description)) {
            return products.stream()
                    .map(SearchProductResult::getProduct)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } else {
            return products.stream()
                    .sorted(Comparator.comparingInt(SearchProductResult::getCountOfMatches).reversed())
                    .map(SearchProductResult::getProduct)
                    .collect(Collectors.toList());
        }
    }

    private SortField sortField(String field) {
        if (field == null) {
            return SortField.DEFAULT;
        }
        if (field.equals("description")) {
            return SortField.DESCRIPTION;
        } else {
            return SortField.PRICE;
        }
    }

    private SortOrder sortOrder(String order) {
        if (order == null) {
            return SortOrder.DEFAULT;
        }
        if (order.equals("asc")) {
            return SortOrder.ASCENDING;
        } else {
            return SortOrder.DESCENDING;
        }
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productDao.getItem(id);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(id));

        Product copyProduct = copyProductExceptPreviousPrices(product);
        copyProduct.getPreviousPrices().put(new Date(), product.getPrice());

        return copyProduct;
    }

    private Product copyProductExceptPreviousPrices(Product product) {
        return new Product(
                product.getId(),
                product.getCode(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getStock(),
                product.getImageUrl(),
                new HashMap<>(product.getPreviousPrices())
        );
    }

    protected void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
