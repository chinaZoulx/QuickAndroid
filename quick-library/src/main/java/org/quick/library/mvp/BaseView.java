package org.quick.library.mvp;

/**
 * Created by work on 2017/6/2.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public interface BaseView<T> {

    void setPresenter(T presenter);
    void showToast(String content);
    void showDialog(String content);
    void hideDialog();
}
