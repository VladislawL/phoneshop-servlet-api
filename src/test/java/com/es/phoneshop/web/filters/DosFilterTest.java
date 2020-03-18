package com.es.phoneshop.web.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private Map<String, DosFilter.RequestTimeCounter> remoteUsers;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private ServletContext servletContext;
    @InjectMocks
    private DosFilter dosFilter;
    @Captor
    private ArgumentCaptor<DosFilter.RequestTimeCounter> requestTimeCounterArgumentCaptor;

    private String ip = "0.0.0.0";
    private String dosTimeOut = "60";
    private String dosMaxCountOfRequests = "20";

    @Before
    public void setup() {
        when(request.getRemoteAddr()).thenReturn(ip);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("dosTimeOut")).thenReturn(dosTimeOut);
        when(servletContext.getInitParameter("dosMaxCountOfRequests")).thenReturn(dosMaxCountOfRequests);
    }

    @Test
    public void shouldPutRemoteUserIntoMap() throws IOException, ServletException {
        long countOfRequest = 1L;
        long lastTime = System.currentTimeMillis();
        DosFilter.RequestTimeCounter requestTimeCounter = dosFilter.new RequestTimeCounter();
        requestTimeCounter.setCountOfRequest(countOfRequest);
        requestTimeCounter.setLastTime(lastTime);

        when(remoteUsers.containsKey(Mockito.eq(ip))).thenReturn(false);

        dosFilter.doFilter(request, response, chain);

        verify(remoteUsers).put(Mockito.eq(ip), requestTimeCounterArgumentCaptor.capture());
        assertEquals(countOfRequest, requestTimeCounterArgumentCaptor.getValue().getCountOfRequest());
    }

    @Test
    public void shouldIncreaseRequestTimeCounter() throws IOException, ServletException {
        long countOfRequest = 1L;
        long expectedCountOfRequest = 2L;
        long lastTime = System.currentTimeMillis();
        DosFilter.RequestTimeCounter requestTimeCounter = dosFilter.new RequestTimeCounter();
        requestTimeCounter.setCountOfRequest(countOfRequest);
        requestTimeCounter.setLastTime(lastTime);

        when(remoteUsers.containsKey(Mockito.eq(ip))).thenReturn(true);
        when(remoteUsers.get(Mockito.eq(ip))).thenReturn(requestTimeCounter);

        dosFilter.doFilter(request, response, chain);

        verify(remoteUsers).replace(Mockito.eq(ip), requestTimeCounterArgumentCaptor.capture());
        assertEquals(expectedCountOfRequest, requestTimeCounterArgumentCaptor.getValue().getCountOfRequest());
    }

    @Test
    public void shouldResetRequestTimeCounter() throws IOException, ServletException {
        long countOfRequest = 20L;
        long expectedCountOfRequest = 1L;
        long lastTime = 0L;
        DosFilter.RequestTimeCounter requestTimeCounter = dosFilter.new RequestTimeCounter();
        requestTimeCounter.setCountOfRequest(countOfRequest);
        requestTimeCounter.setLastTime(lastTime);

        when(remoteUsers.containsKey(Mockito.eq(ip))).thenReturn(true);
        when(remoteUsers.get(Mockito.eq(ip))).thenReturn(requestTimeCounter);

        dosFilter.doFilter(request, response, chain);

        verify(remoteUsers).replace(Mockito.eq(ip), requestTimeCounterArgumentCaptor.capture());
        assertEquals(expectedCountOfRequest, requestTimeCounterArgumentCaptor.getValue().getCountOfRequest());
    }

    @Test
    public void shouldNotPassFilter() throws IOException, ServletException {
        long countOfRequest = 21L;
        long lastTime = System.currentTimeMillis();
        DosFilter.RequestTimeCounter requestTimeCounter = dosFilter.new RequestTimeCounter();
        requestTimeCounter.setCountOfRequest(countOfRequest);
        requestTimeCounter.setLastTime(lastTime);

        when(remoteUsers.containsKey(Mockito.eq(ip))).thenReturn(true);
        when(remoteUsers.get(Mockito.eq(ip))).thenReturn(requestTimeCounter);

        dosFilter.doFilter(request, response, chain);

        verify(remoteUsers, never()).replace(Mockito.eq(ip), requestTimeCounterArgumentCaptor.capture());
    }
}
