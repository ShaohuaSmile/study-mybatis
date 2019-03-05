package self.shaohua.study.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 空串处理
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringTypeHandler extends BaseTypeHandler<String> {
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, transferParam(parameter));
    }

    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return transferParam(result);
    }

    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return transferParam(result);
    }

    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return transferParam(result);
    }

    /**
     * 空串给默认值‘无’
     * @param parameter
     * @return
     */
    private String transferParam(String parameter){
        return StringUtils.isBlank(parameter)  ?  "无" : parameter;
    }
}
