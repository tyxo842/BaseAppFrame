package tyxo.baseappframe.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class WindowUtils {

    /**
     * 显示视图在顶级布局
     *
     * @param context
     * @param layoutId
     * @param x
     * @param y
     * @return
     */
    public static View showViewInWindow(final Context context, int layoutId,
                                        int x, int y) {
        // 重要对象 WindowManager
        final WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        // 创建视图
        final View view = View.inflate(context, layoutId, null);
        // 宽高 背景 位置。。。。
        // layout_width
        // layout_height
        // format背景
        // type 级别 叠加顺序
        // gravity
        // x 绝对布局
        // y
        // focusable
        LayoutParams params = new LayoutParams();
        // 参考吐司
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;// 透明
        params.gravity = Gravity.LEFT | Gravity.TOP;// 坐标 左上角
        params.x = x;
        params.y = y;
        // params.type=WindowManager.LayoutParams.TYPE_TOAST;
        params.type = LayoutParams.TYPE_SYSTEM_ALERT;
        // 其它
        params.flags = LayoutParams.FLAG_NOT_FOCUSABLE// 不能聚焦
                // | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不支持touch事件
                | LayoutParams.FLAG_KEEP_SCREEN_ON;// 亮屏

        OnTouchListener listener = new OnTouchListener() {
            double startX;
            double startY;

            // 满足 操作控件的时候
            // MotionEvent动作与坐标的封装对象
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 按下
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:// 移动

                        double newX = event.getRawX();
                        double newY = event.getRawY();

                        double offX = newX - startX;
                        double offY = newY - startY;

                        // 布局参数对象LayoutParams x y
                        LayoutParams p = (LayoutParams) view.getLayoutParams();
                        p.x += offX;
                        p.y += offY;
                        // 重新显示 View

                        // 防止越界
                        if (p.x < 0) {
                            p.x = 0;
                        }
                        if (p.y < 0) {
                            p.y = 0;
                        }

                        if (p.x > (wm.getDefaultDisplay().getWidth() - view.getWidth())) {
                            p.x = wm.getDefaultDisplay().getWidth() - view.getWidth();
                        }
                        if (p.y > (wm.getDefaultDisplay().getHeight() - view.getHeight())) {
                            p.y = wm.getDefaultDisplay().getHeight() - view.getHeight();
                        }

                        wm.updateViewLayout(view, p);
                        startX = event.getRawX();
                        startY = event.getRawY();
                        //这么写(最后设置startX...)在通话界面设置后,总会有偏差
//					SharePreUtil.putInteger(context, "xposition", (int) startX); 
//					SharePreUtil.putInteger(context, "yposition", (int) startY);

                        SPUtil.putInteger(context, "xposition", p.x);
                        SPUtil.putInteger(context, "yposition", p.y);
                        break;
                    case MotionEvent.ACTION_UP:// 离开
                        startX = event.getRawX();
                        startY = event.getRawY();

//					WindowManager.LayoutParams params2 = new WindowManager.LayoutParams();
//					SharePreUtil.putInteger(context, "xposition", params2.x);
//					SharePreUtil.putInteger(context, "yposition", params2.y);
                        break;
                }
                return true;// 消费事件
            }
        };
        view.setOnTouchListener(listener);
        // 添加
        wm.addView(view, params);
        return view;
    }

    /**
     * 删除 视图在顶级布局
     *
     * @param context
     * @param view
     */
    public static void removeViewInWindow(Context context, View view) {
        // 重要对象 WindowManager
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(view);
    }
}
