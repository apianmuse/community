package muse.community.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount; //问题回复数
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
