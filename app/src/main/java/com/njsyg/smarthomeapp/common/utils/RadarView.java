package com.njsyg.smarthomeapp.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.njsyg.smarthomeapp.R;

/**
 * Created by zz on 2016/9/2.
 */
public class RadarView extends View {

    private Paint circlePaint;//圆形画笔
    private Paint linePaint;//线形画笔
    private Paint sweepPaint;//扫描画笔
    SweepGradient sweepGradient;//扇形渐变
    int degree = 0;

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    //初始化定义的画笔
    private void initPaint() {
        Resources r = this.getResources();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//圆形画笔，设置Paint为抗锯齿
        circlePaint.setARGB(255, 50, 57, 74);//设置透明度和RGB颜色
        circlePaint.setStrokeWidth(3);//轮廓宽度
        circlePaint.setStyle(Paint.Style.STROKE);//设置填充样式,stroke为空心

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//线性画笔
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setARGB(150, 245, 245, 245);
        linePaint.setStrokeWidth(2);

        sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//雷达shader画笔
        sweepPaint.setStrokeWidth(4);
        sweepPaint.setStrokeCap(Paint.Cap.ROUND);
        sweepGradient = new SweepGradient(0, 0, r.getColor(R.color.start_color), r.getColor(R.color.end_color));
        sweepPaint.setShader(sweepGradient);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;//获取最短的边作为直径
        setMeasuredDimension(d, d);//重写测量方法，保证获取的画布是正方形
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int Width = getMeasuredWidth();//// 计算控件的中心位置
        int Height = getMeasuredHeight();
        int pointX = Width / 2;// 获得圆心坐标
        int pointY = Height / 2;
        int radius = (pointX >= pointY) ? pointY : pointX;// 设置半径
        radius -= 10;// 设置半径
        canvas.save();// 保存 Canvas 坐标原点
        canvas.translate(pointX, pointY);// 设置旋转的原点
        canvas.rotate(270 + degree);
        canvas.drawCircle(0, 0, radius, sweepPaint);// 绘制扫描区域
        canvas.restore();// 恢复原 Canvas 坐标 (0,0)
        canvas.drawCircle(pointX, pointY, radius, circlePaint);// 绘制 3 个嵌套同心圆形，使用 circlePaint 画笔
        circlePaint.setAlpha(50);// 降低内部圆形的透明度
        circlePaint.setStrokeWidth(1);// 轮廓宽度
        canvas.drawLine(pointX, 10, pointX, 2 * radius + 10, linePaint);// 绘制十字分割线 ， 竖线
        canvas.drawLine(10, pointY, 2 * radius + 10, pointY, linePaint);
        canvas.save();// 保存 Canvas 坐标原点
        canvas.translate(10, radius + 10);// 设置相对横线起始坐标
        degree += 10;// 扫描旋转增量度数
    }
}
