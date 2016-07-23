/*
 * @(#)ItemCacheController.java V2.0 2016年6月27日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCacheController.java 
 * @Package feng.xueqin.web.controller 
 * @author qinxf
 * @date 2016年6月27日 下午3:48:25
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月27日】 创建文件   by qinxf
 */
package feng.xueqin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feng.xueqin.common.service.ApiService;
import feng.xueqin.common.service.RedisService;
import feng.xueqin.web.service.ItemService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCacheController 
 * @author qinxf
 * @date 2016年6月27日 下午3:48:25
 * @version V2.0 
 *  
 */
@Controller
@RequestMapping("/item")
public class ItemCacheController {

    @Autowired
    private RedisService redisService;
    
    @RequestMapping(value="/cache/{itemId}",method=RequestMethod.GET)
    public ResponseEntity<Void> deleteItemCache(@PathVariable("itemId")Long itemId){
        try {
            redisService.del(ItemService.ITEMCACHENAME+itemId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
           
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
