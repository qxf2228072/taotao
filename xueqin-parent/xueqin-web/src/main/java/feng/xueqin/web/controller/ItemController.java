/*
 * @(#)ItemController.java V2.0 2016年6月7日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemController.java 
 * @Package feng.xueqin.web.controller 
 * @author qinxf
 * @date 2016年6月7日 下午8:26:10
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月7日】 创建文件   by qinxf
 */
package feng.xueqin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import feng.xueqin.pojo.ItemDesc;
import feng.xueqin.pojo.ItemParamItem;
import feng.xueqin.web.pojo.Item;
import feng.xueqin.web.service.ItemService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemController 
 * @author qinxf
 * @date 2016年6月7日 下午8:26:10
 * @version V2.0 
 *  
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    
    @RequestMapping(value = "/{itemId}",method=RequestMethod.GET)
    public ModelAndView itemDetail(@PathVariable("itemId")Long itemId){
        Item itemDetail = itemService.getItemDetail(itemId);
        ModelAndView mv= new ModelAndView("item");
        mv.addObject("item",itemDetail);

        //商品描述
        ItemDesc itemDesc = itemService.getItemDesc(itemId);
        mv.addObject("itemDesc",itemDesc);
        
        //商品参数
        String html = itemService.queryParam(itemId);
        mv.addObject("itemParam",html);
        return mv;
        
    }
}
