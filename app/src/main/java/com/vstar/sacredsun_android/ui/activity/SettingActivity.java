package com.vstar.sacredsun_android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.util.SPHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tanghuailong on 2017/1/19.
 */

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.work_shop_code)
    EditText editText;
    @BindView(R.id.entry_button)
    Button entryButton;

    private static final String TAG = "work_shop_code";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        //隐藏软键盘
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //跳转
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String workshop = editText.getText().toString().trim();

                //保存获得过的车间编号
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(TAG,workshop);
//                editor.commit();

                SPHelper.putAndApply(SettingActivity.this,getString(R.string.WORK_SHOP_CODE),workshop);

                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TAG,workshop);
                startActivity(intent);
            }
        });


    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
