/*
 * @(#)Item.java V2.0 2016年6月7日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: Item.java 
 * @Package feng.xueqin.web.pojo 
 * @author qinxf
 * @date 2016年6月7日 下午8:33:05
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月7日】 创建文件   by qinxf
 */
package feng.xueqin.web.pojo;

import org.apache.commons.lang3.StringUtils;


/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: Item 
 * @author qinxf
 * @date 2016年6月7日 下午8:33:05
 * @version V2.0 
 *  
 */
public class Item extends feng.xueqin.pojo.Item {

    public String[] getImages(){
        return StringUtils.split(super.getImage(),',');
    }
}
