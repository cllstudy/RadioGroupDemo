package com.wsy.radiogroupdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * 作者： cll （cllstudy@163.com）<br>
 * 日期： 2019/8/20 13:56 <br>
 * 描述：
 */
public class RadioGroupAuto extends RadioGroup {
    public RadioGroupAuto(Context context) {
        super(context);
    }

    public RadioGroupAuto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取最大宽度
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        //获取Group中的Child数量
        int childCount = getChildCount();
        //设置Group的左边距，下面也会使用x计算每行所占的宽度
        int x = 0;
        //设置Group的上边距，下面也会使用y计算Group所占的高度
        int y = 30;
        //设置Group的行数
        int row = 0;
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                //重新计算child的宽高
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                //添加到X中，(width+10) 设置child左边距
                x += (width + 10);
                //行数*child高度+这次child高度=现在Group的高度,(height + 10)设置child上边距
                y = row * (height + 10) + (height + 10);
                //当前行宽X大于Group的最大宽度时，进行换行
                if (x > maxWidth) {
                    //当index不为0时，进行row++，防止FirstChild出现大于maxWidth时,提前进行row++
                    if (index != 0)
                        row++;
                    //child的width大于maxWidth时，重新设置child的width为最大宽度
                    if (width >= maxWidth) {
                        width = maxWidth - 30;
                    }
                    //重新设置当前X
                    x = (width + 20);
                    //重新设置现在Group的高度
                    y = row * (height + 10) + (height + 10);
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 10;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += (width + 10);
                y = row * (height + 10) + (height + 10);
                if (x > maxWidth) {
                    if (i != 0)
                        row++;
                    if (width >= maxWidth) {
                        width = maxWidth - 30;
                    }
                    x = (width + 20);
                    y = row * (height + 10) + (height + 10);
                }
                child.layout(x - width, y - height, x, y);
            }
        }
    }
}
