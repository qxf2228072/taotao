package feng.xueqin.controller;
/*
 * @(#)UserController.java V2.0 2016年4月25日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: UserController.java 
 * @Package feng.xueqin.controller 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年4月25日】 创建文件   by qinxf
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import feng.xueqin.pojo.ItemDesc;
import feng.xueqin.service.ItemDescService;

/** 
 * 目录结构
 * 
 * @ClassName: UserController 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0 
 *  
 */

@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService ItemDescService;
    
    @RequestMapping(value="/item/desc/{cid}",method = RequestMethod.GET )
    public ResponseEntity<ItemDesc> itemname(@PathVariable("cid") Long cid){
        try {
            
             ItemDesc desc = ItemDescService.queryById(cid);
            if(null == desc){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(desc);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
}
