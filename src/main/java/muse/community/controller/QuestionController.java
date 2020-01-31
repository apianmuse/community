package muse.community.controller;

import muse.community.dto.CommentDTO;
import muse.community.dto.QuestionDTO;
import muse.community.enums.CommentTypeEnum;
import muse.community.service.CommentService;
import muse.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);//问题详情
        List<CommentDTO> comments = commentService.getCommentsByIdAndType(id, CommentTypeEnum.QUESTION);//一级评论列表
        questionService.incView(id);//累加阅读数
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
