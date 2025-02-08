package org.subin.orbit.global.common.logging;


import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청 본문을 캐싱 처리하여 여러 번 읽기 위한 요청 본문 래퍼 클래스
 */

public class CachingRequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * HTTP 요청 본문의 인코딩 정보
     */
    private final Charset encoding;

    /**
     * HTTP 요청 본문을 바이트 배열로 캐싱한 데이터
     */
    private final byte[] cachedBody;
    
    public CachingRequestBodyWrapper(HttpServletRequest request) {
        super(request);
        this.encoding = getRequestEncoding(request);
        this.cachedBody = cachedRequestBody(request);
    }

    /**
     * HTTP 요청 본문의 인코딩 정보를 반환한다.
     * 
     * @param request   HTTP 요청 객체
     * @return          인코딩 정보를 반환하되, 만약 인코딩 정보가 존재하지 않는다면 <b>UTF-8</b> 인코딩 정보 반환
     */
    private Charset getRequestEncoding(HttpServletRequest request) {
        final String charsetEncoding = request.getCharacterEncoding();
        return StringUtils.hasText(charsetEncoding) ? Charset.forName(charsetEncoding) : StandardCharsets.UTF_8;
    }

    /**
     * HTTP 요청 본문을 바이트 배열로 캐싱 처리 후 그 캐싱 값을 반환한다.
     * @param request   HTTP 요청 객체
     * @return          바이트 배열의 캐싱 값을 반환하되, 예외 발생시 크기가 0인 바이트 배열을 반환
     */
    private byte[] cachedRequestBody(HttpServletRequest request) {
        try {
            return StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e)  {
            return new byte[0];
        }
    }

    /**
     * 캐싱된 요청 본문을 읽어들이기 위한 {@link ServletInputStream}을 반환한다.
     *
     * @return 캐싱 값을 실질적으로 읽어 들이는 작업을 수행하는 {@link ServletInputStream}반환
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {

            private final ByteArrayInputStream cachedInputStream = new ByteArrayInputStream(cachedBody);

            @Override
            public boolean isFinished() {
                return cachedInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException("Not supported ReadListener.");
            }

            @Override
            public int read() throws IOException {
                return cachedInputStream.read();
            }
        };
    }

    /**
     * 바이트 배열 타입의 캐싱 값을 문자 기반으로 읽어들이기 위한 {@link BufferedReader}를 반환한다.
     *
     * @return 캐싱값을 실질적으로 문자 기반으로 읽어들이는 작업을 수행하는 {@link BufferedReader}반환
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }
}