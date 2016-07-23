/*
 * @(#)UserController.java V2.0 2016年4月25日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: UserController.java 
 * @Package feng.xueqin.controller 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年4月25日】 创建文件   by qinxf
 */
package feng.xueqin.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import feng.xueqin.web.service.IndexService;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: UserController 
 * @author qinxf
 * @date 2016年4月25日 下午1:19:16
 * @version V2.0 
 *  
 */
@Controller
public class IndexUIController {

    @Autowired
    private IndexService indexService;
    @RequestMapping(value = "index", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        //大广告位数据
        String indexAd1 = this.indexService.queryIndexAD1();
        mv.addObject("indexAD1", indexAd1);
        
        //右上角小广告
        String indexAd2 = this.indexService.queryIndexAD2();
        mv.addObject("indexAD2", indexAd2);

        return mv;
    }
    
}
