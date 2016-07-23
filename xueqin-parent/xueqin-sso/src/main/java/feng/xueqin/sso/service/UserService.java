/*
 * @(#)UserService.java V2.0 2016年6月29日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: UserService.java 
 * @Package feng.xueqin.sso.service 
 * @author qinxf
 * @date 2016年6月29日 上午10:28:24
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月29日】 创建文件   by qinxf
 */
package feng.xueqin.sso.service;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feng.xueqin.common.service.RedisService;
import feng.xueqin.sso.mapper.UserMapper;
import feng.xueqin.sso.pojo.User;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: UserService 
 * @author qinxf
 * @date 2016年6月29日 上午10:28:24
 * @version V2.0 
 *  
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisService redisService;
    
    
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: check 
     * @date 2016年6月29日 上午10:46:56
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param param
     * @param type
     * @return
     */
    
    public Boolean check(String param, Integer type) {
        User u = new User();
        if(type>3||type<1) return null;
        switch (type) {
        case 1:
            u.setUsername(param);
            break;
        case 2:
            u.setPhone(param);
            break;
        case 3:
            u.setPassword(param);
            break;
        }
        
        return userMapper.selectOne(u)!=null;
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: saveUser 
     * @date 2016年6月29日 下午1:40:41
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param user
     * @return
     */
    
    public Boolean saveUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        
        return userMapper.insert(user) == 1;
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: doLogin 
     * @date 2016年6月29日 下午4:10:06
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param username
     * @param password
     * @return
     * @throws Exception 
     */
    
    public String doLogin(String username, String password) throws Exception {
        User u = new User();
        u.setUsername(username);
        u = userMapper.selectOne(u);
        if(null == u){
            return null;
        }
        if(!StringUtils.equals(u.getPassword(), DigestUtils.md5Hex(password))){
            return null;
        }
        String token =  DigestUtils.md5Hex(System.currentTimeMillis()+ username);
        redisService.set("TOKEN_"+token, mapper.writeValueAsString(u), 60*30);
        return token;
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryToken 
     * @date 2016年6月29日 下午5:06:17
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param token
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    
    public User queryToken(String token){
        String jsonData = redisService.get("TOKEN_"+token);
        if(StringUtils.isEmpty(jsonData)){
            return null;
        }
        User user =null;
        try {
            user = mapper.readValue(jsonData, User.class);
            if(null == user){
                return null;
            }
            redisService.expire("TOKEN_"+token, 60*30);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }
    
}
