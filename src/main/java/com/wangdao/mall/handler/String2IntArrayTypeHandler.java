/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 9:31
 **/
package com.wangdao.mall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字符串要转换成 int[] 数组
 */
@MappedTypes(Integer[].class)
public class String2IntArrayTypeHandler implements TypeHandler<Integer[]> {

    /**
     * javabeen中的类型 ————> 数据库中的类型
     * @param preparedStatement
     * @param index
     * @param integers
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(index,parseArray2String(integers));  // Integer[]数组 转换成的字符串数据
    }

    /**
     * 上面方法的下属方法
     * @param integers
     * @return
     */
    private String parseArray2String(Integer[] integers) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(integers);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串 ————> Integer类型数组
     * @param resultSet
     * @param columnName   这个参数用来获得resultSet的列名 将列名传递给转换的parsesString2Array()
     * @return
     * @throws SQLException
     */
    @Override
    public Integer[] getResult(ResultSet resultSet, String columnName) throws SQLException {
        //根据列名获得数据
        String string = resultSet.getString(columnName);
        //将获得的数据转换成目标类型
        return parseString2Array(string);
    }

    /**
     * 字符串————> Integer数组  上面方法的下属方法
     * @param string
     * @return
     */
    private Integer[] parseString2Array(String string) {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] integers = new Integer[0];
        try {
            integers = objectMapper.readValue(string, Integer[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return integers;
    }

    /**
     *
     * @param resultSet
     * @param index
     * @return
     * @throws SQLException
     */
    @Override
    public Integer[] getResult(ResultSet resultSet, int index) throws SQLException {
        String string = resultSet.getString(index);
        return parseString2Array(string);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return parseString2Array(string);
    }
}
