package org.subin.orbit.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 상태 컬럼의 값을 처리하기 위해 서로 다른 타입의 값들을 상응되는 의미 별로 정의한 {@link Enum}
 */

@RequiredArgsConstructor
@Getter
public enum StatusFormat {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int numeric;

    private final String string;

    private final boolean bool;

    /**
     * 전달 받은 값에 해당하는 논리형 상태값을 반환한다.
     *
     * @param value 논리형으로 변환하고자 하는 값
     * @return      전달 받은 값이 1일 경우 {@code true}, 0일 경우 {@code false} 반환
     */
    public boolean toBoolean(final int value) {
        return Arrays.stream(values())
                .filter(statusFormat -> statusFormat.getNumeric() == value)
                .findFirst()
                .map(StatusFormat::isBool)
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert " + value + " to boolean."));
    }
}