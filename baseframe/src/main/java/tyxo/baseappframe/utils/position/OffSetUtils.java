package tyxo.baseappframe.utils.position;

import android.content.Context;

import java.io.InputStream;

public class OffSetUtils {

    /**
     * 地球坐标转火星坐标
     * latitude:纬度
     * lon:经度
     */
    public static ModifyOffset.PointDouble getChinaLocation(Context context, double lat, double lon) {

        ModifyOffset.PointDouble p = new ModifyOffset.PointDouble(lon, lat);//x=经度;y=维度
        // 载入数据库
        try {
            InputStream is = context.getAssets().open("axisoffset.dat");//asset:资产
            //获取工具
            ModifyOffset mo = ModifyOffset.getInstance(is);
            p = mo.s2c(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
