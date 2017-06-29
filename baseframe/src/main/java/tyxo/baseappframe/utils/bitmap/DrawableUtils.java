package tyxo.baseappframe.utils.bitmap;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {

	/**
	 * 生成一个Drawable，对应的是xml的shape标签
	 */
	public static GradientDrawable generateDrawable(int rgb,float cornerRadius){
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);//设置矩形，其实默认就是矩形
		drawable.setCornerRadius(cornerRadius);//设置圆角的半径
		drawable.setColor(rgb);//设置颜色
		
		return drawable;
	}
	
	/**
	 * 动态的生成Selector
	 */
	public static StateListDrawable generateSelector (Drawable pressed, Drawable normal){
		
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//设置按下的图片
		drawable.addState(new int []{}, normal);
		
		return drawable;
	}
}

