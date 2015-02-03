package com.isslam.husonkids;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;


public class MediaManager {

	private static MediaManager instance = null;
	private Context context;
	MediaPlayer mp = new MediaPlayer();
	public static MediaManager getInstance(Context _context) {
		if (instance == null) {
			instance = new MediaManager();
		}
		instance.setContext(_context);
		return instance;
	}
	 public void playSounds(int position,int type)
	  {
		 if(mp==null)
			 mp = new MediaPlayer();
	  	if(mp.isPlaying())
	  		  mp.stop();
	  	try
	      {  
	        
	          mp.reset();
	      } 
	  	catch(Exception e)
	  	{
	  		
	  	}
	      try {
	      	SharedPreferencesManager sharedPreferencesManager=SharedPreferencesManager.getInstance(context);
	  		int sound=sharedPreferencesManager.GetIntegerPreferences(SharedPreferencesManager._sound, 2);
	         if(sound!=0)
	         {
	  		AssetFileDescriptor afd;
	  		String mp3Path="";
	  		if(type==0)
	  			mp3Path = "mp3titles/f"+sound+"/title_"+position+".mp3";
	  		else
	  			mp3Path = "mp3/f"+sound+"/title_"+position+".mp3";
	  			
	          afd = context.getAssets().openFd(mp3Path);
	          mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
	          mp.prepare();
	          mp.start();
	         }
	      } catch (IllegalStateException e) {
	          e.printStackTrace();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }
	public void StopSounds()
	{
		 if(mp==null)
			 mp = new MediaPlayer();
		if(mp.isPlaying())
			  mp.stop();
		try
	    {  
	      
	        mp.reset();
	    } 
		catch(Exception e)
		{
			
		}
	}
	public void setContext(Context _context) {
		context = _context;
	}

	
}
