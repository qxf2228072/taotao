/*
 * @(#)Function.java V2.0 2016年6月7日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: Function.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年6月7日 下午2:50:51
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月7日】 创建文件   by qinxf
 */
package feng.xueqin.common.service;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: Function 
 * @author qinxf
 * @date 2016年6月7日 下午2:50:51
 * @version V2.0 
 *  
 */
public interface Function<T,E> {

    public T callBack(E e);
}
