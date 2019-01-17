package com.yc.ycbaseadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
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
            case R.id.button4:

                break;
            case R.id.button5:

                start(MultiItemActivity.class);

                break;
        }
    }

    private void start(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
