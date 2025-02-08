package org.subin.orbit.global.common.mybatis;

/**
 * 타입 핸들러를 통해 데이터베이스 처리를 하고자 하는 코드값들을 정의한 {@link Enum}을 일관적으로 구현 및 타입 핸들러에 <br>
 * 적용시키기 위해 정의한 인터페이스
 * <p>
 * - 데이터베이스 처리를 하고자 하는 코드값을 정의한 {@link Enum}은 해당 인터페이스를 반드시 구현해주어야 한다.
 * </p>
 *
 * @see CodeEnumTypeHandler
 */

public interface CodeEnum {

    /**
     * 타입 핸들러를 통해 매핑될 코드값을 반환한다.
     */
    String getCode();

    /**
     * 뷰에 출력할 코드에 대한 라벨을 반환한다.
     */
    String getLabel();
}