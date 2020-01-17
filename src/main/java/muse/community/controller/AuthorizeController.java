package muse.community.controller;

import muse.community.dto.AccessTokenDTO;
import muse.community.dto.GithubUser;
import muse.community.mapper.UserMapper;
import muse.community.model.User;
import muse.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String clientUri;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){ //spring会自动把上下文中的request放到这
        //接收两个参数
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken); //githubUser需存入数据库，并写入session

        if(githubUser != null){
            //githubUser需存入数据库类user
            User user = new User();
            user.setToken(UUID.randomUUID().toString());//UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。UUID全局唯一标识符
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            //登录成功，写cookies和session
            request.getSession().setAttribute("githubUser", githubUser);

            //return "redirect:index";//真正跳转到index
            return "redirect:/";
        }else{
            //登录失败，重新登录
            //return "redirect:index";
            return "redirect:/";
            //还需显示失败提示
        }
        //return "index";//地址不变，只是页面渲染成index
    }
}
