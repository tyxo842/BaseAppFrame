package tyxo.baseappframe.utils;

import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import tyxo.baseappframe.utils.dialog.CustomDialog;

/**
 * Author : {LiYang}
 * qq     : 1577441454
 * Date   : 2016/6/1  15:11
 * Description: 跟editText相关手机输入法的收起
 */
public class EditTextUtil {

    // 增加非条码备货-时间选择处,editText是要收起输入法的位置
    /*public static void clearFocuse(Context context, AutoClearEditText editText) {
//        et_o_add_produce_num.clearFocus();
//        et_o_add_sterilisation_num.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//有则隐,无则显
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }*/
    public static void clearFocuse(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    // 普通备货-数量选择
    public static void clearSoftKB(Context context, CustomDialog dialog, EditText editText) {
        editText.findFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0); //显示软键盘
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //显示软键盘
    }
}
