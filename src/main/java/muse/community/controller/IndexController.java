package muse.community.controller;

import muse.community.dto.QuestionDTO;
import muse.community.mapper.UserMapper;
import muse.community.model.User;
import muse.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper; //只有UserMapper才能访问数据库

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token); //从数据库取出token对应的user
                    //System.out.println(user.getAvatarUrl()); //null
                    if(user != null){
                        request.getSession().setAttribute("User", user);
                    }
                    break;
                }
            }
        }

        //跳转到index之前，从数据库查询问题信息，显示在首页
        List<QuestionDTO> questionDTOList = questionService.List(); //返回问题信息、发布问题用户的头像
        model.addAttribute("questions",questionDTOList);
       return "index";
    }
}
