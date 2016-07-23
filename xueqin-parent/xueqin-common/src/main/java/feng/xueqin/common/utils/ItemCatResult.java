/*
 * @(#)ItemCatResult.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCatResult.java 
 * @Package feng.xueqin.common.utils 
 * @author qinxf
 * @date 2016年5月10日 上午10:21:09
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCatResult 
 * @author qinxf
 * @date 2016年5月10日 上午10:21:09
 * @version V2.0 
 *  
 */
public class ItemCatResult {

    @JsonProperty(value = "data")
    private List<ItemCatData> items = new ArrayList<ItemCatData>();

    /**
     * @return the items
     */
    public List<ItemCatData> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ItemCatData> items) {
        this.items = items;
    }
    
    
}
