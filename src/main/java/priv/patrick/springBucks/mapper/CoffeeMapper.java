package priv.patrick.springBucks.mapper;

import org.apache.ibatis.annotations.*;
import priv.patrick.springBucks.pojo.Coffee;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee (name, price, create_time, update_time)"
            + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            // @Result(column = "update_time", property = "updateTime"),
    })
    Coffee findById(@Param("id") Long id);

    @Select("select * from t_coffee where name = #{name}")
    List<Coffee> findByName(String name);

    @Select("select * from t_coffee")
    List<Coffee> selectAllCoffee();

}
