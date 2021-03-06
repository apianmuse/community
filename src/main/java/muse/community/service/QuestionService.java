package muse.community.service;

import muse.community.dto.PaginationDTO;
import muse.community.dto.QuestionDTO;
import muse.community.exception.CustomizeErrorCode;
import muse.community.exception.CustomizeException;
import muse.community.mapper.QuestionMapper;
import muse.community.mapper.UserMapper;
import muse.community.model.Question;
import muse.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    //从question表中取问题数据，根据问题发布者的id，从user表中取头像

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    /*
    * 首页展示问题
    * */
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

    /*
    * 我的问题
    * */
    public PaginationDTO List(Long userId, Integer page, Integer size) {
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

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getByQuestionId(id);
        //问题不存在异常
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
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
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            System.out.println(question);
            questionMapper.create(question);//////////有问题
        }else {
            //修改问题
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
            //此处还应判断是否更新成功（因为有可能页面中修改问题时，另一个页面删除了此问题），但update返回类型void，还不知如何判断？
        }
    }

    /*
    * 累加阅读数
    * */
    public void incView(Long id) {
        int viewCount = questionMapper.getByQuestionId(id).getViewCount();
        questionMapper.updateViewCount(id, viewCount+1);
    }

    /*
    * 获取相关问题
    * */
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        String tags = queryDTO.getTag();
        if( tags == null || tags ==""){
            return new ArrayList<>();
        }
        String regexTags = tags.replace(',','|');
        System.out.println(regexTags);
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTags);
        List<Question> questions = questionMapper.selectRelated(question);  //从数据库根据标签找相关问题
        //把Question变为QuestionDTO
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
