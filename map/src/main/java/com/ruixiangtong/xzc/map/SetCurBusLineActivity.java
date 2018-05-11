package com.ruixiangtong.xzc.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 设置当前线路名称以及方向
 *
 * @author Xurj
 */
public class SetCurBusLineActivity extends Activity {
    EditText busLineEditText;
    Button okBtn;
    Button changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bus_cur_line);
        busLineEditText = (EditText) findViewById(R.id.lineName);

        okBtn = (Button) findViewById(R.id.ok);
        changeBtn = (Button) findViewById(R.id.changeLine);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SetCurBusLineActivity.this, BusCurLocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busLineEditText.setEnabled(true);
            }
        });
    }


}
