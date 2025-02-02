package org.subin.orbit.global.common.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CachingRequestBodyWrapper extends HttpServletRequestWrapper {

    public CachingRequestBodyWrapper(HttpServletRequest request) {
        super(request);
    }
}