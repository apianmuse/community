package muse.community.controller;

import muse.community.dto.PaginationDTO;
import muse.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index( Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size){
       //解释器判断是否登录

        //跳转到index之前，从数据库查询问题信息，显示在首页
        PaginationDTO pagination = questionService.List(page,size); //返回问题信息+发布问题用户的头像、分页页码
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
