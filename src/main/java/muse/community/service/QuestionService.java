package muse.community.service;

import muse.community.dto.PaginationDTO;
import muse.community.dto.QuestionDTO;
import muse.community.mapper.QuestionMapper;
import muse.community.mapper.UserMapper;
import muse.community.model.Question;
import muse.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    //从question表中取问题数据，根据问题发布者的id，从user表中取头像

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PaginationDTO List(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count(); //数据库中所有问题数量

        //总页数
        Integer totalPage; //Integer默认初始值为0
        totalPage = (totalCount % size == 0)?(totalCount / size):(totalCount / size + 1);
        paginationDTO.setTotalPage(totalPage);

        //页码容错处理
        if(page < 1){
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        paginationDTO.setPagination(page);//计算所有需要显示的分页页码和箭头显示

        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.List(offset,size); //选一页的问题出来
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //questionDTOList只存放了一页问题的信息，还需返回分页页码信息DTO(一般是返回数据由js完成分页页码，此处用后端实现)

        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO); //快速把前一个对象的属性拷贝到后一个对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

       // return questionDTOList;
        return paginationDTO;
    }

    public PaginationDTO List(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId); //数据库中所有问题数量

        //总页数
        Integer totalPage; //Integer默认初始值为0
        totalPage = (totalCount % size == 0)?(totalCount / size):(totalCount / size + 1);
        paginationDTO.setTotalPage(totalPage);

        //页码容错处理
        if(page < 1){
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        paginationDTO.setPagination(page);//计算所有需要显示的分页页码和箭头显示

        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.ListByUserId(userId,offset,size); //选一页的问题出来
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //questionDTOList只存放了一页问题的信息，还需返回分页页码信息DTO(一般是返回数据由js完成分页页码，此处用后端实现)

        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO); //快速把前一个对象的属性拷贝到后一个对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //发布问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //修改问题
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
