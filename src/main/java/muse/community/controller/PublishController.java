package muse.community.controller;

import muse.community.mapper.QuestionMapper;
import muse.community.mapper.UserMapper;
import muse.community.model.Question;
import muse.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){ //服务端api传递页面，需要传递的东西放到Model给前端（前端用th语法）

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //验证非空前后端都要设置，前端放在js
        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("User", user);
                    }
                    break;
                }
            }
        }

        if(user == null){
            model.addAttribute("error","用户未登录");
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
