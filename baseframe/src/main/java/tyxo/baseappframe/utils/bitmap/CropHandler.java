package tyxo.baseappframe.utils.bitmap;

import android.content.Intent;
import android.net.Uri;

/**
* @author tyxo
* @created at 2016/9/20 15:37
* @des :
*/
public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCompressed(Uri uri);

    void onCancel();

    void onFailed(String message);

    void handleIntent(Intent intent, int requestCode);

    CropParams getCropParams();
}
