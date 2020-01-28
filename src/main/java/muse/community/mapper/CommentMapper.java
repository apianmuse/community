package muse.community.mapper;

import muse.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,content,commentator,gmt_create,gmt_modified,like_count) values (#{parentId},#{type},#{content},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount})")
    void create(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    Comment getByCommentId(@Param("parentId") Long parentId);

    @Select("select * from comment where parent_id = #{parentId} and type=1 Order By GMT_CREATE Desc")
    List<Comment> getByQuestionIdInQuestion(Long id);
}
