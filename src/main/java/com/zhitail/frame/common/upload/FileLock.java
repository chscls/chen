/**  
* @Project: xbframe
* @Title: FileLock.java
* @Package com.jeecms.common.upload
* @Description: TODO
* @author Administrator
* @date 2016年7月12日 上午12:40:23
* @Copyright: 2016
* @version V3.0  
*/
package com.zhitail.frame.common.upload;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件锁定
 * @ClassName FileLock
 * @Description TODO
 * @author Administrator
 * @date 2016年7月12日
 */
public class FileLock {

	private static Map<String, Lock> LOCKS = new HashMap<String, Lock>();

    public static synchronized Lock getLock(String key){
        if(LOCKS.containsKey(key)){
            return LOCKS.get(key);
        }else{
            Lock one = new ReentrantLock();
            LOCKS.put(key, one);
            return one;
        }
    }

    public static synchronized void removeLock(String key){
        LOCKS.remove(key);
    }
}
