package com.es.phoneshop.web.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DosFilter implements Filter {
    private Map<String, RequestTimeCounter> remoteUsers;
    private final int TOO_MANY_REQUESTS = 429;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        remoteUsers = new HashMap<>();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String ip = request.getRemoteAddr();
        long timeOut = Long.parseLong(request.getServletContext().getInitParameter("dosTimeOut")) * 1000;
        long maxCountOfRequests = Long.parseLong(request.getServletContext().getInitParameter("dosMaxCountOfRequests"));

        if (remoteUsers.containsKey(ip)) {
            RequestTimeCounter timeCounter = remoteUsers.get(ip);
            if (getCurrentTime() - timeCounter.lastTime > timeOut) {
                RequestTimeCounter newTimeCounter = new RequestTimeCounter(1L, getCurrentTime());
                remoteUsers.replace(ip, newTimeCounter);
            } else if (timeCounter.countOfRequest <= maxCountOfRequests) {
                timeCounter.countOfRequest = timeCounter.countOfRequest + 1;
                remoteUsers.replace(ip, timeCounter);
            } else {
                httpResponse.sendError(TOO_MANY_REQUESTS);
                return;
            }
        } else {
            remoteUsers.put(ip, new RequestTimeCounter(1L, getCurrentTime()));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public class RequestTimeCounter {
        private long countOfRequest;
        private long lastTime;

        public RequestTimeCounter() {
        }

        public RequestTimeCounter(long countOfRequest, long lastTime) {
            this.countOfRequest = countOfRequest;
            this.lastTime = lastTime;
        }

        public long getCountOfRequest() {
            return countOfRequest;
        }

        public void setCountOfRequest(long countOfRequest) {
            this.countOfRequest = countOfRequest;
        }

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
