package com.lzy.imagepicker.utils;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    public static Uri getUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "CZCG_JPEG_" + timeStamp + ".png";
        File file = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM + "/czcg/" + imageFileName);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri uri = Uri.fromFile(file);
        return uri;
    }
}
