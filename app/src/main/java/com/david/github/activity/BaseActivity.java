package com.david.github.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by davidhodge on 7/23/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }
}
