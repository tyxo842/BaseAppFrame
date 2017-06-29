package tyxo.baseappframe.utils.bitmap;

import android.content.Context;

//为防止多次创建,导致内存溢出,通过帮助类,将BitmapUtils设置成单例模式;
public class BitmapHelp {

	private static BitmapUtil instance;

	private BitmapHelp() {

	}

	public static BitmapUtil getBitmapUtils(Context context) {
		if (instance == null) {
			instance = new BitmapUtil(context);
		}
		return instance;
	}
}
