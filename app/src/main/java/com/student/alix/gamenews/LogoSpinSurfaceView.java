package com.student.alix.gamenews;

    import android.content.Context;
    import android.view.SurfaceHolder;
    import android.view.SurfaceView;

/**
 * LogoSpinSurfaceView
 * (Code duplicated from Labs by Bobby Law)
 */

public class LogoSpinSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    //SurfaceHolder for the canvas
    private SurfaceHolder shLogoSurface;

    //Thread for drawing with
    LogoSpinThread drawingThread = null;

    //Create the view
    public LogoSpinSurfaceView(Context context)
    {
        super(context);
        shLogoSurface = getHolder();
        shLogoSurface.addCallback(this);
        drawingThread = new LogoSpinThread(getHolder(), this,getContext());
        setFocusable(true);
    }

    public LogoSpinThread getThread()
    {
        return drawingThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        drawingThread.setRunning(true);
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        drawingThread.setSurfaceSize(width,height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawingThread.setRunning(false);
        while(retry)
        {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}