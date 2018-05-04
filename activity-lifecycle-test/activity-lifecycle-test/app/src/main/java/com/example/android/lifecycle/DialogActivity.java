/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Denys","DialogActivity - Create.Method: onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
    }

    /**
     * Callback method defined by the View
     * @param v
     */
    public void finishDialog(View v) {
        Log.d("Denys","DialogActivity - Finish.Method: finishDialog");
        DialogActivity.this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Denys","Start DialogActivity.Method: onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Denys","Restart DialogActivity.Method: onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Denys","Resume DialogActivity.Method: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Denys","Pause DialogActivity.Method: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Denys","Stop DialogActivity.Method: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Denys","Destroy DialogActivity.Method: onDestroy");
    }
}
