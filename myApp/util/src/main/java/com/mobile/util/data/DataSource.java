package com.mobile.util.data;

/**
 * 数据源，接收到服务端数据后做回调的
 */
public interface DataSource {
    /**
     * callback接口，客户端成功接收到数据后做回调
     */
    interface SuccessCallback<T> {
        void onSuccess(T t);
    }

    /**
     * callback接口，客户端接受数据失败后做回调，只需要返回一个error就可以
     */
    interface FailCallback {
        void onFail(int error);
    }

    /**
     * 总接口，两个都有
     * @param <T>
     */
    interface Callback<T> extends FailCallback, SuccessCallback<T>{

    }

}
