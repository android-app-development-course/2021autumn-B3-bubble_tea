package com.my.bubbletea.data;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.my.bubbletea.R;

//这是一个画头像圆形的控件，后来没用了
@SuppressLint("AppCompatCustomView")
public class BitmapShaderCircularView extends ImageView {

    private Paint paint;
    private int radius;  //圆形头像的半径
    private float mScale;
    private int pathWidth;//绘制轨迹线条宽度
    private int shadeColor;//遮罩颜色，argb

    public BitmapShaderCircularView(Context context) {
        super(context);
    }

    public BitmapShaderCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapShaderCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //首先在view调用测量的时候，我们根据宽高取得一个正方形
        //并初始化半径
        int minTarget = Math.min(getMeasuredWidth(), getMeasuredHeight());
        radius = minTarget / 2;

        //重新设置宽高
        setMeasuredDimension(minTarget, minTarget);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();

        Bitmap bitmap = drawable2Bitmap(getDrawable());
        if (bitmap == null){
            return;
        }

        //构建BitmapShader对象，设置为CLAMP模式，当所画图形的尺寸小于bitmap尺寸的时候，会对bitmap进行裁剪
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //我们要对bitmap进行缩放处理
        //radius * 2为宽高，再取bitmap的宽高中值小的进行相除，取得bitmap缩放比例
        mScale = (radius * 2.0f) / Math.min(bitmap.getWidth(), bitmap.getHeight());
//        Log.i("lgq","tp-----"+bitmap.getWidth()+"........"+bitmap.getHeight());


        //设置矩阵，针对bitmap作缩放处理
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        bitmapShader.setLocalMatrix(matrix);

        paint.setShader(bitmapShader);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //这里开始画一个圆形，并对paint设置的BitmapShader中的bitmap进行裁剪处理
        canvas.drawCircle(radius, radius, radius, paint);


        // 得到自定义视图的高度
        int viewHeight;

        // 得到自定义视图的宽度
        int viewWidth;

        // 得到自定义视图的X轴中心点
        int viewCenterX;

        // 得到自定义视图的Y轴中心点
        int viewCenterY;

        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();

        viewCenterX = viewWidth / 2;
        viewCenterY = viewHeight / 2;

        Paint mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.pink));//14549B
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF rectF = new RectF();

        rectF.left = viewCenterX - radius+5;
        rectF.top = viewCenterY - radius+5;
        rectF.right = viewCenterX + radius-5;
        rectF.bottom = viewCenterY + radius-5;



        canvas.drawArc(rectF, 0, 360, false, mPaint);//画边框


//        canvas.drawPath(path, mPaint);

    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


}