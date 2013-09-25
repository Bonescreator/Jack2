package com.example.jack;

import com.example.jack.model.Boomerang;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
	SurfaceHolder.Callback
{
	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Boomerang boom;
	
	public MainGamePanel (Context context)
	{
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		// create boomerang and load bitmap
		boom = new Boomerang(BitmapFactory.decodeResource(getResources(), 
				R.drawable.droid_1), 50, 50);
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		// the surface is created; we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		Log.d(TAG, "Trying to shut down thread.");
		while (retry)
		{
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e)
			{
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread has been shut down.");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			// delegating event handling to the boomerang
			boom.handleActionDown((int)event.getX(), (int)event.getY());
			
			if (event.getY() > getHeight() - 50)
			{
				thread.setRunning(false);
				((Activity)getContext()).finish();
			}
			else
			{
				Log.d(TAG, "Coords: x=" + event.getX() + ", y=" + event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			// the gestures
			if (boom.isTouched())
			{
				// the boomerang is being dragged
				boom.setX((int)event.getX());
				boom.setY((int)event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			// not being touched any more
			if (boom.isTouched())
			{
				boom.setTouched(false);
			}
		}
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// fill the canvas with a solid color
		canvas.drawColor(Color.GRAY);
		boom.draw(canvas);
	}
}
