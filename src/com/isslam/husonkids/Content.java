package com.isslam.husonkids;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Content extends FragmentActivity {

	Context context;

	private void playSounds(int position) {
		MediaManager mediaManager = MediaManager.getInstance(this);
		mediaManager.setContext(this);
		mediaManager.playSounds(position, 1);
	}

	Handler handler;
	Runnable runnable;
	Animation anim2;
	ImageView img_header_bg;
	RelativeLayout main_layout;
	TextView txt_theker;

	@TargetApi(16)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_ly);
		context = this;
		main_layout = (RelativeLayout) findViewById(R.id.main_layout);
		txt_theker = (TextView) findViewById(R.id.txt_theker);
		txt_theker.setVisibility(View.GONE);
		String fontPath = "fonts/aref_light.ttf";// kufyan.otf
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txt_theker.setTypeface(tf);
		String athkar_titles = getResources().getStringArray(R.array.athkars)[MyPagerAdapter.lastPage];
		txt_theker.setText(athkar_titles);
		// txt_theker.setShadowLayer(30, 0, 0, Color.BLACK);
		anim2 = AnimationUtils.loadAnimation(this, R.anim.popup_show);
		img_header_bg = (ImageView) findViewById(R.id.img_header_bg);
		anim2.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				ColorMatrix matrix = new ColorMatrix();
				matrix.setSaturation(0);
				txt_theker.setVisibility(View.VISIBLE);
				ColorMatrixColorFilter filter = new ColorMatrixColorFilter(
						matrix);
				main_layout.getBackground().setColorFilter(filter);
			}
		});
		img_header_bg.setVisibility(View.INVISIBLE);

		String image = "img_bg_" + MyPagerAdapter.lastPage;

		Context _context = main_layout.getContext();
		int id = _context.getResources().getIdentifier(image, "drawable",
				_context.getPackageName());
		Drawable background = getResources().getDrawable(id);
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			main_layout.setBackgroundDrawable(background);

		} else {

			main_layout.setBackground(background);
		}
		if (handler == null)
			handler = new Handler();
		if (runnable != null)
			handler.removeCallbacks(runnable);
		runnable = new Runnable() {
			@Override
			public void run() {
				playSounds(MyPagerAdapter.lastPage);

				img_header_bg.startAnimation(anim2);
				img_header_bg.setVisibility(View.VISIBLE);

			}
		};
		handler.postDelayed(runnable, 600);

		final FloatingActionButton btn_share = (FloatingActionButton) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);

				String shareBody = getResources().getStringArray(
						R.array.athkars)[MyPagerAdapter.lastPage]
						+ "\n"
						+ getString(R.string.share_extra)
						+ "\n"
						+ "http://play.google.com/store/apps/details?id="
						+ context.getPackageName();
				sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
				sendIntent.setType("text/plain");

				startActivity(Intent
						.createChooser(
								sendIntent,
								getResources().getStringArray(
										R.array.athkar_titles)[MyPagerAdapter.lastPage]));

			}
		});

		final FloatingActionButton btn_home = (FloatingActionButton) findViewById(R.id.btn_home);
		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GotoHome();

			}
		});
		final FloatingActionButton btn_repeat = (FloatingActionButton) findViewById(R.id.btn_repeat);
		btn_repeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playSounds(MyPagerAdapter.lastPage);

			}
		});

		final FloatingActionButton btn_mute = (FloatingActionButton) findViewById(R.id.btn_mute);
		btn_mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_repeat.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				sharedPreferencesManager.savePreferences(
						SharedPreferencesManager._sound, 0);
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();
				MediaManager mediaManager = MediaManager.getInstance(context);
				mediaManager.StopSounds();
			}
		});
		// /floating Actions
		final FloatingActionButton btn_boy = (FloatingActionButton) findViewById(R.id.btn_boy);
		btn_boy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				btn_repeat.setVisibility(View.VISIBLE);
				int _sound = sharedPreferencesManager.GetIntegerPreferences(
						SharedPreferencesManager._sound, 1);
				if (_sound != 1) {
					sharedPreferencesManager.savePreferences(
							SharedPreferencesManager._sound, 1);

					MediaManager mediaManager = MediaManager
							.getInstance(context);
					mediaManager.setContext(context);
					mediaManager.playSounds(MyPagerAdapter.lastPage, 1);
					// TODO Auto-generated method stub
				}
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();

			}
		});

		final FloatingActionButton btn_girl = (FloatingActionButton) findViewById(R.id.btn_girl);
		btn_girl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				btn_repeat.setVisibility(View.VISIBLE);
				int _sound = sharedPreferencesManager.GetIntegerPreferences(
						SharedPreferencesManager._sound, 2);
				if (_sound != 2) {
					sharedPreferencesManager.savePreferences(
							SharedPreferencesManager._sound, 2);

					MediaManager mediaManager = MediaManager
							.getInstance(context);
					mediaManager.setContext(context);
					mediaManager.playSounds(MyPagerAdapter.lastPage, 1);
					// TODO Auto-generated method stub
				}
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();

			}
		});

		SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
				.getInstance(context);

		int _sound = sharedPreferencesManager.GetIntegerPreferences(
				SharedPreferencesManager._sound, 2);
		if (_sound == 0)
			btn_repeat.setVisibility(View.GONE);
		else
			btn_repeat.setVisibility(View.VISIBLE);

	}

	@Override
	public void onBackPressed() {

		GotoHome();

	}

	private void GotoHome() {
		MediaManager mediaManager = MediaManager.getInstance(this);
		mediaManager.setContext(this);
		mediaManager.StopSounds();
		if (main_layout != null)
			main_layout.getBackground().clearColorFilter();
		txt_theker.setVisibility(View.GONE);
		anim2 = AnimationUtils.loadAnimation(Content.this, R.anim.popup_hide);
		anim2.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				img_header_bg.setVisibility(View.GONE);
				finish();
			}
		});
		img_header_bg.startAnimation(anim2);
		img_header_bg.setVisibility(View.VISIBLE);
	}

}
