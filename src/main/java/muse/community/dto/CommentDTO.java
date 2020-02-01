package muse.community.dto;

import lombok.Data;
import muse.community.model.User;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId; //问题或者评论的id
    private Integer type; //回复类型
    private String content; //回复内容
    private Long commentator; //回复人Id
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount; //点赞数
    private User user;
    private Integer commentCount;
}
