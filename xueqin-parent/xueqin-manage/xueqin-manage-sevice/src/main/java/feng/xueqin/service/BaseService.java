/*
 * @(#)BaseService.java V2.0 2016年4月28日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: BaseService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年4月28日 下午2:14:40
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年4月28日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import feng.xueqin.pojo.BasePojo;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: BaseService 
 * @author qinxf
 * @date 2016年4月28日 下午2:14:40
 * @version V2.0 
 *  1、  queryById
2、      queryAll
3、      queryOne
4、      queryListByWhere
5、      queryPageListByWhere
6、      save
7、      update
8、      deleteById
9、      deleteByIds
10、     deleteByWhere

 */
@Service
public abstract class BaseService<T extends BasePojo>{

    @Autowired
    private Mapper<T> mapper;
    
    /**
     * 根据id查询数据
     * 
     * @param id
     * @return
     */
    public T queryById(Long id){
        return mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll(){
        return mapper.select(null);
    }
    
    /**
     * 根据条件查询数据
     * 
     * @param id
     * @return
     */
    public T queryOne(T t){
        return mapper.selectOne(t);
    }
    
    public List<T>  queryListByWhere(T t){
        return mapper.select(t);
    }
    
    public PageInfo<T> queryPageListByWhere(Integer pageNum,Integer pageSize,T t){
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.queryListByWhere(t);
        return new PageInfo<T>(list);
    }
    
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insert(t);
    }
    public Integer saveSelective(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return mapper.insertSelective(t);
    }
    /**
     * 修改数据，返回成功的条数
     * 
     * @param record
     * @return
     */
    public Integer update(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     * 
     * @param record
     * @return
     */
    public Integer updateSelective(T record) {
        record.setUpdated(new Date());
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除数据
     * 
     * @param id
     * @return
     */
    public Integer deleteById(Long id) {
        
        return mapper.deleteByPrimaryKey(id);
    }

    public Integer deleteByIds(Class<T> clazz,String property,List<Object> values){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
       return mapper.deleteByExample(example);
    }
    
    /**
     * 根据条件做删除
     * 
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record) {
        return mapper.delete(record);
    }

}
