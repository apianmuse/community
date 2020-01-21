package muse.community.mapper;

import muse.community.model.User;
import org.apache.ibatis.annotations.*;

//操作user表
@Mapper
public interface UserMapper {
    //只有UserMapper才能访问数据库
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    //如果参数是一个类，则#{}中的参数会自动匹配对象中的变量
    void insert(User user);

    @Select("select * from user where token=#{token} ")
    //如果参数不是一个类，则需要@Param
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id} ")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId} ")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where account_id=#{accountId}")
    void update(User user);
}
