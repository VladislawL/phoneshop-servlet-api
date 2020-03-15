package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.utils.PriceFormatUtils;
import com.es.phoneshop.services.cartsevice.CartService;
import com.es.phoneshop.services.orderservice.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    @Mock
    private OrderService orderService;
    @Mock
    private ServletContext servletContext;
    @Mock
    private PrintWriter writer;
    @InjectMocks
    private CheckoutPageServlet servlet = new CheckoutPageServlet();
    @Captor
    private ArgumentCaptor<BigDecimal> totalPriceArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> errorMessageArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> urlArgumentCaptor;
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Before
    public void setup() throws IOException {
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void shouldForwardToCheckoutPageWithoutErrorInDoGet() throws ServletException, IOException {
        BigDecimal subtotalPrice = new BigDecimal(1);
        String deliveryCostString = "0";
        BigDecimal deliveryCost = new BigDecimal(Integer.parseInt(deliveryCostString));
        BigDecimal totalPrice = subtotalPrice.add(deliveryCost);
        String formattedTotalPrice = PriceFormatUtils.format(totalPrice, Locale.getDefault());

        Cart cart = new Cart();
        List<CartItem> cartItems = Arrays.asList(new CartItem());
        cart.setSubtotalPrice(subtotalPrice);
        cart.setCartItems(cartItems);

        when(cartService.getCart(request)).thenReturn(cart);
        when(servletContext.getInitParameter("deliveryCost")).thenReturn(deliveryCostString);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("totalPrice"), totalPriceArgumentCaptor.capture());
        assertEquals(formattedTotalPrice, totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldSetErrorMessageAndForwardToCheckoutPageInDoGet() throws ServletException, IOException {
        String errorMessage = "Your cart is empty";
        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("error"), errorMessageArgumentCaptor.capture());
        assertEquals(errorMessage, errorMessageArgumentCaptor.getValue());
    }

    @Test
    public void shouldSendUrlToOrderPage() throws ServletException, IOException {
        Cart cart = new Cart();

        String deliveryCostString = "0";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        String paymentMethodString = "cash";

        String expectedId = "";
        String expectedURL = "order/overview/";
        String test = "test";

        when(cartService.getCart(request)).thenReturn(cart);
        when(servletContext.getInitParameter(Mockito.eq("deliveryCost"))).thenReturn(deliveryCostString);
        when(request.getParameter(Mockito.eq("firstName"))).thenReturn(test);
        when(request.getParameter(Mockito.eq("lastName"))).thenReturn(test);
        when(request.getParameter(Mockito.eq("phoneNumber"))).thenReturn(test);
        when(request.getParameter(Mockito.eq("address"))).thenReturn(test);
        when(request.getParameter(Mockito.eq("deliveryDate"))).thenReturn(dateString);
        when(request.getParameter(Mockito.eq("paymentMethod"))).thenReturn(paymentMethodString);

        servlet.doPost(request, response);

        verify(orderService).placeOrder(orderArgumentCaptor.capture());
        assertEquals(expectedId, orderArgumentCaptor.getValue().getSecureId());
        verify(cartService).deleteCart(Mockito.eq(request));
        verify(writer).write(urlArgumentCaptor.capture());
        assertEquals(expectedURL, urlArgumentCaptor.getValue());
    }
}
