package muse.community.dto;

public class GithubUser {
    private String name;
    private long id; //long防止用户量暴增时不会越界
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString(){
        return "GithubUser{ name = '"+name+"\'"+", id = "+id+"\'"+", bio = "+bio+"\'"+"}";
    }
}
