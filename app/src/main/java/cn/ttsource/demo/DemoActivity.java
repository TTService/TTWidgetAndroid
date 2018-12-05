package cn.ttsource.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cn.ttsource.widget.R;

/**
 * **********************************************
 * <p/>
 * Date: 2018-12-05 10:21
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = this.findViewById(R.id.list);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(DemoActivity.this, ButtonActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(DemoActivity.this, ScrollActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(DemoActivity.this, ScrollActivity2.class));
                        break;
                }
            }
        });
    }

    private ArrayList<String> getData() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("CircleMoveView");
        titleList.add("HorizontalScrollViewEx");
        titleList.add("HorizontalScrollViewEx2");
        return titleList;
    }
}

