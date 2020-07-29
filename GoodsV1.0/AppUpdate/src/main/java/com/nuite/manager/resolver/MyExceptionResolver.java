package com.nuite.manager.resolver;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionResolver {

    @ExceptionHandler(Exception.class)
    public ModelAndView resolveException(Exception ex) {
        // TODO Auto-generated method stub
        System.out.println("==============异常开始=============");

        ex.printStackTrace();
        System.out.println("==============异常结束=============");
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
        return mv;
    }

}
