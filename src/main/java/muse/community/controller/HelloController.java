package muse.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name="name") String name, Model model){
        //(name="name") 定义传过来的key是什么
        //String name接收参数值
        model.addAttribute("name",name);//参数放到里面返回到页面中
        return "hello";//去templates中找同名html文件渲染成网页
    }
}
