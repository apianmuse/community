package muse.community.advice;

import muse.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //自定义的默认通用的业务异常处理（拦截MVC可handle的异常）
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //@ResponseBody //返回JSON
    ModelAndView handle(Throwable e, Model model) {
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务器冒烟");
        }
        return new ModelAndView("error");
    }

}
