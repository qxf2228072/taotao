/*
 * @(#)ItemCatService.java V2.0 2016年4月28日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemCatService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年4月28日 上午10:19:42
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年4月28日】 创建文件   by qinxf
 */
package feng.xueqin.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feng.xueqin.common.service.RedisService;
import feng.xueqin.common.utils.ItemCatData;
import feng.xueqin.common.utils.ItemCatResult;
import feng.xueqin.pojo.ItemCat;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemCatService 
 * @author qinxf
 * @date 2016年4月28日 上午10:19:42
 * @version V2.0 
 *  
 */
@Service
public class ItemCatService extends BaseService<ItemCat>{

    @Autowired
    private RedisService redisService;
    
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryTree 
     * @date 2016年5月10日 上午10:45:53
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @return
     */
    
    public ItemCatResult queryTree() {
        ItemCatResult itemCatResult = new ItemCatResult();
        //先从缓存中查找,没有再去数据库
        String key = "TAOTAO_MANAGE_ITEM_CAT_ALL";
        String cache = redisService.get(key);
        if(!StringUtils.isEmpty(cache)){
           try {
            return mapper.readValue(cache, ItemCatResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        List<ItemCat> itemCat = super.queryAll();
        Map<Long,List<ItemCat>> itemCatMap = new HashMap<Long,List<ItemCat>>();
        for (ItemCat itemCat1 : itemCat) {
            if(!itemCatMap.containsKey(itemCat1.getParentId())){
                itemCatMap.put(itemCat1.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat1.getParentId()).add(itemCat1);
        }
     // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat2 : itemCatList1) {
            ItemCatData catData = new ItemCatData();
            catData.setUrl("/products/" + itemCat2.getId() + ".html");
            catData.setName("<a href='" + catData.getUrl() + "'>" + itemCat2.getName() + "</a>");
            itemCatResult.getItems().add(catData);
            if(!itemCat2.getIsParent()){
                continue;
            }
            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat2.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            catData.setItems(itemCatData2);
            for (ItemCat itemCat3 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat3.getName());
                id2.setUrl("/products/" + itemCat3.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat3.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat4 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat4.getId() + ".html|" + itemCat4.getName());
                    }
                }
            }
            if (itemCatResult.getItems().size() >= 14) {
                break;
            }
            //存入缓存
            try {
                redisService.set(key, mapper.writeValueAsString(itemCatResult), 60*60*24*30*3);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return itemCatResult;
    }

}
