/*
 * @(#)ItemCatMapper.java V2.0 2016年4月28日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCatMapper.java 
 * @Package feng.xueqin.mapper 
 * @author qinxf
 * @date 2016年4月28日 上午10:22:36
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年4月28日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import feng.xueqin.mapper.ItemParamMapper;
import feng.xueqin.pojo.ItemParam;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCatMapper 
 * @author qinxf
 * @date 2016年4月28日 上午10:22:36
 * @version V2.0 
 *  
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryPageList 
     * @date 2016年5月9日 下午2:07:31
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param page
     * @param rows
     * @return
     */
    
    public PageInfo<ItemParam> queryPageList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example exampl = new Example(ItemParam.class);
        exampl.setOrderByClause("updated DESC");
        List<ItemParam> list = itemParamMapper.selectByExample(exampl);
        System.out.println("商品规格是:" + list);
        return new PageInfo<ItemParam>(list);
    }

    
}
