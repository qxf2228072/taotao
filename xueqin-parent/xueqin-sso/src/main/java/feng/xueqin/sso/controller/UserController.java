/*
 * @(#)UserController.java V2.0 2016年6月29日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: UserController.java 
 * @Package feng.xueqin.sso.controller 
 * @author qinxf
 * @date 2016年6月29日 上午9:45:34
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月29日】 创建文件   by qinxf
 */
package feng.xueqin.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import feng.xueqin.common.utils.CookieUtils;
import feng.xueqin.sso.pojo.User;
import feng.xueqin.sso.service.UserService;

/**
 * TODO 对该类总结性的描述
 * 
 * @ClassName: UserController
 * @author qinxf
 * @date 2016年6月29日 上午9:45:34
 * @version V2.0
 * 
 */
@RequestMapping("user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    private static final String COOKIE_NAME = "TT_TOKEN";

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String tologin() {
        return "login";
    }

    @RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
            @PathVariable("type") Integer type) {
        try {
            Boolean b = userService.check(param, type);
            if (null == b) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @ResponseBody
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (bindingResult.hasErrors()) {
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String defaultMessage = objectError.getDefaultMessage();
                msgs.add(defaultMessage);
            }
            result.put("status", "400");
            result.put("data", null);
            result.put("msg", StringUtils.join(msgs, ","));
            return result;
        }
        Boolean b = userService.saveUser(user);
        if (b) {
            result.put("status", "200");
        } else {
            result.put("status", "300");
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Map<String, Object> doLogin(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String token = userService.doLogin(username,password);
            if (null == token) {
                result.put("status", "400");
            } else {
                result.put("status", "200");
                CookieUtils.setCookie(request, response, COOKIE_NAME, token);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.put("status", "500");
        }
        return result;
    }
    
    @RequestMapping(value="/{token}",method = RequestMethod.GET)
    private ResponseEntity<User> queryUser(@PathVariable("token")String token){
       try {
        User u = userService.queryToken(token);
           if(null == u ){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
           }
           return ResponseEntity.ok(u);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
