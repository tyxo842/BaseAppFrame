package tyxo.baseappframe.utils.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class CallLogUtil {


	/**
	 * 删除通话记录
	 * [电话挂断以后得隔一定的时间才产生电话记录]
	 */
	public static void delete(Context context, final String number){
		// 获取内容解析者
		final ContentResolver resolver = context.getContentResolver();
		//获取正确的地址
		final Uri uri = Uri.parse("content://call_log/calls");
		// 创建 内容观察者
		ContentObserver observer = new ContentObserver(new Handler()) {
			// 旧版本 2.3
			// 接收到修改信号
			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				
				Log.i("tyxo", "删除 通话记录....onChange(boolean selfChange)");
				resolver.unregisterContentObserver(this);
				// delete from calls where number='10010';
				// 请求provider
				resolver.delete(uri, "number=?", new String []{number});
				
			}
			
			// 接收到修改信号
			// 高版本 4.3
			@Override
			public void onChange(boolean selfChange, Uri uri) {
//				super.onChange(selfChange, uri);
				
				Log.i("tyxo", "删除 通话记录....onChange(boolean selfChange, Uri uri)");
				resolver.unregisterContentObserver(this);
				// delete from calls where number='10010';
				// 请求provider
				resolver.delete(uri, "number=?", new String[]{number});
				
			}
		};
		
		// 接收信号 的对象 注册上
		// "content://call_log/calls"
		// "content://call_log/calls/1"子路径
		// resolver.registerContentObserver(地址, 子路径是否提示, 内容观察者);
		resolver.registerContentObserver(uri, false, observer);
	}
}















