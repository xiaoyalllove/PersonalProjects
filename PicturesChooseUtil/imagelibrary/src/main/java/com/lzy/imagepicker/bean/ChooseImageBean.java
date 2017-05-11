package com.lzy.imagepicker.bean;

/**
 * 调用相机或相册获取照片(js)
 * <p>
 * maxSelects: 最大获取相片数量。
 * isRateTailor: 照片是否需要固定比例剪裁。
 * tailoringRate: 照片固定剪裁比例，width/height。
 * success: 获取成功回调，参数data为图片路径组成的数组，Array。
 */
 /* gwt.chooseImage({
            maxSelects: 1,
            isRateTailor: false,
            tailoringRate: 0,
            success: function(data) {
            //your-code
            }
        });
  */
public class ChooseImageBean {
    int maxSelects;
    boolean isRateTailor;
    float tailoringRate;

    public int getMaxSelects() {
        return maxSelects;
    }

    public void setMaxSelects(int maxSelects) {
        this.maxSelects = maxSelects;
    }

    public boolean isRateTailor() {
        return isRateTailor;
    }

    public void setRateTailor(boolean rateTailor) {
        isRateTailor = rateTailor;
    }

    public float getTailoringRate() {
        return tailoringRate;
    }

    public void setTailoringRate(float tailoringRate) {
        this.tailoringRate = tailoringRate;
    }
}
