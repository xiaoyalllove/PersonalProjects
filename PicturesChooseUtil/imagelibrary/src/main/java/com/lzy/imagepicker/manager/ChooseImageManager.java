package com.lzy.imagepicker.manager;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.czcg.viewlib.beans.BensEntity;
import com.czcg.viewlib.beans.SheetBean;
import com.czcg.viewlib.widget.SheetViewWidget;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ChooseImageBean;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

// 调用相机或相册获取照片
public class ChooseImageManager {

    private static ChooseImageManager chooseImageManager = new ChooseImageManager();


    private ChooseImageManager() {

    }

    public static ChooseImageManager getInstance() {
        return chooseImageManager;
    }

    /**
     * 调用相机或相册获取照片
     *
     * @param activity
     * @param chooseImageBean
     * @param takePhoto
     * @param fragment
     * @param isCrop          拍照是否裁剪 true 裁剪
     */
    public void chooseImage(final AppCompatActivity activity, final ChooseImageBean chooseImageBean,
                            final TakePhoto takePhoto, final Fragment fragment, final boolean isCrop) {
        SheetBean sheetBean = new SheetBean();
        List<BensEntity> btns = new ArrayList<>();
        sheetBean.setTitle("");
        BensEntity b = new BensEntity();
        b.setTitle("拍摄");
        btns.add(b);
        BensEntity b1 = new BensEntity();
        b1.setTitle("从相机选择");
        btns.add(b1);
        sheetBean.setBtns(btns);
        SheetViewWidget.getInstance().createFragment(activity, sheetBean, new SheetViewWidget.SheetViewListener() {
            @Override
            public void listener(String title) {
                if (title.equals("拍摄")) {
                    createCamera(takePhoto, Constants.getUri(), isCrop);
                } else if (title.equals("从相机选择")) {
                    createAlbum(fragment, activity, chooseImageBean, Constants.getUri());
                }
            }
        });
    }

    /**
     * 相册
     *
     * @param uri
     * @param chooseImageBean
     */
    private void createAlbum(Fragment fragment, AppCompatActivity activity, ChooseImageBean chooseImageBean, Uri uri) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setSelectLimit(chooseImageBean.getMaxSelects());              //选中数量限制
        imagePicker.setRateTailor(chooseImageBean.isRateTailor());
        imagePicker.setTailoringRate(chooseImageBean.getTailoringRate());
        imagePicker.setOutputUri(uri);

        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(chooseImageBean.getMaxSelects());
        Intent intent = new Intent(activity, ImageGridActivity.class);
        if (fragment == null) {
            activity.startActivityForResult(intent, Constants.REQUEST_CODE_SELECT);
        } else {
            fragment.startActivityForResult(intent, Constants.REQUEST_CODE_SELECT);
        }
    }

    /**
     * 相机拍照
     *
     * @param takePhoto
     * @param uri
     */
    private void createCamera(TakePhoto takePhoto, Uri uri, boolean isCrop) {
        //纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setCorrectImage(true).create());

        //设置图片裁剪
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(800).setOutputY(800); //宽X高（任意缩放）
        // builder.setAspectX(800).setAspectY(800); //宽/高（等比缩放）
        builder.setWithOwnCrop(false);//true 从矩形四边中间缩放 false 从矩形四个点缩放
        if (isCrop) {
            takePhoto.onPickFromCaptureWithCrop(uri, builder.create()); //从相机中获取图片裁剪
        } else {
            takePhoto.onPickFromCapture(uri); // 从相机获取图片(不裁剪)
        }

    }

    /**
     * 图片选择成功回调
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    public ArrayList<ImageItem> createSuccees(int resultCode, int requestCode, Intent data) {
        ArrayList<ImageItem> arrayList = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//添加图片返回
            if (data != null && requestCode == Constants.REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    ImageItem imageItem = images.get(i);
                    arrayList.add(imageItem);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) { //预览图片返回
            if (data != null && requestCode == Constants.REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    ImageItem imageItem = images.get(i);
                    arrayList.add(imageItem);
                }
            }
        } else if (resultCode == ImagePicker.REQUEST_CODE_MYCROP) {//裁剪返回
            ImageItem imageItem = new ImageItem();
            imageItem.setPath(data.getStringExtra(ImagePicker.EXTRA_IMAGE_CROP));
            arrayList.add(imageItem);
        }
        return arrayList;
    }

}
