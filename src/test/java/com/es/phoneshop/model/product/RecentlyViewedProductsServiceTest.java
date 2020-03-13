package com.es.phoneshop.model.product;

import com.es.phoneshop.model.recentlyviewedproducts.ViewedProducts;
import com.es.phoneshop.services.RecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedProductsServiceTest {

    @Mock
    private RecentlyViewedProductsService viewedProductsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private List<Product> productList;
    @Mock
    private Product product;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    @Captor
    private ArgumentCaptor<ViewedProducts> attributeArgumentCaptor;

    private String recentlyViewedAttribute = RecentlyViewedProductsService.RECENTLY_VIEWED_ATTRIBUTE;

    @Before
    public void setup() {
        viewedProductsService = RecentlyViewedProductsService.getInstance();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldGetProductFromSession() {
        Product testProduct = new Product();
        ViewedProducts viewedProducts = new ViewedProducts(new LinkedList<>(Arrays.asList(testProduct)));

        when(session.getAttribute(Mockito.same(recentlyViewedAttribute))).thenReturn(viewedProducts);

        List<Product> testProductList = viewedProductsService.getProducts(request);

        assertEquals(viewedProducts.getProductList(), testProductList);
    }

    @Test
    public void shouldAddProductToRecentlyViewedListOfProducts() {
        ViewedProducts viewedProducts = new ViewedProducts(productList);

        when(session.getAttribute(Mockito.same(recentlyViewedAttribute))).thenReturn(viewedProducts);
        when(product.getId()).thenReturn(0L);

        viewedProductsService.addProduct(request, product);

        verify(productList).add(Mockito.same(0), productArgumentCaptor.capture());
        verify(session).setAttribute(Mockito.same(recentlyViewedAttribute), attributeArgumentCaptor.capture());
        assertEquals(product, productArgumentCaptor.getValue());
        assertEquals(viewedProducts.getProductList(), attributeArgumentCaptor.getValue().getProductList());
    }

    @Test
    public void shouldReturnNViewedProducts() {
        Currency usd = Currency.getInstance("USD");
        List<Product> expectedProducts = Arrays.asList(new Product(1L, "", "", new BigDecimal(1), usd, 1, ""));
        ViewedProducts viewedProducts = new ViewedProducts(expectedProducts);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Mockito.eq(RecentlyViewedProductsService.RECENTLY_VIEWED_ATTRIBUTE))).thenReturn(viewedProducts);

        List<Product> products = viewedProductsService.getNViewedProducts(request, 3);

        assertTrue(isEqualsProductLists(expectedProducts, products));
    }

    private boolean isEqualsProductLists(List<Product> a, List<Product> b) {
        if(a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < Math.max(a.size(), b.size()); i++) {
            if (!a.get(i).getId().equals(b.get(i).getId())) {
                return false;
            }
        }

        return true;
    }
}
