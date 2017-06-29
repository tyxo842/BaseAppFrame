package tyxo.baseappframe.utils.position;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.List;

import tyxo.baseappframe.utils.SPUtil;
import tyxo.baseappframe.utils.log.TLog;

public class GpsUtils {
	
	static class MyLocationListener implements LocationListener{
		
		private Context context;
		
		public MyLocationListener(Context context){
			super();
			this.context = context;
		}

		// 条件 坐标改变
		@Override
		public void onLocationChanged(Location location) {
			// Location：坐标对象
			//先获取地球坐标
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			TLog.i("地球坐标 lat:" + lat + "lon:" + lon);
			
			//工具类转化加密成火星坐标;即为纠偏操作
			ModifyOffset.PointDouble p = OffSetUtils.getChinaLocation(context, lat, lon);
			TLog.i( "火星坐标  lat:" + p.x + "lon:" + p.y);
			
			String safeNumber = SPUtil.getString(context, "safe_number", "5554");
			// SmsManager.getDefault().sendTextMessage(接收号码, 本机号码,文本内容,null, null);
			SmsManager.getDefault().sendTextMessage(safeNumber, null, "gps lat:" + p.x + "lon:" + p.y,null,null);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
		
	};//???此处有个;,去掉也不报错??不明白
	
	private static MyLocationListener listener_gps = null;
	private static MyLocationListener listener_network = null;
	private static LocationManager lm = null;
	
	public static void startLocation(Context context){
		// ①　配置权限
		// ②　创建LocationManager
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		List<String> providers = lm.getAllProviders();
		for (String p : providers) {
			TLog.i( p);
		}
		// ③　添加监听器
		Criteria c = new Criteria();
		// Criteria  
		//查询条件对象
		// 1.付费
		c.setCostAllowed(true);
		// 2.电量
		c.setPowerRequirement(Criteria.POWER_HIGH);
		// 3.精度
		c.setAccuracy(Criteria.ACCURACY_FINE);// gps
		// provider:定位方式
		String bestProvider = lm.getBestProvider(c, true);
		TLog.i("bestProvider :" + bestProvider);
		// ListView OnItemClickListener
		// LocationManager LocationListener

		// ④　模拟器（手工发送坐标 因为没有定位芯片）
		listener_gps = new MyLocationListener(context);
		listener_network = new MyLocationListener(context);
		// lm.requestLocationUpdates(定位方式, 每隔n毫秒, 移动多少米, 监听器 获取坐标);
		// lm.requestLocationUpdates(bestProvider, 1000, 0, listener);
//		lm.requestLocationUpdates(查询坐标的类型(gps/network), 间隔刷新时间, 便宜位置(0为最精确), MyLocationListener对象)
		lm.requestLocationUpdates("gps", 1000, 0, listener_gps);
		lm.requestLocationUpdates("network", 1000, 0, listener_network);
	}
	
	//停止刷新位置
	public static void stopLocation(Context context){
		lm.removeUpdates(listener_gps);
		lm.removeUpdates(listener_network);
	}
}
