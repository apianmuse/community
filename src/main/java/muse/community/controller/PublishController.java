package muse.community.controller;

import muse.community.cache.TagCache;
import muse.community.dto.QuestionDTO;
import muse.community.model.Question;
import muse.community.model.User;
import muse.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionService questionService;

    /*
    * 编辑问题
    * */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());////
        model.addAttribute("tags", TagCache.get()); //获取库中所有标签
        return "publish";
    }

    /*
    * 发布问题页面
    * */
    @GetMapping("/publish")
    public String publish( Model model){
        model.addAttribute("tags", TagCache.get()); //获取库中所有标签
        return "publish";
    }

    /*
    * 提交问题
    * */
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Long id,//form post过来的数据
                            HttpServletRequest request,
                            Model model){ //服务端api传递页面，需要传递的东西放到Model给前端（前端用th语法）

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get()); //获取库中所有标签

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
        String invalid = TagCache.filterInvalid(tag);
        if(invalid == null || invalid == ""){
            model.addAttribute("error","输入非法标签");
            return "publish";
        }

        //解释器判断是否登录
        User user = (User)request.getSession().getAttribute("User");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question); //类似于用户登录那个判断
        //questionMapper.create(question);
        return "redirect:/";
    }
}
