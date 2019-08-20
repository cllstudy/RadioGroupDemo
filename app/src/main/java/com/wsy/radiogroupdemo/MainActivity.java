package com.wsy.radiogroupdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroupAuto rgp;
    private String[] loanList;
//    private String[] loanFeeList;
    private List<String> loanAndFeeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgp = findViewById(R.id.my_radio_group_auto);
        initView();
    }

    private void initView() {
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        loanAndFeeList = new ArrayList<>();
        loanList = "2,4,6,8,10,11,12".split(",");
//        loanFeeList = "50,80,100,20,30,50,70".split(",");
        //求最大最小值 (为了保持RadioButton文字长度一致,跟最长的保持一致!)
        int max =Integer.parseInt(loanList[0]);
        int min = Integer.parseInt(loanList[0]);
        for (String i : loanList) {
            int j = Integer.parseInt(i);
            max = max > j ? max : j;
            min = min < j ? min : j;
        }
        String maxS = String.valueOf(max);
        int maxLen = maxS.length();
        for (int i = 0; i < loanList.length; i++) {
//            loanAndFeeList.add( loanList[i] + "," + loanFeeList[i]);
            loanAndFeeList.add( loanList[i]);
        }
        int len = loanAndFeeList.size();
        for (int j = 0; j < len; j++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(10, 0, 10, 0); // 设置文字距离按钮四周的距离
//            radioButton.setButtonDrawable(R.drawable.transfer_radiobutton_drawable);
            String newLoanList = loanList[j];
            if (loanList[j].length() < maxLen) {
                newLoanList = newLoanList + appendLength(maxLen - loanList[j].length());
                // 实现 TextView同时显示两种风格文字 http://txlong-onz.iteye.com/blog/1142781
                SpannableStringBuilder sb = new SpannableStringBuilder(newLoanList);
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.WHITE);
                sb.setSpan(fcs, loanList[j].length(), maxLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                radioButton.setText(sb);
            } else {
                newLoanList = loanList[j];
                radioButton.setText(newLoanList);
            }
            radioButton.setId(j); //设置RadioButton的id
            radioButton.setTag(loanAndFeeList.get(j));
            radioButton.setTextSize(13); //默认单位是 sp
            radioButton.setHeight(50); //默认单位是px
            rgp.addView(radioButton); //添加到RadioGroup中
        }
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton clickRadioButton = (RadioButton) group.findViewById(checkedId);
                String tipInfo = "id: " + clickRadioButton.getId() + " text: " + clickRadioButton.getText() +
                        /*"hint: " + clickRadioButton.getHint() +*/ " tag:" + clickRadioButton.getTag();
                Toast.makeText(MainActivity.this, tipInfo,
                        Toast.LENGTH_SHORT).show();
            }
        });
        //根据这个来设置默认选中的项, 注意,这个要设置在监听之后!,否则默认点击监听不到!虽然有选中效果
        //参考 http://blog.csdn.net/lzqjfly/article/details/16963645
        //以及http://stackoverflow.com/questions/9175635/how-to-set-radio-button-checked-as-default-in-radiogroup-with-android
        rgp.check(0);
    }

    /**
     * 补全长度,保持最长的长度
     *
     * @param count 字符串长度
     * @return 补全后的长度
     * 这里长度不够的就用 "s" 占位, 赋值的时候将字体设置白色!
     */
    public String appendLength(int count) {
        String st = "";
        if (count < 0) {
            count = 0;
        }
        for (int i = 0; i < count; i++) {
            st = st + "s";
        }
        return st;
    }
}
