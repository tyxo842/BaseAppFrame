package tyxo.baseappframe.utils.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import tyxo.baseappframe.utils.Utility;
import tyxo.baseappframe.utils.db.DataCleanManager;

/**
 * 作者: ${LY} on 2016/1/611:44.
 * 注释: 自定义dialog
 */
public class DialogUtil {

    private static MyDialog mDialog;
    private static AlertDialog mAlertDialog;

    /**
     * 缓存清理
     */
    public static void showDialogClear(final Context context, String title, int icon,
                                       CharSequence message, final DataCleanManager dataCleanManager,
                                       int layoutId, int msgId, int contentId, int... ids) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setMessage(message + "\n");
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dataCleanManager.PositiveAction(context, "");
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDialog != null) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                    }
                });

        if (mDialog == null) {
            mDialog = builder.create(layoutId, msgId, contentId, ids);
            mDialog.setCanceledOnTouchOutside(false);//点击空白不消失;否则(删掉此行),再点击不出现dialog
            mDialog.show();
        }

    }

    /**
     * 获取一个耗时等待对话框
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    /**
     * 显示网络设置
     */
    public static void showTips(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("没有可用网络");
        builder.setMessage("当前网络不可用，是否设置网络？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 如果没有网络连接，则进入网络设置界面
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                    mAlertDialog = null;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                    mAlertDialog = null;
                }
            }
        });
        if (mAlertDialog == null) {
            mAlertDialog = builder.create();
            mAlertDialog.setCanceledOnTouchOutside(false);//点击空白不消失;否则(删掉此行),再点击不出现dialog
            mAlertDialog.show();
        }
    }

    /**
     * 显示网络设置
     */
    private static MyDialog mNetDialog;

    public static void showTipsMy(final Context context, int layoutId, int msgId, int contentId, int... ids) {
        MyDialog.Builder builder = new MyDialog.Builder(context);

        builder.setTitle("没有可用网络");
        builder.setMessage("当前网络不可用，是否设置网络？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 如果没有网络连接，则进入网络设置界面
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                if (mNetDialog != null) {
                    mNetDialog.dismiss();
                    mNetDialog = null;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mNetDialog != null) {
                    mNetDialog.dismiss();
                    mNetDialog = null;
                }
            }
        });
        if (mNetDialog == null) {
            mNetDialog = builder.create(layoutId, msgId, contentId, ids);
            mNetDialog.setCanceledOnTouchOutside(false);//点击空白不消失;否则(删掉此行),再点击不出现dialog
            mNetDialog.show();
        }
    }

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
            }
        });
        builder.create().show();
    }

    public static void showTipsDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("");
        builder.setPositiveButton("ok", onClickListener);
        builder.create().show();
    }

    /**
     * 查看发货明细 删除条目
     */
    private static MyDialog mDeleteDialog;

    public static void showDialogDeleteDeliveryDetail(final Context context, String title, int icon,
                                                      CharSequence message, final Handler handler, final String stockUpId,
                                                      int layoutId, int msgId, int contentId, int... ids) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setMessage(message + "\n");
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Message msg = new Message();
                msg.what = Utility.STOCKUP_DELETE_ID;
                msg.obj = stockUpId;
                handler.sendMessage(msg);
                if (mDeleteDialog != null) {
                    mDeleteDialog.dismiss();
                    mDeleteDialog = null;
                }
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDeleteDialog != null) {
                            mDeleteDialog.dismiss();
                            mDeleteDialog = null;
                        }
                    }
                });

        if (mDeleteDialog == null) {
            mDeleteDialog = builder.create(layoutId, msgId, contentId, ids);
            mDeleteDialog.setCanceledOnTouchOutside(false);//点击空白不消失;否则(删掉此行),再点击不出现dialog
            mDeleteDialog.show();
        }
    }

    /**
     * 订单查询 Detail 非条码备货 长按删除
     */
    private static MyDialog mLongClickDialog;

    public static void showDialogDeleteMsg(final Context context, String title, int icon,
                                           CharSequence message, final Handler handler, final int position,
                                           int layoutId, int msgId, int contentId, int... ids) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setMessage(message + "\n");
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Message msg = new Message();
                msg.what = Utility.UNBARCODE_ITEM_DELETE;
                msg.arg1 = position;
                handler.sendMessage(msg);
//                clearDialog();
                if (mLongClickDialog != null) {
                    mLongClickDialog.dismiss();
                    mLongClickDialog = null;
                }
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        clearDialog();
                        if (mLongClickDialog != null) {
                            mLongClickDialog.dismiss();
                            mLongClickDialog = null;
                        }
                    }
                });

        if (mLongClickDialog == null) {
            mLongClickDialog = builder.create(layoutId, msgId, contentId, ids);
            mLongClickDialog.setCanceledOnTouchOutside(false);//点击空白不消失;否则(删掉此行),再点击不出现dialog
            mLongClickDialog.show();
        }
    }

    /*public static Dialog createUpperDialog(Activity context, String title, List<TextValue> list,
                                           View.OnClickListener dialogListtener, @Nullable AdapterView.OnItemClickListener listener) {
        final int checkPostion;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.barcodetype_chooser, null);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        tvTitle.setText(title);

        //为控件设置点击事件
        ((TextView) dialogView.findViewById(R.id.cancel_action)).setOnClickListener(dialogListtener);
        ((TextView) dialogView.findViewById(R.id.ok_action)).setOnClickListener(dialogListtener);

        final ListView listView = (ListView) dialogView.findViewById(R.id.listview);
        listView.setAdapter(new DialogListViewAdapter<TextValue>(list, context));
        listView.setOnItemClickListener(listener);

        final Dialog customDialog = new Dialog(context, R.style.custom_dialog);

        WindowManager.LayoutParams localLayoutParams = customDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.x = 0;
        localLayoutParams.y = 0;



        int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
        dialogView.setMinimumWidth(screenWidth);

        customDialog.onWindowAttributesChanged(localLayoutParams);
        customDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setContentView(dialogView);
        return customDialog;
    }*/
}
