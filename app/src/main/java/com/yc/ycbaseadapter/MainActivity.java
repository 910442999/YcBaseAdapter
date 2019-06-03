package com.yc.ycbaseadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                start(OneItemActivity.class);
                break;
            case R.id.button2:
                start(RefreshActivity1.class);
                break;
            case R.id.button3:
                start(TreeViewItemActivity.class);
                break;
            case R.id.button5:
                start(MultiItemActivity.class);
                break;
            case R.id.button4:
                start(MoreTypeItemActivity.class);
                break;
            case R.id.button6:
                start(OverFlyingActivity.class);
                break;
            case R.id.button7:
                start(LadderActivity.class);
                break;
            case R.id.button8:
                start(FlowActivity.class);
                break;
            case R.id.button9:
                start(RefreshLoadActivity.class);
                break;

        }
    }

    private void start(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
