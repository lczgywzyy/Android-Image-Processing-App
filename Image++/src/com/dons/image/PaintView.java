package com.dons.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PaintView extends View{

	Paint mPaint;
	Bitmap mBitmap;
	Matrix mMatrix;
	RectF mSrcRectF;
	RectF mDestRectF;
	boolean mPause;
	
	public PaintView(Context context,AttributeSet attributeSet){
		super(context,attributeSet);
		
		mPaint = new Paint();
		mMatrix = new Matrix();
		mSrcRectF = new RectF();
		mDestRectF = new RectF();
		mPause = false;		
	}
	
	public void addBitmap(Bitmap bitmap){
		mBitmap = bitmap;
	}
	
	public Bitmap getBitmap(){
		return mBitmap;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);
		
		if(!mPause){		
			if(mBitmap!=null){
				
				// Setting size of Source Rect
				mSrcRectF.set(0, 0,mBitmap.getWidth(),mBitmap.getHeight());
				
				// Setting size of Destination Rect
				mDestRectF.set(0, 0, getWidth(), getHeight());
				
				// Scaling the bitmap to fit the PaintView
				mMatrix.setRectToRect( mSrcRectF , mDestRectF, ScaleToFit.CENTER);
				
				// Drawing the bitmap in the canvas
				canvas.drawBitmap(mBitmap, mMatrix, mPaint);
			}			
			
			// Redraw the canvas
			invalidate();
		}
	}
	
	// Pause or resume onDraw method
	public void pause(boolean pause){
		mPause = pause;
	}	
}