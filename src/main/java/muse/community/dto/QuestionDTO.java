package muse.community.dto;

import lombok.Data;
import muse.community.model.User;

@Data
public class QuestionDTO {
    //首页需要显示的关于问题的信息
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;////
}
