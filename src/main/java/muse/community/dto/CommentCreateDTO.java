package muse.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    //评论时前端传给后端，用来创建评论
    private Long parentId; //问题或者评论的id
    private String content;
    private Integer type;
}
