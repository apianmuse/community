package muse.community.controller;

import muse.community.dto.CommentCreateDTO;
import muse.community.dto.CommentDTO;
import muse.community.dto.ResultDTO;
import muse.community.enums.CommentTypeEnum;
import muse.community.exception.CustomizeErrorCode;
import muse.community.model.Comment;
import muse.community.model.User;
import muse.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;

    /*
    * 发布评论
    * */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST) //用JSON方式传参
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,//@RequestBody自动反序列化json，把对应的key赋值到DTO
                       HttpServletRequest request){

        //判断是否登录
        User user = (User)request.getSession().getAttribute("User");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN); //返回给前端的状态
        }

        //回复内容不能为空
        if(commentCreateDTO == null || commentCreateDTO.getContent() == null|| commentCreateDTO.getContent() == ""){
             //if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){ //Apache Commons Lang
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY); //返回给前端的状态
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());//type=1是回复问题，type=2是回复评论
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment); //commentService需要异常处理，提示当前回复已经不存在

        return ResultDTO.okOf();
        //@ResponseBody把对象自动序列化成json发给前端
    }

    /*
    * 展示二级评论
    * */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentsDTOs =commentService.getCommentsByIdAndType(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentsDTOs); //除了返回okOf还要返回二级评论的List
    }
}
