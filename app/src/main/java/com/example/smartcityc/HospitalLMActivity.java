package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.smartcityc.Fragment.LmPtFragment;
import com.example.smartcityc.Fragment.LmZjFragment;

public class HospitalLMActivity extends AppCompatActivity {
    private Button lmZj;
    private Button lmPt;
    private LinearLayout lmLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_lmactivity);
        bindView();
        initEvent();
    }

    private FragmentTransaction fragmentTransaction(Fragment fragment) {
        return getSupportFragmentManager().beginTransaction().replace(R.id.lm_layout, fragment);
    }
    private void initEvent() {
        fragmentTransaction(new LmPtFragment()).commit();
        lmZj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lmPt.setBackgroundResource(R.color.gray);
                lmZj.setBackgroundResource(R.color.blue);
                fragmentTransaction(new LmZjFragment()).commit();
            }
        });
        lmPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lmZj.setBackgroundResource(R.color.gray);
                lmPt.setBackgroundResource(R.color.blue);
                fragmentTransaction(new LmPtFragment()).commit();
            }
        });
    }

    private void bindView() {
        lmZj = (Button) findViewById(R.id.lm_zj);
        lmPt = (Button) findViewById(R.id.lm_pt);
        lmLayout = (LinearLayout) findViewById(R.id.lm_layout);
    }
}