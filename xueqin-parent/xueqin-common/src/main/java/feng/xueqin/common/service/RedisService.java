/*
 * @(#)RedisService.java V2.0 2016年6月7日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: RedisService.java 
 * @Package feng.xueqin.service 
 * @author qinxf
 * @date 2016年6月7日 下午2:23:58
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月7日】 创建文件   by qinxf
 */
package feng.xueqin.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: RedisService 
 * @author qinxf
 * @date 2016年6月7日 下午2:23:58
 * @version V2.0 
 *  
 */
@Service
public class RedisService {

    @Autowired(required=false)
    private ShardedJedisPool shardedJedisPool;
    
    private <T> T excute(Function<T, ShardedJedis> fun) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
       return fun.callBack(jedis);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(null != jedis){
                jedis.close();
            }
        }  
        return null;
    }
     
    
    /**
     * 
     * 方法描述...
     * 业务逻辑说明  ：TODO(总结性的归纳方法业务逻辑) 
     * 1、处理…………<br/>
     * 2、处理…………<br/>
     * 3、处理…………<br/>
     * @Title: set 
     * @date 2016年6月7日 下午3:05:02
     * @author qinxf
     * @modifier 
     * @modifydate 
     * @param key
     * @param value
     * @return ok
     */
    public String set(final String key,final String value){
 
        return this.excute(new Function<String, ShardedJedis>() {

            @Override
            public String callBack(ShardedJedis e) {
               
                return e.set(key, value);
            }
            
        });
        
    }
    //get
    public String get(final String key){
        
        return this.excute(new Function<String, ShardedJedis>() {

            @Override
            public String callBack(ShardedJedis e) {
               
                return e.get(key);
            }            
        }); 
    }
    //sel
    public Long del(final String key){
        
        return this.excute(new Function<Long, ShardedJedis>() {

            @Override
            public Long callBack(ShardedJedis e) {
               
                return e.del(key);
            }            
        }); 
    }
    //set
   public String set(final String key,final String value,final Integer seconds){
        
        return this.excute(new Function<String, ShardedJedis>() {

            @Override
            public String callBack(ShardedJedis e) {
                String res = e.set(key, value);
                e.expire(key, seconds);
                return  res;
            }            
        }); 
    }
   
   /**
    * 设置生存时间，单位为秒
    * 
    * @param key
    * @param seconds
    * @return
    */
   public Long expire(final String key, final Integer seconds) {
       return this.excute(new Function<Long, ShardedJedis>() {
        @Override
        public Long callBack(ShardedJedis e) {
            // TODO Auto-generated method stub
            return e.expire(key, seconds);
        }
       });
   }

   
}
