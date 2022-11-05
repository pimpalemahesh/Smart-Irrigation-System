package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.Adapter.SliderAdapter;
import com.myinnovation.smartirrigationsystem.databinding.ActivitySliderBinding;

import java.util.Locale;

public class SliderActivity extends AppCompatActivity {

    ActivitySliderBinding binding;
    int[] layouts;
    SliderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        final String[] languages = {"English", "Marathi"};
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//        mBuilder.setTitle("Select Language");
//
//        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    setLocale("en");
//                    dialog.dismiss();
//                } else {
//                    setLocale("mr");
//                    dialog.dismiss();
//                }
//            }
//        });
//        AlertDialog dialog = mBuilder.create();
//        dialog.show();


        layouts = new int[] {
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };

        adapter = new SliderAdapter(this,layouts);
        binding.pager.setAdapter(adapter);
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.pager.getCurrentItem()+1 < layouts.length){
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                }
            }
        });

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == layouts.length - 1){
                    binding.next.setText("Continue");
                }else {
                    binding.next.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
//
//    private void setLocale(String language) {
//        Locale locale = new Locale(language, "IN");
//        Locale.setDefault(locale);
//        Configuration config = this.getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(locale);
//        } else{
//            config.locale = locale;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            createConfigurationContext(config);
//        }
//        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
//
//        SharedPreferences.Editor editor = getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
//        editor.putString("LANG", language);
//        editor.apply();
//    }
}