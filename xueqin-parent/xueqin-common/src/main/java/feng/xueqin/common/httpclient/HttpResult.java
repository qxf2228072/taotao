/*
 * @(#)HttpResult.java V2.0 2016年5月12日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: HttpResult.java 
 * @Package feng.xueqin.web.httpclient 
 * @author qinxf
 * @date 2016年5月12日 下午1:45:50
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年5月12日】 创建文件   by qinxf
 */
package feng.xueqin.common.httpclient;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: HttpResult 
 * @author qinxf
 * @date 2016年5月12日 下午1:45:50
 * @version V2.0 
 *  
 */
public class HttpResult {

    private Integer code;
    private String data;
    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }
    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
    /** 
     * <p>Title: </p> 
     * <p>Description: </p> 
     * @param code
     * @param data 
     */
    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }
    public HttpResult(){}
}
