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
import feng.xueqin.pojo.ItemParamItem;
import feng.xueqin.service.ItemDescService;
import feng.xueqin.service.ItemParamItemService;

/** 
 * 目录结构
 * 
 * @ClassName: UserController 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0 
 *  
 */

@RequestMapping("item/param/item")
@Controller
public class ItemParamItemController {
    
    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 根据商品id查询商品规格参数数据
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemId(@PathVariable("itemId") Long itemId) {
        try {
            ItemParamItem record = new ItemParamItem();
            record.setItemId(itemId);
            ItemParamItem itemParamItem = this.itemParamItemService.queryOne(record);
            if (null == itemParamItem) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
