/*
 * @(#)ContentCategoryService.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ContentCategoryService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年5月10日 下午5:31:18
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import feng.xueqin.mapper.ContentCategoryMapper;
import feng.xueqin.pojo.ContentCategory;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ContentCategoryService 
 * @author qinxf
 * @date 2016年5月10日 下午5:31:18
 * @version V2.0 
 *  
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    @Resource
    private ContentCategoryMapper categoryMapper;
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: saveContentCatgrory 
     * @date 2016年5月11日 下午1:28:57
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param name
     * @param parentId
     */
    
    public ContentCategory saveContentCatgrory(String name, Long parentId) {
        //保存当前节点
        ContentCategory category = new ContentCategory();
        category.setId(null);
        category.setParentId(parentId);
        category.setName(name);
        category.setIsParent(false);
        category.setCreated(new Date());
        category.setUpdated(category.getCreated());
        category.setSortOrder(1);
        category.setStatus(1);
        categoryMapper.insertSelective(category);
        //获取父节点,判断是不是父节点,如果是不做修改,如果不是改成父节点
        ContentCategory parentCatgory = categoryMapper.selectByPrimaryKey(parentId);
        if(!parentCatgory.getIsParent()){
            parentCatgory.setIsParent(true);
            categoryMapper.updateByPrimaryKeySelective(parentCatgory);
        }
        
        return category;
    }
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: deleteCategoryAll 
     * @date 2016年5月11日 下午3:16:27
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param parentId
     * @param id
     */
    
    public void deleteCategoryAll(Long parentId, Long id) {
        //建立查询类
        ContentCategory category = new ContentCategory();
        category.setId(id);
        //1.先删除子类和自己
        ContentCategory contentCategory = categoryMapper.selectByPrimaryKey(category);
        if(!contentCategory.getIsParent()){
            categoryMapper.delete(contentCategory);
        }else {
            //用递归来处理
            List<Object> ids = new ArrayList<Object>();           
            ids.add(id);
            deleteCategory(id,ids);
            super.deleteByIds(ContentCategory.class,"id",ids);
            
        }
        //2.查看是否有同级目录
           //有,父目录不做修改
            //没有,修改父目录为不是父目录
        ContentCategory category1 = new ContentCategory();
        category1.setParentId(parentId);
        List<ContentCategory> list = categoryMapper.select(category1);
        if(null == list || list.isEmpty()){
            category1.setIsParent(false);
            categoryMapper.updateByPrimaryKeySelective(category1);
        }
    }
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: deleteCategory 
     * @date 2016年5月11日 下午4:05:55
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param parentId
     * @param ids
     */
    
    private void deleteCategory(Long parentId, List<Object> ids) {
       ContentCategory category = new ContentCategory();
       category.setParentId(parentId);
       List<ContentCategory> list = categoryMapper.select(category);
        for (ContentCategory contentCategory : list) {
            ids.add(contentCategory.getId());
            if(contentCategory.getIsParent()){
                deleteCategory(contentCategory.getId(), ids);
            }
        }
    }  
}
