package com.navinfo.opentsp.dongfeng.common.result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * base result , it contain business code and business message, no http code, no http message
 *
 * @author zhangy
 * @date 2016-03-02
 * @modify
 * @copyright Navi Tsp
 */
public class BaseBusinessResult<T> {

    /**
     * business data
     */
    private T bdata;

    public BaseBusinessResult() {
    }

    public BaseBusinessResult(T bdata) {
        this.bdata = bdata;
    }

    public T getBdata() {
        return bdata;
    }

    public void setBdata(T bdata) {
        this.bdata = bdata;
    }

    /**
     * 填充业务操作状态信息
     *
     * @param e 枚举常量状态信息
     */
    public void fillBusinessStatus(Enum e) {
        Class ownerClass = e.getClass();
        Method method = null;
        Object code = null;
        Object message = null;
        try {
            method = ownerClass.getMethod("code");
            code = method.invoke(e);
            method = ownerClass.getMethod("message");
            message = method.invoke(e);
        } catch (NoSuchMethodException nsme) {
            return;
        } catch (InvocationTargetException ite) {
            return;
        } catch (IllegalAccessException iae) {
            return;
        }
    }

    @Override
    public String toString() {
        return "BaseBusinessResult{" + '\'' + ", bdata=" + bdata + '}';
    }

}
