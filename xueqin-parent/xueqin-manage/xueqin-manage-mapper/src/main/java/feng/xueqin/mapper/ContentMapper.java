/*
 * @(#)ContentMapper.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ContentMapper.java 
 * @Package feng.xueqin.mapper 
 * @author qinxf
 * @date 2016年5月10日 下午8:00:13
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;

import feng.xueqin.pojo.Content;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ContentMapper 
 * @author qinxf
 * @date 2016年5月10日 下午8:00:13
 * @version V2.0 
 *  
 */
public interface ContentMapper extends Mapper<Content>{

   List<Content> queryList();
}
