package com.dons.image;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import com.dons.image.R;
import com.dons.imageprocessing.ImageProcessing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.dons.drawer.NavDrawerItem;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint("NewApi")
public class EditAction extends Activity{

	PaintView mPaintView;
	int mWidth;
	int mHeight;
    private ProgressDialog pDialog;
    public static Bitmap bitmap;
    public static Bitmap processedImage;   
    public static Bitmap copyImage;  
    public static int flag=0;    
    ImageButton original,grayscale,reflection,snow,highlight,flea,blur,emboss,engrave; 
    public static final int progress_bar_type = 0;   // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_spinner =1;   // Progress dialog type (0 - for Horizontal progress bar)
    public SeekBar seekBar;
    public Handler myHandler; 
    
    
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
   		
	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_action);	
        
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        seekBar.setVisibility(View.INVISIBLE);
        
        original=(ImageButton)findViewById(R.id.imageButton1);
        original.setBackgroundColor(Color.TRANSPARENT);
        grayscale=(ImageButton)findViewById(R.id.imageButton2);
        grayscale.setBackgroundColor(Color.TRANSPARENT);
        reflection=(ImageButton)findViewById(R.id.imageButton3);
        reflection.setBackgroundColor(Color.TRANSPARENT);
        snow=(ImageButton)findViewById(R.id.imageButton4);
        snow.setBackgroundColor(Color.TRANSPARENT);
        highlight=(ImageButton)findViewById(R.id.imageButton5);
        highlight.setBackgroundColor(Color.TRANSPARENT);
        flea=(ImageButton)findViewById(R.id.imageButton6);
        flea.setBackgroundColor(Color.TRANSPARENT);
        blur=(ImageButton)findViewById(R.id.imageButton7);
        blur.setBackgroundColor(Color.TRANSPARENT);
        emboss=(ImageButton)findViewById(R.id.imageButton8);
        emboss.setBackgroundColor(Color.TRANSPARENT);
        engrave=(ImageButton)findViewById(R.id.imageButton9);
        engrave.setBackgroundColor(Color.TRANSPARENT);
        
	    mWidth = mHeight = 0;		
		 //Getting reference to PaintView
		mPaintView = (PaintView) findViewById(R.id.paintView1);
		
		new ToRunInBackground().execute(HomePage.path);	
		
		mPaintView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				seekBar.setVisibility(View.INVISIBLE);
			}
		});
		
		original.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ProcessImageInBackground().execute("original");
			}
		});
		
		grayscale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ProcessImageInBackground().execute("grayscale");
			}
		});

		
		reflection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ProcessImageInBackground().execute("reflection");
			}
		});
		
		snow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ProcessImageInBackground().execute("snow");
			}
		});
		
		highlight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ProcessImageInBackground().execute("highlight");
			}
		});
	
		flea.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		    	new ProcessImageInBackground().execute("flea");
		}
	});
		
		blur.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    	new ProcessImageInBackground().execute("blur");
			}
		});
   
		emboss.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    	new ProcessImageInBackground().execute("emboss");
			}
		});

		engrave.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    	new ProcessImageInBackground().execute("engrave");
			}
		});
		
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

	}
	
	public void doUpdate(int value) {
		System.out.println("-----------");
		Bitmap newBM=ImageProcessing.applyContrast(bitmap,value);
		 mPaintView.addBitmap(newBM);
	}
	
	@Override
	public void onResume() {		
		mPaintView.pause(false);		// Resume repainting
		mPaintView.invalidate();  	
		super.onResume();
	}
	
	@Override
	public void onPause() {
		
		// Pause repainting
		mPaintView.pause(true);		
		super.onPause();
		
	}	

//--------------------------------------------------------------------------------------------------------------------------------------- 		
	
	private class ToRunInBackground extends AsyncTask<String, String, Bitmap>
	{		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){	         	
				    dismissDialog(progress_bar_type);
	        	    mPaintView.addBitmap(result);    
	         }
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	        
			showDialog(progress_bar_type);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(flag==0)
			{
				BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inSampleSize = 3;
			    bitmap= BitmapFactory.decodeFile(params[0],options);	
			    flag=1;
			    copyImage=bitmap;
					
			}
			

			return bitmap;
		}
		
	}	

	
//--------------------------------------------------------------------------------------------------------------------------------------- 		

		private class ProcessImageInBackground extends AsyncTask<String, String,Bitmap>
		{		
			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);	
			        if(result!=null)
			        {
						dismissDialog(progress_bar_spinner);
		        	    mPaintView.addBitmap(processedImage);  	
			        }
  
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();		        
				showDialog(progress_bar_spinner);
			}

			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			@Override
			protected Bitmap doInBackground(String... params) {
				// TODO Auto-generated method stub
				ImageProcessing processImage=new ImageProcessing();
				String token=params[0];
				if(token.equals("original"))
				{
				processedImage=processImage.applyNormalImage(bitmap);	
				}
				else if(token.equals("invert"))
				{
				processedImage=processImage.applyInvert(bitmap);	
				}
				else if(token.equals("grayscale"))
				{
				processedImage=processImage.applyGreyscale(bitmap);	
				}
				else if(token.equals("reflection"))
				{
				processedImage=processImage.applyReflection(bitmap);	
				}
				else if(token.equals("snow"))
				{
				processedImage=processImage.applySnowEffect(bitmap);	
				}
				else if(token.equals("highlight"))
				{
				processedImage=processImage.applyHighlightImage(bitmap);
				}
				else if(token.equals("flea"))
				{
				processedImage=processImage.applyFleaEffect(bitmap);
				}
				else if(token.equals("blur"))
				{
				processedImage=processImage.applyGaussianBlur(bitmap);
				}
				else if(token.equals("emboss"))
				{
				processedImage=processImage.applyEmboss(bitmap);
				}
				else if(token.equals("engrave"))
				{
				processedImage=processImage.applyEngrave(bitmap);
				}
				return processedImage;
			}
			
		}
		
		
