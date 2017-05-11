package xiaoyalllove.pictureschooseutil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ChooseImageBean;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.manager.ChooseImageManager;
import com.lzy.imagepicker.manager.TakePhotoManager;

import java.util.ArrayList;

import xiaoyalllove.pictureschooseutil.adapter.ImagePickerAdapter;

public class MainActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener, TakePhoto.TakeResultListener, InvokeListener {
    public static final int IMAGE_ITEM_ADD = -1;
    private ArrayList<ImageItem> selImageList;
    private ImagePickerAdapter adapter;
    private int maxImgCount = 1;               //允许选择图片最大数
    private ChooseImageBean chooseImageBean;
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhoto = TakePhotoManager.getInstance().getTakePhoto(this, this, this);
        takePhoto.onCreate(savedInstanceState);

        initWidget();
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            default:
                chooseImageBean = new ChooseImageBean();
                chooseImageBean.setMaxSelects(4);
                chooseImageBean.setRateTailor(false);
                chooseImageBean.setTailoringRate(1);
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
                ChooseImageManager.getInstance().chooseImage(MainActivity.this, chooseImageBean,
                        takePhoto, null, false);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            takePhoto.onActivityResult(requestCode, resultCode, data);
        } else {
            ArrayList<ImageItem> images = ChooseImageManager.getInstance().createSuccees(resultCode, requestCode, data);
            if (images.size() > 0) {
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    /**
     * 拍照成功返回的监听
     *
     * @param result
     */
    @Override
    public void takeSuccess(TResult result) {
        String picFileFullName = result.getImage().getOriginalPath(); //--图片原始路径
        ImageItem imageItem = new ImageItem();
        imageItem.setPath(picFileFullName);
        selImageList.add(imageItem);
        adapter.setImages(selImageList);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        return TakePhotoManager.getInstance().invoke(invokeParam, this);
    }
}
