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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feng.xueqin.pojo.ContentCategory;
import feng.xueqin.service.ContentCategoryService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ContentCategoryController 
 * @author qinxf
 * @date 2016年5月11日 上午11:01:53
 * @version V2.0 
 *  
 */
@RequestMapping("/content/category")
@Controller
public class ContentCategoryController {
    
    @Autowired
    private ContentCategoryService contentCategoryService;
    
    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<List<ContentCategory>> queryContentCategoryAll(
            @RequestParam(value = "id",defaultValue = "0")Long parentId){
        try {
            ContentCategory category = new ContentCategory();
            category.setParentId(parentId);
            List<ContentCategory> contentCategories = contentCategoryService.queryListByWhere(category);
            if(contentCategories.isEmpty() || contentCategories.size()<=0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(contentCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<ContentCategory> saveCategory(@RequestParam("name")String name,@RequestParam("parentId")Long parentId){
       try {
        ContentCategory contentCatgrory = contentCategoryService.saveContentCatgrory(name,parentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(contentCatgrory);
    } catch (Exception e) {
        e.printStackTrace();
    }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);    
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    private ResponseEntity<Void> updateCategory(@RequestParam("name")String name,@RequestParam("id")Long id){
       try {
           ContentCategory contentCatgrory = new ContentCategory();
           contentCatgrory.setId(id);
           contentCatgrory.setUpdated(new Date());
           contentCatgrory.setName(name);
           contentCategoryService.updateSelective(contentCatgrory);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    } catch (Exception e) {
        e.printStackTrace();
    }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);    
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    private ResponseEntity<Void> deleteCategory(@RequestParam("parentId")Long parentId,@RequestParam("id")Long id){
       try {
           contentCategoryService.deleteCategoryAll(parentId,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
        e.printStackTrace();
    }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();    
    }
}
