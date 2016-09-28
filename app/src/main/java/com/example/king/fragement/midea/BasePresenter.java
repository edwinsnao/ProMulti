package com.example.king.fragement.midea;

/**
 * Created by Kings on 2016/4/10.
 */
public abstract class BasePresenter<T extends MVP> {
    protected T mView;

    public void attach(T view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }
}
