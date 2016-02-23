package org.csitebooks.convertbytearray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	
	byte[] data =null;
//	String path;
	 VideoView videoView;
	 private Context context;
	 Button b1,b2,play;
	 String root;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 root = Environment.getExternalStorageDirectory()+ File.separator +"ConvertByteArray/";
        
        File myDir = new File(root);
        
        if (!myDir.exists()) {
        	myDir.mkdir();
        }
		
//		Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

		
		
		
		
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		play = (Button) findViewById(R.id.button3);
		
		b2.setEnabled(false);
		play.setEnabled(false);
		
		videoView = (VideoView) findViewById(R.id.videoView1);
		File file=new File(root+"small.mp4");
		
		context = this;
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				videoView.setVideoPath(root+"video.mp4");
				videoView.setMediaController(new MediaController(context));
				videoView.start();
				play.setEnabled(false);
				b1.setEnabled(true);
			}
		});
		
		
		
		BufferedOutputStream bufEcrivain=null;
		try {
			bufEcrivain = new BufferedOutputStream((new FileOutputStream(file)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream VideoReader = new BufferedInputStream(getResources().openRawResource(R.raw.small));
		byte[] buff = new byte[32 * 1024];
		int len;
		try {
			while( (len = VideoReader.read(buff)) > 0 ){
			    bufEcrivain.write(buff,0,len);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bufEcrivain.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bufEcrivain.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convertIntoByteArray(root+"small.mp4");
				b2.setEnabled(true);
				b1.setEnabled(false);
			}
		});
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				try {
					convertIntoFile(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(Environment.isExternalStorageRemovable())
				{
				  // yes SD-card is present
//					root = root;
					
					Toast.makeText(getApplicationContext(), "Video Saved on SD card", Toast.LENGTH_LONG).show();
				}
				else
				{
				 // Sorry
//					root = root;
					Toast.makeText(getApplicationContext(), "Video Saved on SD card", Toast.LENGTH_LONG).show();
				}
				
				play.setEnabled(true);
				b2.setEnabled(false);
				
			}
		});
		
		
		
		}
	private String convertIntoByteArray(String pathOnSdCard) {
		// TODO Auto-generated method stub
		String strFile=null;
		File file=new File(pathOnSdCard);
		try {
		 data = FileUtils.readFileToByteArray(file);//Convert any file, image or video into byte array
		strFile = Base64.encodeToString(data, Base64.NO_WRAP);//Convert byte array into string
		} catch (IOException e) {
		e.printStackTrace();
		}
		return strFile;
	}
	
	private void convertIntoFile(byte[] videoData) throws IOException {
		FileOutputStream out = new FileOutputStream(root+"video.mp4");
		out.write(videoData);
		out.close();
	}
}
