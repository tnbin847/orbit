package org.subin.orbit.global.common.logging;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CachingResponseBodyWrapper extends HttpServletResponseWrapper {

    public CachingResponseBodyWrapper(HttpServletResponse response) {
        super(response);
    }
}