/*
 * @(#)PropertieService.java V2.0 2016年5月3日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: PropertieService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年5月3日 下午4:05:20
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月3日】 创建文件   by qinxf
 */
package feng.xueqin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: PropertieService 
 * @author qinxf
 * @date 2016年5月3日 下午4:05:20
 * @version V2.0 
 *  
 */
@Service
public class PropertieService {

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
    
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
}
