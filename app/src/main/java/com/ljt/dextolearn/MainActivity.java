package com.ljt.dextolearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ljt.dextolearn.dynamic.Dynamic;

import java.io.File;
import java.io.IOException;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    Dynamic dynamic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDexClass();
                Log.d("TAG","----->>>>已执行...");
            }
        });
    }
    //加载Dex文件中的class,并调用其中的sayHello方法
    public void loadDexClass(){
        File cacheDir = FileUtils.getCacheDir(getApplicationContext());
        String internalPath = cacheDir.getAbsolutePath() + File.separator + "dynamic_dex.jar";
        File desFile = new File(internalPath);
        try {
            if(!desFile.exists()){
                desFile.createNewFile();
                FileUtils.copyFiles(this,"dynamic_dex.jar",desFile);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        DexClassLoader dexClassLoader = new DexClassLoader(internalPath, cacheDir.getAbsolutePath(), null, getClassLoader());
        try{
            Class libClass = dexClassLoader.loadClass("com.ljt.dextolearn.dynamic.impl.DynamicImpl");
            dynamic= (Dynamic) libClass.newInstance();
            if (dynamic != null)
                Toast.makeText(this, dynamic.sayHello(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
