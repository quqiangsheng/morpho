package com.max256.morpho.common.web.filter;




import com.max256.morpho.common.util.IPUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 输出请求信息到日志 方便调试
 * URI、参数、header、cookie等
 * 
 * 开关 根据日志级别 debug会自动开启输出
 * 
 */
public  class DebugRequestAndResponseFilter extends BaseFilter {
    private static final Logger log = LoggerFactory.getLogger(DebugRequestAndResponseFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	 HttpServletRequest httpRequest = (HttpServletRequest) request;
         HttpServletResponse httpResponse = (HttpServletResponse) response;
    	
        if (log.isDebugEnabled()) {
            debugRequest(httpRequest);
        }
        chain.doFilter(request, response);

        if (log.isDebugEnabled()) {
            debugResponse(httpResponse);
        }
    }

    private void debugRequest(HttpServletRequest request) {
        log.debug("=====================request begin==========================");
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            uri = uri + "?" + queryString;
        }
        log.debug("{}:{}", request.getMethod(), uri);
        log.debug("remote ip:{}  sessionId:{}  ", IPUtils.getIp(request), request.getRequestedSessionId());
        log.debug("===header begin============================================");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = headersToString(request.getHeaders(name));
            log.info("{}={}", name, value);
        }
        log.debug("===header   end============================================");
        log.debug("===parameter begin==========================================");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = StringUtils.join(request.getParameterValues(name), "||");
            log.debug("{}={}", name, value);
        }
        log.debug("===parameter   end==========================================");
        log.debug("=====================request   end==========================");
    }

    private String headersToString(Enumeration<String> headers) {
        StringBuilder s = new StringBuilder();
        int index = 0;
        while (headers.hasMoreElements()) {
            if (index > 0) {
                s.append("||");
            }
            s.append(headers.nextElement());
        }
        return s.toString();
    }

    private void debugResponse(HttpServletResponse response) {
        log.debug("=====================response begin==========================");
        log.debug("status:{}", response.getStatus(), response.getContentType());
        log.debug("contentType:{}, characterEncoding:{}", response.getContentType(), response.getCharacterEncoding());
        log.debug("===header begin============================================");
        Collection<String> headerNames = response.getHeaderNames();
        for (String name : headerNames) {
            String value = StringUtils.join(response.getHeaders(name), "||");
            log.debug("{}={}", name, value);
        }
        log.debug("===header   end============================================");
        log.debug("=====================response   end==========================");
    }


}
