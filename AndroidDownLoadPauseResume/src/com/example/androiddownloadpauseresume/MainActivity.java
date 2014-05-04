package com.example.androiddownloadpauseresume;

import java.net.MalformedURLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	DownloadTaskListener taskListener;
	DownloadTask mDownloadTask;
	ProgressBar progressBar1;
	String url = "http://img.yingyonghui.com/apk/16457/com.rovio.angrybirdsspace.ads.1332528395706.apk";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void pauseDownLoad(View view)
	{
		mDownloadTask.cancel(true);
	}
	
	public void resumeDownload(View view)
	{
		 try {
			mDownloadTask = newDownloadTask(url);
			mDownloadTask.execute();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	public void download(View view)
	{
		Log.e("test","downloading file ......");
		
		try {
			 mDownloadTask = newDownloadTask(url);
			mDownloadTask.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DownloadTask newDownloadTask(String url) throws MalformedURLException {
        taskListener = new DownloadTaskListener() {
            @Override
            public void updateProcess(DownloadTask task) {
            	Log.e("test","downloadSize="+task.getDownloadSize());
                Log.e("test","downloadPercent="+task.getDownloadPercent());
                progressBar1.setProgress((int)task.getDownloadPercent());
            }

            @Override
            public void preDownload(DownloadTask task) {
                Log.e("test","download task listener preDownload");
            }

            @Override
            public void finishDownload(DownloadTask task) {
            	Log.e("test","download task listener finishDownload");
                
            }

            @Override
            public void errorDownload(DownloadTask task, Throwable error) {

                if (error != null) {
                    Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }
        };
        return new DownloadTask(this, url, StorageUtils.FILE_ROOT, taskListener);
    }

}
