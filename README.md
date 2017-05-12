# PersonalProjects
##### 调用相机或相册获取照片

 ``` java
 public class MainActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {
 
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhoto = TakePhotoManager.getInstance().getTakePhoto(this, this, this);
        takePhoto.onCreate(savedInstanceState);
   }

 maxSelects: 最大获取相片数量。
 isRateTailor: 照片是否需要固定比例剪裁。
 tailoringRate: 照片固定剪裁比例，width/height。

 ChooseImageBean chooseImageBean = new ChooseImageBean();
                 chooseImageBean.setMaxSelects(4);
                 chooseImageBean.setRateTailor(false);
                 chooseImageBean.setTailoringRate(1);
              
  ChooseImageManager.getInstance().chooseImage(MainActivity.this, chooseImageBean,
                        takePhoto, null, false);
                        
```
