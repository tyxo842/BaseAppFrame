package tyxo.baseappframe.utils.phone;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Method;

//import com.android.internal.telephony.ITelephony;

public class CallUtils {

	/**
	 * 挂断电话
	 */
	
	public static void endCall() throws Exception{
		// private ITelephony getITelephony() {
		// 		return ITelephony.Stub.asInterface(
		// 		ServiceManager.getService(Context.TELEPHONY_SERVICE));
		// }
		// 反射获取隐藏类
		Class<?> clz = Class.forName("android.os.ServiceManager");
		//获取方法
		Method getServiceMethod = null;
		Method [] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if ("getService".equals(method.getName())) {
				getServiceMethod = method;
				break;
			}
		}
		
		// getService.invoke(实例, 参数)-->调用
		IBinder iBinder = (IBinder) getServiceMethod.invoke(null, Context.TELEPHONY_SERVICE);
		/**需要调 手机硬件 aidl 暂未写 */
		/*ITelephony it = ITelephony.Stub.asInterface(iBinder);
		//挂断
		it.endCall();*/
	}
}
