package muse.community.controller;

import muse.community.mapper.UserMapper;
import muse.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper; //只有UserMapper才能访问数据库

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token); //从数据库取出token对应的user
                if(user != null){
                    request.getSession().setAttribute("User", user);
                }
                break;
            }
        }
       return "index";
    }
}
