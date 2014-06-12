package com.dons.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomePage extends Activity {

	public static Uri currImageURI;
	public static String path;	
	private static int CAMERA_CAPTURE_IMAGE = 0;
    private static int RESULT_LOAD_IMAGE = 1;   
    int seconds;    
	ImageButton gallery,camera,about;
	public static Bitmap photo;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  	    //Remove title bar
 	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

 	    //Remove notification bar
 	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_page);

        final GifMovieView gif1 = (GifMovieView) findViewById(R.id.gif1);
		gif1.setMovieResource(R.drawable.welcome);
        
        
        gallery=(ImageButton)findViewById(R.id.imageButton1);
        gallery.setBackgroundColor(Color.TRANSPARENT);
        
        camera=(ImageButton)findViewById(R.id.imageButton2);
        camera.setBackgroundColor(Color.TRANSPARENT);

        
        camera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
			}
		});
        
        gallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startCameraActivity();
			}
		});        
        
    }

	public void onGifClick(View v) {
		GifMovieView gif = (GifMovieView) v;
		gif.setPaused(!gif.isPaused());
	}
	
 	protected void startCameraActivity(){
    	
		Calendar c = Calendar.getInstance(); 
        seconds = c.get(Calendar.SECOND);
    	File file = new File(Environment.getExternalStorageDirectory()+"/"+seconds+".jpg");
    	path=Environment.getExternalStorageDirectory()+"/"+seconds+".jpg";
    	currImageURI= Uri.fromFile( file ); 	
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
     	intent.putExtra( MediaStore.EXTRA_OUTPUT,currImageURI); 	
    	startActivityForResult( intent, CAMERA_CAPTURE_IMAGE );
    }
   
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    
        
         if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                    // currImageURI is the global variable I'm using to hold the content:// URI of the image
            	
           	         currImageURI= data.getData();
           	         path=getRealPathFromURI(currImageURI);
//                 	 Intent toEdit=new Intent(HomePage.this,EditAction.class);
//    				 startActivity(toEdit);
           	      Intent i=new Intent(HomePage.this,EditAction.class);
          		  startActivity(i);

            }
    }
         

         if(requestCode==0 && resultCode==-1){
         	
         	switch( resultCode ){
     		case 0: Toast.makeText(getApplicationContext(),"User Cancelled", Toast.LENGTH_SHORT).show();
     			break;
     		case -1:
     			 Intent toEdit=new Intent(HomePage.this,EditAction.class);
				 startActivity(toEdit);
     			   
    			break;
      	}
         }             
         
    } 

//---------------------------------------------------------------------------------------------------------------------------------------------------
  
    	// And to convert the image URI to the direct file system path of the image file
    	public String getRealPathFromURI(Uri contentUri) {

    	        // can post image
    	        String [] proj={MediaStore.Images.Media.DATA};
    	        Cursor cursor = managedQuery( contentUri,
    	                        proj, // Which columns to return
    	                        null,       // WHERE clause; which rows to return (all rows)
    	                        null,       // WHERE clause selection arguments (none)
    	                        null); // Order-by clause (ascending by name)
    	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	        cursor.moveToFirst();

    	        return cursor.getString(column_index);
    	}
    	
//---------------------------------------------------------------------------------------------------------------------------------------------------

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        menu.clear();
        return true;
    }
    
}
