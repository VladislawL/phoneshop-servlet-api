package com.es.phoneshop.model.utils;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import java.util.List;

public final class ProductUtils {
    public static int countNumberOfDescriptionMatches(Product product, String description) {
        int countOfMatches = 0;
        description = checkDescription(description);
        for (String str : description.toLowerCase().split(" ")) {
            if (product.getDescription().toLowerCase().contains(str)) {
                countOfMatches++;
            }
        }
        return countOfMatches;
    }

    public static Comparator<Product> getComparator(SortField field, SortOrder order) {
        Comparator<Product> comparator = Comparator.comparing(product -> 0);
        if(field != SortField.DEFAULT && order != SortOrder.DEFAULT) {
            if (field == SortField.PRICE) {
                comparator = Comparator.comparing(Product::getPrice).reversed();
            } else {
                comparator = Comparator.comparing(Product::getDescription);
            }
            if (order == SortOrder.DESCENDING) {
                comparator = comparator.reversed();
            }
        }
        return comparator;
    }

    private static String checkDescription(String description) {
        if (description == null) {
            return "";
        } else {
            return description;
        }
    }

    public static boolean containsProduct(List<Product> products, long productId) {
        for (Product product: products) {
            if (product.getId() == productId) {
                return true;
            }
        }
        return false;
    }

    public static Optional<Product> getProductFromList(List<Product> products, long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }
}
