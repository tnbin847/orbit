package org.subin.orbit.global.config.security;

/**
 * 스프링 시큐리티를 이용한 작업에서 사용될 상수값들을 정의한 상수 클래스
 */

public final class SecurityConstants {

    /**
     * 별도의 인증 요구 없이, 접근을 허용하는 요청 URL들을 정의한 상수 배열
     */
    public static final String[] PUBLICY_REQUEST_MATCHERS = {
            "/",
            "/sign-up",
            "/api/v1/login",
            "/api/v1/logout",
            "/api/v1/user/exists/**",
            "/api/v1/user"
    };
}