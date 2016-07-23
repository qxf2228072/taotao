/*
 * @(#)ItemService.java V2.0 2016年5月4日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: ItemService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年5月4日 下午8:43:53
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月4日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import feng.xueqin.common.service.ApiService;
import feng.xueqin.mapper.ItemMapper;
import feng.xueqin.pojo.Item;
import feng.xueqin.pojo.ItemDesc;
import feng.xueqin.pojo.ItemParamItem;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: ItemService 
 * @author qinxf
 * @date 2016年5月4日 下午8:43:53
 * @version V2.0 
 *  
 */
@Service
public class ItemService extends BaseService<Item>{

    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_WEB_URL}")
    private String TAOTAO_WEB_URL;
    
    
    public void saveItem(Item item,String desc,String itemParams){
        item.setStatus(1);
        item.setId(null);
        super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
        //商品规格
        // 保存规格参数数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        this.itemParamItemService.save(itemParamItem);
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: queryPageList 
     * @date 2016年5月5日 下午1:47:04
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param page
     * @param rows
     * @return
     */
    
    public PageInfo<Item> queryPageList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example exampl = new Example(Item.class);
        exampl.setOrderByClause("updated DESC");
        List<Item> list = itemMapper.selectByExample(exampl);
        return new PageInfo<Item>(list);
    }

    /**
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: updateItem 
     * @date 2016年5月6日 上午9:33:24
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param item
     * @param desc
     */
    
    public void updateItem(Item item, String desc) {
     // 强制设置不能修改的字段为null
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.updateSelective(itemDesc);
        
        //通知后台商品更新了,删除缓存
        String url = TAOTAO_WEB_URL + "/item/cache/"+item.getId()+".html";
        try {
            apiService.doGet(url);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
