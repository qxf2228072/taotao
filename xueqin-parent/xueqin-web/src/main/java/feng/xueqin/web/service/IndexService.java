/*
 * @(#)IndexService.java V2.0 2016年5月12日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: IndexService.java 
 * @Package feng.xueqin.web.service 
 * @author qinxf
 * @date 2016年5月12日 下午4:40:39
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月12日】 创建文件   by qinxf
 */
package feng.xueqin.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import feng.xueqin.common.service.ApiService;
import feng.xueqin.common.utils.EasyUIResult;
import feng.xueqin.pojo.Content;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: IndexService 
 * @author qinxf
 * @date 2016年5月12日 下午4:40:39
 * @version V2.0 
 *  
 */
@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    public String queryIndexAD1(){
        String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
        try {
            String json = apiService.doGet(url); 
            if(StringUtils.isEmpty(json)){
                return null;
            }
            EasyUIResult easyUIResult  = EasyUIResult.formatToList(json, Content.class);
            List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();

            for(Content content : (List<Content>) easyUIResult.getRows()){
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("srcB", content.getPic());
                map.put("height", 240);
                map.put("alt", content.getTitle());
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl());
                map.put("heightB", 240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public String queryIndexAD2(){
        String url = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
        try {
            String json = apiService.doGet(url); 
            if(StringUtils.isEmpty(json)){
                return null;
            }
            // 解析json，生成前端所需要的json数据
            JsonNode jsonNode = MAPPER.readTree(json);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("width", 310);
                map.put("height", 70);
                map.put("src", row.get("pic").asText());
                map.put("href", row.get("url").asText());
                map.put("alt", row.get("title").asText());
                map.put("widthB", 210);
                map.put("heightB", 70);
                map.put("srcB", row.get("pic").asText());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
