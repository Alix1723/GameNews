package com.student.alix.gamenews;

/**
 * Created by Alix on 18/12/2014. (Created by rla on 29/10/2014.)
 */
        import android.content.Context;
        import android.os.Bundle;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;

public class LogoSpinSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder shBioSurface;

    LogoSpinThread drawingThread = null;

    public LogoSpinSurfaceView(Context context)
    {
        super(context);
        draw();
        setFocusable(true);
    }

    public void draw()
    {
        shBioSurface = getHolder();
        shBioSurface.addCallback(this);
        drawingThread = new LogoSpinThread(getHolder(), this,getContext());
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