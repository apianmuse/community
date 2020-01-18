package muse.community.service;

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

    public List<QuestionDTO> List() {
        List<Question> questions = questionMapper.List();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO); //快速把前一个对象的属性拷贝到后一个对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
