package muse.community.service;

import muse.community.dto.CommentDTO;
import muse.community.enums.CommentTypeEnum;
import muse.community.exception.CustomizeErrorCode;
import muse.community.exception.CustomizeException;
import muse.community.mapper.CommentMapper;
import muse.community.mapper.QuestionMapper;
import muse.community.mapper.UserMapper;
import muse.community.model.Comment;
import muse.community.model.Question;
import muse.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    /*
    * 添加评论
    * */
    @Transactional
    public void insert(Comment comment) {
        //未登录异常判断
        if(comment.getParentId() == null || comment.getParentId() ==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }
        //评论类型异常判断
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_WRONG);
        }

        //插入数据库
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.getByCommentId(comment.getParentId());
            if(dbComment == null){ //没有此条评论
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.create(comment);
            //累加二级评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            int commentCount =commentMapper.getByCommentId(parentComment.getId()).getCommentCount();
            commentMapper.incCommentCount(parentComment.getId(), commentCount+1);
        }else {
            //回复问题
            Question question = questionMapper.getByQuestionId(comment.getParentId());
            if(question == null){ //没有这个问题
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.create(comment);
            //累加一级评论数
            int commentCount = questionMapper.getByQuestionId(comment.getParentId()).getCommentCount();
            questionMapper.incCommentCount(comment.getParentId(), commentCount+1);
        }
    }

    /*
    * 获取一级或二级评论
    * */
    public List<CommentDTO> getCommentsByIdAndType(Long id, CommentTypeEnum type) {
        List<Comment> comments = commentMapper.getCommentsByIdAndType(id,type.getType());
        if(comments.size() == 0){
            return new ArrayList<>();
        }

        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet()); //此处使用不同于question那遍历拿user的方法
                                                    //map遍历返回一个结果集
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人转换为Map
        List<User> users = new ArrayList<>();
        for(Long userId:userIds){
            users.add(userMapper.findById(userId));
        }

        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user)); //把userId和user匹配起来

        //转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
