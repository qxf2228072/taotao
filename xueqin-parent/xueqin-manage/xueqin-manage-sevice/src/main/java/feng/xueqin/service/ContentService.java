/*
 * @(#)ContentService.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ContentService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年5月10日 下午5:32:20
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import feng.xueqin.mapper.ContentMapper;
import feng.xueqin.pojo.Content;
import feng.xueqin.pojo.Item;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ContentService 
 * @author qinxf
 * @date 2016年5月10日 下午5:32:20
 * @version V2.0 
 *  
 */
@Service
public  class ContentService extends BaseService<Content> {

    @Resource
    private ContentMapper contentMapper;
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryPageList 
     * @date 2016年5月12日 上午10:35:07
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param page
     * @param rows
     * @return
     */
    
    public PageInfo<Content> queryPageList(Integer page, Integer rows,Long categoryId) {
        PageHelper.startPage(page, rows);
        Example exampl = new Example(Content.class);
        Criteria createCriteria = exampl.createCriteria();
        createCriteria.andEqualTo("categoryId", categoryId);
        exampl.setOrderByClause("updated DESC");
        List<Content> list = contentMapper.selectByExample(exampl);
        return new PageInfo<Content>(list);
    }

}
