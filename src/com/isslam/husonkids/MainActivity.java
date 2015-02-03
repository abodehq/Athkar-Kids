package com.isslam.husonkids;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	public final static int PAGES = 10;
	// You can choose a bigger number for LOOPS, but you know, nobody will fling
	// more than 1000 times just in order to test your "infinite" ViewPager :D
	public final static int LOOPS = 10;
	public final static int FIRST_PAGE = 1;
	public final static float BIG_SCALE = 1.0f;
	public final static float SMALL_SCALE = 0.8f;
	public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
	ImageView img_header_bg;
	public MyPagerAdapter adapter;
	public ViewPager pager;
	RelativeLayout ly_main_bg;
	RelativeLayout main_layout;
	RelativeLayout header;
	Drawable backgrounds[] = new Drawable[2];
	Context context;

	private void playSounds(int position) {

		MediaManager mediaManager = MediaManager.getInstance(this);
		mediaManager.setContext(this);
		mediaManager.playSounds(position, 0);

	}

	public float convertFromDp(float input) {
		final float scale = getResources().getDisplayMetrics().density;
		return ((input - 0.5f) / scale);
	}

	Handler handler;
	Runnable runnable;
	Animation anim;
	Animation animout;

	public void onPageChanged() {
		Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.hide_from_top);
		header.startAnimation(anim2);
	}

	public void OnpageRemains() {
		Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.grow_from_top);
		header.startAnimation(anim2);
	}

	public void OnpageSelected(final int position, final int lastPage) {
		Log.e("athkar", "athkar");

		String athkar_titles = getResources().getStringArray(
				R.array.athkar_titles)[position];
		txtTitle.setText(getResources().getString(R.string.athkar_pre) + " "
				+ athkar_titles);
		Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.grow_from_top);

		header.startAnimation(anim2);
		if (handler == null)
			handler = new Handler();
		if (runnable != null)
			handler.removeCallbacks(runnable);
		runnable = new Runnable() {
			@Override
			public void run() {
				double num = ((Math.random() * 1000) % 360) - 180;

				img_header_bg.setColorFilter(ColorFilterGenerator
						.adjustHue((float) num));
				OnpageSelectedNow(position, lastPage);
				playSounds(position);

			}
		};
		handler.postDelayed(runnable, 600);
	}

	@SuppressLint("NewApi")
	public void OnpageSelectedNow(int position, int lastPage) {

		String image = "img_bg_" + position;
		Log.e("image", image);
		Context context = ly_main_bg.getContext();
		int id = context.getResources().getIdentifier(image, "drawable",
				context.getPackageName());

		// Log.e("---->",id+"");
		backgrounds[0] = ly_main_bg.getBackground();// res.getDrawable(R.drawable.img_bg_1);
		backgrounds[1] = getResources().getDrawable(id);

		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			main_layout.setBackgroundDrawable(backgrounds[0]);
			ly_main_bg.setBackgroundDrawable(backgrounds[1]);

		} else {
			main_layout.setBackground(backgrounds[0]);
			ly_main_bg.setBackground(backgrounds[1]);
		}

		anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
		ly_main_bg.startAnimation(anim);

		// crossfader.startTransition(100);
	}

	TextView txtTitle;

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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		ly_main_bg = (RelativeLayout) findViewById(R.id.ly_main_bg);
		header = (RelativeLayout) findViewById(R.id.header);
		img_header_bg = (ImageView) findViewById(R.id.img_header_bg);
		main_layout = (RelativeLayout) findViewById(R.id.main_layout);

		// ColorFilterGenerator.setAlpha(img_header_bg, 0.8f);

		pager = (ViewPager) findViewById(R.id.myviewpager);

		adapter = new MyPagerAdapter(this, this.getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(adapter);

		txtTitle = (TextView) findViewById(R.id.txtTitle);

		txtTitle.setTextSize(convertFromDp(txtTitle.getTextSize()));

		String fontPath = "fonts/neckar.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtTitle.setTypeface(tf);
		// Set current item to the middle page so we can fling to both
		// directions left and right
		pager.setCurrentItem(FIRST_PAGE);

		// Necessary or the pager will only have one extra page to show
		// make this at least however many pages you can see
		pager.setOffscreenPageLimit(3);

		// Set margin for pages as a negative number, so a part of next and
		// previous pages will be showed

		//
		pager.setPageMargin(Integer.parseInt(getString(R.string.pagermargin)));

		final FloatingActionButton btn_mute = (FloatingActionButton) findViewById(R.id.btn_mute);
		btn_mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				sharedPreferencesManager.savePreferences(
						SharedPreferencesManager._sound, 0);
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();

			}
		});
		// /floating Actions
		final FloatingActionButton btn_boy = (FloatingActionButton) findViewById(R.id.btn_boy);
		btn_boy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				sharedPreferencesManager.savePreferences(
						SharedPreferencesManager._sound, 1);
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();
				playSounds(MyPagerAdapter.lastPage);
			}
		});

		final FloatingActionButton btn_girl = (FloatingActionButton) findViewById(R.id.btn_girl);
		btn_girl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager
						.getInstance(context);
				sharedPreferencesManager.savePreferences(
						SharedPreferencesManager._sound, 2);
				((FloatingActionsMenu) findViewById(R.id.multiple_actions))
						.collapse();
				playSounds(MyPagerAdapter.lastPage);
			}
		});

		final FloatingActionButton btn_share = (FloatingActionButton) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = getString(R.string.share_body) + "\n"
						+ "http://play.google.com/store/apps/details?id="
						+ context.getPackageName();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.share_title));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						getString(R.string.share_title)));

			}
		});

		final FloatingActionButton btn_exit = (FloatingActionButton) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				exitByBackKey();
			}
		});

	}

}
