package muse.community.mapper;

import muse.community.dto.QuestionDTO;
import muse.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
//操作question表
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> List(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question") //count(1)表示第一列
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> ListByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}") //count(1)表示第一列
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
