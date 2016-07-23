/*
 * @(#)ItemAndDescController.java V2.0 2016年5月4日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemAndDescController.java 
 * @Package feng.xueqin.controller 
 * @author qinxf
 * @date 2016年5月4日 下午8:37:58
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月4日】 创建文件   by qinxf
 */
package feng.xueqin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import feng.xueqin.common.utils.EasyUIResult;
import feng.xueqin.pojo.Item;
import feng.xueqin.service.ItemService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemAndDescController 
 * @author qinxf
 * @date 2016年5月4日 下午8:37:58
 * @version V2.0 
 *  
 */
@RequestMapping("/item")
@Controller
public class ItemAndDescController {

    @Autowired
    private ItemService itemService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams){
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            // 设置初始数据
            item.setStatus(1);

            item.setId(null);// 强制设置id为null

            // 保存商品的基本数据
            this.itemService.saveItem(item, desc,itemParams);
            // 成功 201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> getItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "20") Integer rows){
        try {
            PageInfo<Item> info = itemService.queryPageList(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(info.getTotal(), info.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc") String desc){
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // 保存商品的基本数据
            this.itemService.updateItem(item, desc);
            // 成功 201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @RequestMapping(value="/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable("itemId")Long itemId){
        try {
           Item item = this.itemService.queryById(itemId);
           if(null == item){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
           }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
}
