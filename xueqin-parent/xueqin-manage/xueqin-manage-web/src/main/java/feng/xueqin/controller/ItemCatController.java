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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import feng.xueqin.pojo.ItemCat;
import feng.xueqin.service.ItemCatService;

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
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping(value="/item/cat",method = RequestMethod.GET )
    public ResponseEntity<List<ItemCat>> item(@RequestParam(value= "id",defaultValue = "0") Long parentId){
        try {
            ItemCat t = new ItemCat();
            t.setParentId(parentId);
            List<ItemCat> itemCatList = itemCatService.queryListByWhere(t);
            if(null == itemCatList||itemCatList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemCatList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(value="/item/catname/{cid}",method = RequestMethod.GET )
    public ResponseEntity<ItemCat> itemname(@PathVariable("cid") Long cid){
        try {
            
            ItemCat item = itemCatService.queryById(cid);
            if(null == item){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
}
