/*
 * @(#)ContentCategoryController.java V2.0 2016年5月11日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ContentCategoryController.java 
 * @Package feng.xueqin.controller 
 * @author qinxf
 * @date 2016年5月11日 上午11:01:53
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月11日】 创建文件   by qinxf
 */
package feng.xueqin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import feng.xueqin.common.utils.EasyUIResult;
import feng.xueqin.pojo.Content;
import feng.xueqin.service.ContentService;


/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ContentCategoryController 
 * @author qinxf
 * @date 2016年5月11日 上午11:01:53
 * @version V2.0 
 *  
 */
@RequestMapping("/content")
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content){
       try {
           content.setCreated(new Date());
           content.setUpdated(content.getCreated());
        contentService.saveSelective(content);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
        e.printStackTrace();
    }
       return ResponseEntity.status(HttpStatus.CREATED).build();  
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> getItemList(@RequestParam(value="categoryId")Long categoryId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "20") Integer rows){
        try {
            PageInfo<Content> info = contentService.queryPageList(page, rows,categoryId);
            EasyUIResult easyUIResult = new EasyUIResult(info.getTotal(), info.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出错 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
    
    
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseEntity<Integer> deleteContent(Long[] ids){
        try {
            List<Object> idList = new ArrayList<Object>();
            for(Long id:ids){
                idList.add(id);
            }
               Integer deleteByIds = contentService.deleteByIds(Content.class, "id", idList);
               return ResponseEntity.ok().body(deleteByIds);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
