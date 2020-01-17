package muse.community.mapper;

import muse.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //只有UserMapper才能访问数据库
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    //如果参数是一个类，则#{}中的参数会自动匹配对象中的变量
    void insert(User user);

    @Select("select * from user where token=#{token} ")
    //如果参数不是一个类，则需要@Param
    User findByToken(@Param("token") String token);
}
