package org.subin.orbit.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}인터페이스를 구현한 {@link Enum}에 정의된 코드값을 데이터베이스에 저장하거나 데이터베이스로부터 가져온 코드값을 {@link Enum}으로
 * 변환하기 위해 매핑 처리를 수행하는 타입 핸들러
 *
 * @param <E>   타입 핸들러에서 처리하고자 하는 {@link Enum}
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    /**
     * 타입 핸들러가 처리할 {@link Enum}
     */
    private final Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) { throw new IllegalArgumentException("Type argument cannot be null."); }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertColumnCodeToEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertColumnCodeToEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertColumnCodeToEnum(cs.getString(columnIndex));
    }

    /**
     * 데이터베이스로부터 가져온 코드값과 일치한 상수값을 정의한 {@link Enum}을 반환한다.
     *
     * @param columnCode    데이터베이스로부터 가져온 코드값
     * @return              코드값과 일치한 상수값이 있다면 해당 상수값을 정의한 {@link Enum}을, 그렇지 않다면 {@code null} 반환
     */
    private E convertColumnCodeToEnum(final String columnCode) {
        try {
            final E[] enumConstants = type.getEnumConstants();
            for (E codeEnum : enumConstants) {
                if (codeEnum.getCode().equals(columnCode)) {
                    return codeEnum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert " + columnCode + " to " + type.getSimpleName()  + ".");
        }
    }
}