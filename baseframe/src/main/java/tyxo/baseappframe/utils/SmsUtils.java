package tyxo.baseappframe.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import tyxo.baseappframe.utils.md5.SecurityUtils;

public class SmsUtils {

    /**
     * 获取短信的数据返回集合
     */
    public static List<SmsInfo> findAll(Context context) {
        List<SmsInfo> list = new ArrayList<SmsInfo>();
        // a.权限
        // b.找到uri
        Uri uri = Uri.parse("content://sms");
        // c.获取ContentResolver
        ContentResolver resolver = context.getContentResolver();
        // d.访问返回游标
        // select address,date,type,body from sms;
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // e.转换成集合
        while (cursor.moveToNext()) {
            SmsInfo bean = new SmsInfo();
            bean.address = cursor.getString(cursor.getColumnIndex("address"));
            bean.date = cursor.getLong(cursor.getColumnIndex("date"));
            bean.type = cursor.getString(cursor.getColumnIndex("type"));
            bean.body = cursor.getString(cursor.getColumnIndex("body"));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    /**
     * 备份
     */
    public static void backup(Context context, OnProgressChangedListener listener) {
        int count = 0;
        int progress = 0;
        try {
            // a.创建文件sd卡 sms.xml WRITE_EXTERNAL_STORAGE
            // mnt/sdcard/sms.xml
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(dir + "/sms.xml");
            file.createNewFile();
            String namespace = null;

            // b.获取序列化器
            XmlSerializer writer = Xml.newSerializer();
            FileOutputStream fos = new FileOutputStream(file);
            // 设置工具
            writer.setOutput(fos, "utf-8");
            // 设置第一行
            // <?xml version="1.0" encoding="utf-8"?>
            writer.startDocument("utf-8", true);
            // c.按照标签格式写入标签到文件
            List<SmsInfo> list = findAll(context);
            count = list.size();
            if (listener != null) {
                listener.onStart(count);
            }

            //格式
            // <sms-list count="10">
            // <sms>
            // <address></adddress>\
            // ...
            // <sms>
            // </sms-list>

            writer.startTag(namespace, "sms-list");
            writer.attribute(namespace, "count", list.size() + "");

            for (SmsInfo info : list) {
                Log.i("ly", "号码:" + info.address + " 内容:" + info.body);
                writer.startTag(namespace, "sms");
                writer.startTag(namespace, "address");
                writer.text(SecurityUtils.encryt(info.body));
                writer.endTag(namespace, "address");
                writer.startTag(namespace, "body");
                writer.text(SecurityUtils.encryt(info.body));
                writer.endTag(namespace, "body");
                writer.startTag(namespace, "type");
                writer.text(info.type);
                writer.endTag(namespace, "type");
                writer.startTag(namespace, "date");
                writer.text(info.date + "");
                writer.endTag(namespace, "date");
                writer.endTag(namespace, "sms");
                Thread.sleep(100);
                ++progress;

                if (listener != null) {
                    listener.onProgressUpdate(progress);
                }
            }
            writer.endTag(namespace, "sms-list");
            writer.endDocument();
            fos.flush();
            fos.close();

            if (listener != null) {
                listener.onFinish(progress);//抽取接口处,调用接口方法,具体实现外传便于修改维护
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //1.具体的实现 Alt_shift_M
//	a.命名 On..Listener b.方法 on.. c.添加 setOn... d.调用这个实例
    //2.接口是最抽象   比抽象类
    public static interface OnProgressChangedListener {
        void onFinish(int progress);

        void onProgressUpdate(int progress);

        void onStart(int max);
    }

    //方法抽取出来之后,
        /*	private static void onFinish(int progress) {
            Message msgEnd = new Message();
			msgEnd.what = 3;
			msgEnd.obj = progress;
			handler.sendMessage(msgEnd);
		}
		private static void onProgressUpdate(int progress) {
			Message msgupdate = new Message();
			msgupdate.what = 2;
			msgupdate.obj = progress;
			handler.sendMessage(msgupdate);
		}
		private static void onStart(int count) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = count;
			handler.sendMessage(msg);
		}*/

    public static class SmsInfo {
        public String address;
        public String body;
        public String type;
        public long date;
    }
}

