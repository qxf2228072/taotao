/*
 * @(#)ItemCatApiController.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCatApiController.java 
 * @Package feng.xueqin.controller.api 
 * @author qinxf
 * @date 2016年5月10日 上午10:19:22
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feng.xueqin.common.utils.ItemCatResult;
import feng.xueqin.service.ItemCatService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCatApiController 
 * @author qinxf
 * @date 2016年5月10日 上午10:19:22
 * @version V2.0 
 *  
 */
@Controller
public class ItemCatApiController {
    
    @Autowired
    private ItemCatService itemCatService;
    
    @RequestMapping(value="/api/item/cat",method=RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryAllToTree(){
        try {
            ItemCatResult itemCatResult = itemCatService.queryTree();
            return ResponseEntity.ok(itemCatResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
