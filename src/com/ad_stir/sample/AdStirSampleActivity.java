/*
   Copyright 2012 motionBEAT Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.ad_stir.sample;

import com.ngigroup.adstir.AdstirTerminate;
import com.ngigroup.adstir.AdstirView;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class AdStirSampleActivity extends Activity {
    private AdstirView adstirView;
    LinearLayout layout = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        layout = (LinearLayout) findViewById(R.id.layout_main); // 広告を挿入したいlayoutのidを指定してください。
        adstirView = new AdstirView(this,1); // 枠No未指定の場合はデフォルト枠が使用されます
        layout.addView(adstirView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); // layoutへaddViewしてください。

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
                // ActivityのonDestroy()時にAdstirTerminateクラスを初期化してください。
        new AdstirTerminate(this);
    }

        // AdstirViewのstopメソッドを実行することにより、不要な通信を抑えることが出来ます。
    @Override
    protected void onPause() {
            super.onPause();
            adstirView.stop();
          //親Viewを取得してremoveView実行
            ViewGroup parent = (ViewGroup)adstirView.getParent();
            if ( parent != null ) {
                parent.removeView(adstirView);
            }
        }

        // AdstirViewのstartメソッドを実行することにより、通信を再開することが出来ます。
    @Override
    protected void onResume() {
            super.onResume();
            int index = 0;
            while(layout.getChildAt(index) != null){
                if(layout.getChildAt(index) == adstirView){
                    return;
                }
                index++;
            }
            adstirView = null;
            adstirView = new AdstirView(this,1);
            layout.addView(adstirView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            adstirView.start();
    }
}