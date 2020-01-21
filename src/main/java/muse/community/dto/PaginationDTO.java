package muse.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    //返回页码信息
    private List<QuestionDTO> questions; //本页的问题信息
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>(); //需展示的页码
    private Integer totalPage;

    public void setPagination(Integer page) {

        this.page = page;

        //添加应显示的页码
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page - i > 0){
                pages.add(0,page-i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //是否展示上一页
        showPrevious = (page == 1)?false:true;
        //是否展示下一页
        showNext = (page == totalPage)?false:true;
        //是否展示第一页
        showFirstPage = (pages.contains(1))?false:true;
        //是否展示最后一页
        showEndPage = (pages.contains(totalPage))?false:true;

    }

}
