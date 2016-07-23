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
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import feng.xueqin.common.utils.EasyUIResult;
import feng.xueqin.pojo.Item;
import feng.xueqin.pojo.ItemDesc;
import feng.xueqin.pojo.ItemParam;
import feng.xueqin.service.ItemDescService;
import feng.xueqin.service.ItemParamService;

/** 
 * 目录结构
 * 
 * @ClassName: ItemParamController 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0 
 *  
 */

@Controller
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;
    @RequestMapping(value = "/item/param/{itemCatId}",method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(
            @PathVariable("itemCatId")Long cid, 
            @RequestParam("paramData")String paramData){
        
        try {
            ItemParam  itemParam = new ItemParam();
            itemParam.setId(null);
            itemParam.setItemCatId(cid);
            itemParam.setParamData(paramData);
            itemParamService.save(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @RequestMapping(value = "/item/param/{cid}",method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemCatId(@PathVariable("cid")Long cid){
        
        try {
            ItemParam  itemParam = new ItemParam();
            itemParam.setItemCatId(cid);
            ItemParam queryOne = itemParamService.queryOne(itemParam);
            if(null == queryOne){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok().body(queryOne);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
   
    @RequestMapping(value="/item/param/list",method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> getItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "20") Integer rows){
        try {
            PageInfo<ItemParam> info = itemParamService.queryPageList(page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(info.getTotal(), info.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
}
