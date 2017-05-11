package com.lzy.imagepicker.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TException;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

/**
 * 裁剪管理类
 */
public class CropManager {
    private static CropManager cropManager = new CropManager();

    private CropManager() {
    }

    public static CropManager getInstance() {

        return cropManager;
    }

    /**
     * 裁剪配置
     *
     * @param imagePicker
     * @param takePhoto
     */
    public void cropConfig(ImagePicker imagePicker, TakePhoto takePhoto) {
        if (imagePicker.getSelectImageCount() == 1) {
            //设置图片裁剪
            CropOptions.Builder builder = new CropOptions.Builder();

            //照片是否需要固定比例剪裁。
            if (!imagePicker.isRateTailor()) { //宽X高（任意缩放）false
                builder.setOutputX(800).setOutputY(800);
            } else {//宽/高（等比缩放）true
                float mAspectRatioX = imagePicker.getTailoringRate();
                float mAspectRatioY = 1;
                if (mAspectRatioX < 1f) {
                    mAspectRatioX = mAspectRatioX * 10;
                    mAspectRatioY = mAspectRatioY * 10;
                }
                AspectRatio aspectRatio = new AspectRatio(mAspectRatioX, mAspectRatioY);
                imagePicker.setCropAspectX((int) (aspectRatio.getAspectRatioX()));
                imagePicker.setCropAspectY((int) (aspectRatio.getAspectRatioY()));
                builder.setAspectX(imagePicker.getCropAspectX()).setAspectY(imagePicker.getCropAspectY());
            }
            //true 从矩形四边中间缩放 false 从矩形四个点缩放
            builder.setWithOwnCrop(false);

            //获取需要的参数
            ArrayList<ImageItem> mImageItems = imagePicker.getSelectedImages();
            String imagePath = mImageItems.get(0).path;
            Uri imageUri = Uri.parse("file://" + imagePath);

            try {
                takePhoto.onCrop(imageUri, imagePicker.getOutputUri(), builder.create());
            } catch (TException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 裁剪成功 回调
     *
     * @param activity
     * @param result
     */
    public void cropSuccessBack(Activity activity, TResult result) {
        Intent intent = new Intent();
        intent.putExtra(ImagePicker.EXTRA_IMAGE_CROP, result.getImage().getOriginalPath());
        activity.setResult(ImagePicker.REQUEST_CODE_MYCROP, intent);
        activity.finish();
    }

}
