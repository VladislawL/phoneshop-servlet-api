package com.es.phoneshop.model.utils;

import com.es.phoneshop.model.product.OperationSystem;
import com.es.phoneshop.model.product.PhoneType;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import java.util.function.Predicate;

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

    public static Predicate<Product> getProductPredicate(BigDecimal minPrice, BigDecimal maxPrice, String type, String color, String operationSystem) {
        return (product -> (product.getPrice().compareTo(minPrice) >= 0) &&
                (product.getPrice().compareTo(maxPrice) <= 0) &&
                (product.getColor().toLowerCase().contains(color(color).toLowerCase())) &&
                (product.getType().equals(phoneType(type)) || "none".equals(type)) &&
                (product.getOperationSystem().equals(operationSystem(operationSystem)) || "none".equals(operationSystem))
        );
    }

    private static String color(String color) {
        if (color == null) {
            color = "";
        }
        return color;
    }

    private static PhoneType phoneType(String type) {
        PhoneType result;
        if (type == null || "none".equals(type)) {
            result = PhoneType.DEFAULT;
        } else if ("button".equals(type)) {
            result = PhoneType.BUTTON;
        } else {
            result = PhoneType.SMARTPHONE;
        }
        return result;
    }

    private static OperationSystem operationSystem(String operationSystem) {
        OperationSystem result;
        if (operationSystem == null || "none".equals(operationSystem)) {
            result = OperationSystem.DEFAULT;
        } else if ("ios".equals(operationSystem)) {
            result = OperationSystem.IOS;
        } else {
            result = OperationSystem.ANDROID;
        }
        return result;
    }

    public static Comparator<Product> getComparator(SortField field, SortOrder order) {
        Comparator<Product> comparator = Comparator.comparing(product -> 0);
        if (field != SortField.DEFAULT && order != SortOrder.DEFAULT) {
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

    public static Optional<Product> getProductFromList(List<Product> products, long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }
}
