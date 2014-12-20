/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.spear.sample.activity;

import java.util.List;

import com.fax.utils.http.HttpUtils;
import com.fax.utils.task.ResultAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.R;
import xjgjly.lib.com.fragment.main.ImagesFragment;
import xjgjly.lib.com.fragment.main.imagedetailFragment;
import xjgjly.lib.com.model.ArchiveJson;
import xjgjly.lib.com.model.DedeArchivesEntity;
import xjgjly.lib.com.model.DedeUploadsEntity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import me.xiaopan.android.spear.sample.adapter.GridImageAdapter;
import me.xiaopan.android.spear.sample.fragment.ViewPagerFragment;

@SuppressLint("NewApi")
public class ViewPagerActivity extends ActionBarActivity {
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frameLayout = new FrameLayout(getBaseContext());
		frameLayout.setId(R.id.fragment_display);
		setContentView(frameLayout);
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new imagedetailFragment();
		fragment.setArguments(getIntent().getExtras());
		fragmentTransaction.replace(R.id.fragment_display, fragment);
		fragmentTransaction.commitAllowingStateLoss();
	}
	
}
