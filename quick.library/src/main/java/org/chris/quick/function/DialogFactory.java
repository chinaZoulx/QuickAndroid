package org.chris.quick.function;

/**
 * Created by work on 2017/3/21.
 *
 * @author chris zou
 * @mail chrisSpringSmell@gmail.com
 */

public class DialogFactory {

    private static class DialogHolder {
        private static final DialogFactory INSTANCE = new DialogFactory();
    }

    public static DialogFactory getInstance() {
        return DialogHolder.INSTANCE;
    }

    public enum DialogType {
        IS_OK(0),
        LOADING(1);

        DialogType(int value) {
            this.value = value;
        }

        public int value;
    }
//
//    public IDialog getDialog(DialogType dialogType) {
//        switch (dialogType) {
//            case IS_OK:
//                return new IsOkDialog();
//            case LOADING:
//                return new LoadingDialog();
//            default:
//        }
//        return null;
//    }
}
