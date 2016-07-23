/*
 * @(#)ItemCatData.java V2.0 2016年5月10日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCatData.java 
 * @Package feng.xueqin.common.utils 
 * @author qinxf
 * @date 2016年5月10日 上午10:22:22
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月10日】 创建文件   by qinxf
 */
package feng.xueqin.common.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCatData 
 * @author qinxf
 * @date 2016年5月10日 上午10:22:22
 * @version V2.0 
 *  
 */
public class ItemCatData {

    @JsonProperty(value = "u")
    private String url;
    @JsonProperty(value = "n")
    private String name;
    @JsonProperty(value = "i")
    private List<?> items;
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the items
     */
    public List<?> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<?> items) {
        this.items = items;
    }
    
    
}