//--------------------------------------------------------------------------------------------------------------------------------------- 		
		//--------------------------------------------------------------------------------------------------------------------------------------- 		

				private class ProcessImageWithSeekBarInBackground extends AsyncTask<String, String,Bitmap>
				{		
					@Override
					protected Bitmap doInBackground(String... params) {
						// TODO Auto-generated method stub
						ImageProcessing processImage=new ImageProcessing();
						String token=params[0];
						if(token.equals("contrast"))
						{
						processedImage=processImage.applyContrast(bitmap,Integer.parseInt(params[1])*50);	
						}					
						return processedImage;
					}

					@Override
					protected void onPostExecute(Bitmap result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						mPaintView.addBitmap(result);					}
					
				}
				
				
//--------------------------------------------------------------------------------------------------------------------------------------- 		
			
		@Override
		protected Dialog onCreateDialog(int id) {
		    switch (id) {
		    case progress_bar_type:
		        pDialog = new ProgressDialog(this);
		        pDialog.setMessage("Loading Image. . .Please Wait. . .");
		        pDialog.setIndeterminate(true);
		        pDialog.setProgressNumberFormat(null);
		        pDialog.setProgressPercentFormat(null);
		        //pDialog.setMax(100);
		        pDialog.setCancelable(false);
		        pDialog.setCanceledOnTouchOutside(false);
		        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		        pDialog.setCancelable(true);
		        pDialog.show();
		        return pDialog;
		       
		    case progress_bar_spinner:		    	 
		    	  pDialog = new ProgressDialog(this);
				  pDialog.setMessage("Processing Image :P ");
				  pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				  pDialog.setIndeterminate(true);
				  pDialog.setCancelable(false);
			      pDialog.setCanceledOnTouchOutside(false);
				  pDialog.show();
				  return pDialog;
		    default:
		        return null;
		    }
		}
		
//--------------------------------------------------------------------------------------------------------------------------------------- 		
		
		// Navigation Drawer in Action
		
//--------------------------------------------------------------------------------------------------------------------------------------- 		
		
		
		/**
		 * Slide menu item click listener
		 * */
		private class SlideMenuClickListener implements
				ListView.OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// display view for selected nav drawer item
				//displayView(position);
				if(position==1)
				{
					//System.out.println("---------------------------------------------------");
					//Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
					Calendar c = Calendar.getInstance(); 
		        	int seconds = c.get(Calendar.SECOND);

		        	File f3=new File(Environment.getExternalStorageDirectory()+"/image/");
		    		if(!f3.exists()){
		    			f3.mkdirs();
		    		}
		    		OutputStream outStream = null;
		            File file = new File(Environment.getExternalStorageDirectory() + "/image/"+seconds+".png");
	            
		            try {
		            outStream = new FileOutputStream(file);
		            processedImage.compress(Bitmap.CompressFormat.PNG, 85, outStream);
		            outStream.flush();
		            outStream.close();

		            //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

		           } catch (Exception e) {}
		            mDrawerLayout.closeDrawer(mDrawerList);
					bluetoothFileTransfer(Environment.getExternalStorageDirectory() + "/image/"+seconds+".png");
					
				}
			}
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_edit_action, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// toggle nav drawer on selecting action bar app icon/title
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}
			// Handle action bar actions click
			switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}

		/* *
		 * Called when invalidateOptionsMenu() is triggered
		 */
		@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
			// if nav drawer is opened, hide the action items
			boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
			return super.onPrepareOptionsMenu(menu);
		}

		/**
		 * Diplaying fragment view for selected nav drawer list item
		 * */
		private void displayView(int position) {
			// update the main content by replacing fragments
			Fragment fragment = null;
			switch (position) {
			case 0:
				//fragment = new HomeFragment();
				break;
			case 1:
				//fragment = new FindPeopleFragment();		
				break;
			case 2:
				//fragment = new PhotosFragment();
				break;
			case 3:
				//fragment = new CommunityFragment();
				break;
			case 4:
				//fragment = new PagesFragment();
				break;
			case 5:
				//fragment = new WhatsHotFragment();
				break;

			default:
				break;
			}

			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(position, true);
				mDrawerList.setSelection(position);
				setTitle(navMenuTitles[position]);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				// error in creating fragment
				Log.e("MainActivity", "Error in creating fragment");
			}
		}

		@Override
		public void setTitle(CharSequence title) {
			mTitle = title;
			getActionBar().setTitle(mTitle);
		}

		/**
		 * When using the ActionBarDrawerToggle, you must call it during
		 * onPostCreate() and onConfigurationChanged()...
		 */

		@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			super.onPostCreate(savedInstanceState);
			// Sync the toggle state after onRestoreInstanceState has occurred.
			mDrawerToggle.syncState();
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			// Pass any configuration change to the drawer toggls
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
		
		
		
		public void bluetoothFileTransfer(String path) {
  			BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
  			
  			if (btAdapter == null) {
  				//showAlertMessage("Oops","No Bluetooth Support");
  			 }
  			else
  			{
  				Intent intent = new Intent();  			
  				intent.setAction(Intent.ACTION_SEND);			
  				intent.setType("image/jpeg");		
  				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)) );
  				startActivity(intent);
			}
  			
		}


	}



