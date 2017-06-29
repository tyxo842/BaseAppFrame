package tyxo.baseappframe.utils.db;

import android.content.Context;

/**
 * Created by LY on 2016/9/20 10: 23.
 * Mail      tyxo842@163.com
 * Describe : AlertDialog弹出框点确定按钮执行的操作接口，删除程序数据
 */
public interface IDialogAction {
    public void PositiveAction(Context context, String... filepath);
}
