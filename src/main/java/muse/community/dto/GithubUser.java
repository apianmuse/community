package muse.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id; //long防止用户量暴增时不会越界
    private String bio;
    private String avatar_url;
}
