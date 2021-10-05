package com.example.red_pakege.base;

public interface IBasePresenter<T extends IBaseView> {

    /**
     * 绑定view到presenter
     * @param view
     *
     */
    void onAttachView(T view);
    /**
     * 解绑view
     */
    void onDetachView();
}

