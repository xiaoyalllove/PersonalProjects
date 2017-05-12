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

 
 ChooseImageBean chooseImageBean = new ChooseImageBean();
                 chooseImageBean.setMaxSelects(4);
                 chooseImageBean.setRateTailor(false);
                 chooseImageBean.setTailoringRate(1);
              
  ChooseImageManager.getInstance().chooseImage(MainActivity.this, chooseImageBean,
                        takePhoto, null, false);
                        
```
