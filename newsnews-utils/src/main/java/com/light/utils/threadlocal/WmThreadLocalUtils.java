package com.light.utils.threadlocal;

import com.light.model.wemedia.pojos.WmUser;

/**
 * 自媒体线程工具类
 * ThreadLocal : 线程局部变量 , 可以存储数据 ,
 *      在一个线程中调用方法保存的数据 , 只有在该线程中能够获取
 * @author houhai
 */
public class WmThreadLocalUtils {

    private final static ThreadLocal<WmUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(WmUser user){
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static WmUser getUser(){
        return userThreadLocal.get();
    }

}
