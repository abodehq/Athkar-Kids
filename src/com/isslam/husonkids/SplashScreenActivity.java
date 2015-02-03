package com.isslam.husonkids;

import android.app.Activity;

import android.app.AlertDialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends Activity {

	protected int _splashTime = 15000; // Time before Run App Main Activity

	private Context context;

	LinearLayout ly_slogan;
	LinearLayout ly_app;
	RelativeLayout ly_ma;
	RelativeLayout ly_strip;
	FloatingActionButton btn_rate;
	FloatingActionButton btn_other;
	FrameLayout ly_enter;
	ImageView contentbg;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = SplashScreenActivity.this;

		// remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// application full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.ly_splash_screen);// Splash Layout

		ly_slogan = (LinearLayout) findViewById(R.id.ly_slogan);
		ly_app = (LinearLayout) findViewById(R.id.ly_app);
		ly_ma = (RelativeLayout) findViewById(R.id.ly_main_splash);
		ly_strip = (RelativeLayout) findViewById(R.id.ly_strip);

		contentbg = (ImageView) findViewById(R.id.contentbg);
		contentbg.setVisibility(View.GONE);
		ly_enter = (FrameLayout) findViewById(R.id.ly_enter);
		ly_enter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ly_enter.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		ly_enter.setVisibility(View.GONE);

		btn_other = (FloatingActionButton) findViewById(R.id.btn_other);
		btn_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri
							.parse("https://play.google.com/store/apps/developer?id=islam+is+the+way+of+life"));
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ context.getPackageName())));
				}

			}
		});
		btn_other.setVisibility(View.GONE);
		btn_rate = (FloatingActionButton) findViewById(R.id.btn_rate);
		btn_rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("market://details?id="
						+ context.getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
						| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
						| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ context.getPackageName())));
				}

			}
		});
		btn_rate.setVisibility(View.GONE);

		Animation anim = AnimationUtils.loadAnimation(
				SplashScreenActivity.this, R.anim.fade_out);
		ly_app.startAnimation(anim);
		ly_slogan.setVisibility(View.GONE);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {

				ly_slogan.setVisibility(View.VISIBLE);
				ly_app.setVisibility(View.GONE);
				Animation anim = AnimationUtils.loadAnimation(
						SplashScreenActivity.this, R.anim.fade_in);
				ly_slogan.startAnimation(anim);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					public void run() {
						playanim();

						// startActivity(i);
						// finish();
						// Actions to do after 10 seconds
					}
				}, 2000);
				// startActivity(i);
				// finish();
				// Actions to do after 10 seconds
			}
		}, 2000);

	}

	private void playanim() {
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		ly_ma.getBackground().setColorFilter(filter);
		Animation animf = AnimationUtils.loadAnimation(
				SplashScreenActivity.this, R.anim.strip_hide);
		ly_strip.startAnimation(animf);
		animf.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				ly_strip.setVisibility(View.GONE);
				btn_rate.setVisibility(View.VISIBLE);
				btn_other.setVisibility(View.VISIBLE);
				Animation animb = AnimationUtils.loadAnimation(
						SplashScreenActivity.this, R.anim.grow_from_bottom);
				btn_rate.startAnimation(animb);
				btn_other.startAnimation(animb);

				ly_enter.setVisibility(View.VISIBLE);
				Animation animc = AnimationUtils.loadAnimation(
						SplashScreenActivity.this, R.anim.grow_from_top_speed);
				ly_enter.startAnimation(animc);

				Animation myRotation = AnimationUtils.loadAnimation(
						SplashScreenActivity.this, R.anim.rotator);
				contentbg.setVisibility(View.VISIBLE);
				contentbg.startAnimation(myRotation);
				// grow_from_bottom

			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// check if user Click on
													// the splash to start app
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// synchronized (splashTread) {
			// splashTread.notifyAll();
			// }
		}
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void exitByBackKey() {

		AlertDialog alertbox = new AlertDialog.Builder(this)

				.setMessage(getResources().getString(R.string.app_exit))
				.setPositiveButton(
						getResources().getString(R.string.app_exit_confirm),
						new DialogInterface.OnClickListener() {

							// do something when the button is clicked
							public void onClick(DialogInterface arg0, int arg1) {

								System.exit(0);
								// close();

							}
						})
				.setNegativeButton(
						getResources().getString(R.string.app_exit_cancle),
						new DialogInterface.OnClickListener() {

							// do something when the button is clicked
							public void onClick(DialogInterface arg0, int arg1) {
							}
						}).show();
	}

	@Override
	public void onBackPressed() {

		// finish();

	}

}
