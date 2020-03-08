package com.es.phoneshop.demodata;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class DemoDataContextServletListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ProductDao productDao;
    @Spy
    @InjectMocks
    private DemoDataContextServletListener demoDataContextServletListener;

    @Test
    public void shouldConfirmThatProductListIsNotEmpty() {
        when(demoDataContextServletListener.getProductDao()).thenReturn(productDao);
        when(productDao.getProduct(1L)).thenReturn(Optional.of(new Product()));

        demoDataContextServletListener.contextInitialized(servletContextEvent);

        verify(productDao, atLeast(1)).save(Mockito.any());
    }
}
