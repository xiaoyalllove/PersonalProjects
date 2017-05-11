package com.lzy.imagepicker.manager;

import android.app.Activity;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

/**
 * 图片选择第三方库，拍照
 * compile 'com.squareup.picasso:picasso:2.5.2'
 * compile 'com.jph.takephoto:takephoto_library:4.0.3'
 */

public class TakePhotoManager {
    private static TakePhotoManager takePhotoManager = new TakePhotoManager();

    private TakePhotoManager() {

    }

    public static TakePhotoManager getInstance() {
        return takePhotoManager;
    }

    private static TakePhoto takePhoto;

    /**
     * 创建 TakePhoto 对象
     *
     * @param activity
     * @param listener
     * @param takeResultListener
     * @return
     */
    public static TakePhoto getTakePhoto(Activity activity, InvokeListener listener, TakePhoto.TakeResultListener takeResultListener) {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(listener).bind(new TakePhotoImpl(activity, takeResultListener));
        }
        return takePhoto;
    }

    public static PermissionManager.TPermissionType invoke(InvokeParam invokeParam, Activity activity) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(activity), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
//            invokeParam = invokeParam;
        }
        return type;
    }
}
