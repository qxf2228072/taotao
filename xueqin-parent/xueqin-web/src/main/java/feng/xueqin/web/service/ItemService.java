/*
 * @(#)ItemService.java V2.0 2016年6月7日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemService.java 
 * @Package feng.xueqin.web.controller 
 * @author qinxf
 * @date 2016年6月7日 下午8:29:59
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月7日】 创建文件   by qinxf
 */
package feng.xueqin.web.service;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import feng.xueqin.common.service.ApiService;
import feng.xueqin.common.service.RedisService;
import feng.xueqin.pojo.ItemDesc;
import feng.xueqin.web.pojo.Item;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemService 
 * @author qinxf
 * @date 2016年6月7日 下午8:29:59
 * @version V2.0 
 *  
 */
@Service
public class ItemService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_MANAGE_URL}") 
    private String TAOTAO_MANAGE_URL;
    
    
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final String ITEMCACHENAME = "item_detail_cache_";

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: getItemDetail 
     * @date 2016年6月7日 下午8:32:14
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param itemId
     */
    
    public Item getItemDetail(Long itemId) {
       String key = ITEMCACHENAME+itemId;
       String cadata = redisService.get(key);
       if (StringUtils.isNotEmpty(cadata)) {
           try {
            return mapper.readValue(cadata, Item.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       }
        String url = TAOTAO_MANAGE_URL +"/rest/item/"+itemId;
        try {
            String jsonData = apiService.doGet(url);
            if (null != jsonData) {
                try {
                    redisService.set(key, jsonData, 60 * 60 *24);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return mapper.readValue(jsonData, Item.class);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;   
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: getItemDesc 
     * @date 2016年6月7日 下午8:49:39
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param itemId
     * @return
     */
    
    public ItemDesc getItemDesc(Long itemId) {
        String url = TAOTAO_MANAGE_URL +"/rest/item/desc/"+itemId;
        try {
            String jsonData = apiService.doGet(url);
            if (null != jsonData) {
                return mapper.readValue(jsonData, ItemDesc.class);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
        
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryParam 
     * @date 2016年6月7日 下午8:53:11
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param itemId
     * @return
     */
    
    public String queryParam(Long itemId) {
        String url = TAOTAO_MANAGE_URL +"/rest/item/param/item/"+itemId;
        try {
            String jsonData = apiService.doGet(url);
            JsonNode jsoNode = mapper.readTree(jsonData);
            
            String cc = jsoNode.get("paramData").asText();
            ArrayNode paramData = (ArrayNode) mapper.readTree(cc);
           StringBuilder  sb = new StringBuilder();
           sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
           for (JsonNode param : paramData) {
               sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText()
                       + "</th></tr>");
               ArrayNode params = (ArrayNode) param.get("params");
               for (JsonNode p : params) {
                   sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>"
                           + p.get("v").asText() + "</td></tr>");
               }
           }
           sb.append("</tbody></table>");
           return sb.toString();
       } catch (Exception e) {
           e.printStackTrace();
       }
        return null;
    }
    
    
}
