package tyxo.baseappframe.utils.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import tyxo.baseappframe.utils.IOUtils;

/**
 * Created on 2015/12/28.
 */
public class ResponseHandleUtil {

    public static void HandleGankResponseFromHttp(Context context, String response, int currentImagePosition, int onceLoad, int type) throws ExecutionException, InterruptedException, JSONException {

        /*Realm realm = Realm.getInstance(context);
        realm.beginTransaction();//开启事务

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        for(int i=currentImagePosition;currentImagePosition<jsonArray.length() && i<currentImagePosition+onceLoad;i++){
            jsonObject=jsonArray.getJSONObject(i);
            String url = jsonObject.getString("url");

            ImageFuli imageFuli =new ImageFuli(url);
//            Bitmap bitmap = Glide.with(context).load(imageFuli.getUrl()).asBitmap().thumbnail(0.1f)
//                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .get();
//            imageFuli.setWidth(bitmap.getWidth());
//            imageFuli.setHeight(bitmap.getHeight());
            imageFuli.setType(type);
            realm.copyToRealm(imageFuli);
        }

        realm.commitTransaction();//提交事务
        realm.close();*/
    }

    public static void HandleDoubanResponseFromHttp(Context context, String httpResponse, int type) throws ExecutionException, InterruptedException {
        Log.d("http", "http" + type);
        /*Realm realm = Realm.getInstance(context);
        realm.beginTransaction();//开启事务
        Document document = Jsoup.parse(httpResponse);
        Elements elements = document.select("div[class=thumbnail]>div[class=img_single]>a>img");
        for (Element e :elements){
            String url = e.attr("src");
            ImageFuli imageFuli =new ImageFuli(url);
//            Bitmap bitmap = Glide.with(context).load(imageFuli.getUrl()).asBitmap().thumbnail(0.1f)
//                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .get();
//            imageFuli.setWidth(bitmap.getWidth());
//            imageFuli.setHeight(bitmap.getHeight());
            imageFuli.setType(type);
            realm.copyToRealm(imageFuli);
        }
        realm.commitTransaction();//提交事务
        realm.close();*/
    }

    /**网络请求 HttpURLConnection get*/
    public static String get(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.connect();
        //200-300 :成功返回数据
        //300 - 400:跳转
        // 400 - 500: 错误请求,发生在客户端
        //500: 服务器内部错
        //请求成功
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            //将输入流转换为字符串,并返回
            String json = IOUtils.streamToString(is);
            return json;
        }
        return null;
    }
}
