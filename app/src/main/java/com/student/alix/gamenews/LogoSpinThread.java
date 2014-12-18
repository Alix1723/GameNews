package com.student.alix.gamenews;

/**
 * Created by Alix on 18/12/2014. (Created by rla on 29/10/2014.)
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class LogoSpinThread extends Thread{

    private int canvasWidth;
    private int canvasHeight;
    private float xPos = 0.0f;
    private float yPos = 0.0f;
    private float cycle;

    private float HalfAppletHeight;
    private float HalfAppletWidth;

    private boolean first = true;
    private boolean run = false;

    private SurfaceHolder shLogoSurface;
    private Paint paintLogo;
    private LogoSpinSurfaceView logoview;
    private Context thisContext;

    public LogoSpinThread(SurfaceHolder surfaceHolder, LogoSpinSurfaceView bioSurfV, Context context) {
        this.shLogoSurface = surfaceHolder;
        this.logoview = bioSurfV;
        paintLogo = new Paint();
        this.thisContext = context;
    }

    public void doStart() {
        synchronized (shLogoSurface) {
            first = false;
        }
    }

    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = shLogoSurface.lockCanvas(null);
                synchronized (shLogoSurface) {
                    svDraw(c);
                }
            } finally {
                if (c != null) {
                    shLogoSurface.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        run = b;
    }
    public void setSurfaceSize(int width, int height) {
        synchronized (shLogoSurface) {
            canvasWidth = width;
            canvasHeight = height;
            HalfAppletHeight = canvasHeight / 2;
            HalfAppletWidth  = canvasWidth / 2;
            doStart();
        }
    }

    private void svDraw(Canvas canvas) {
        if(run) {
            canvas.save();
            canvas.restore();
            canvas.drawColor(Color.BLACK);
            paintLogo.setStyle(Paint.Style.FILL);
            drawAxes(canvas);
            paintLogo.setStyle(Paint.Style.FILL);
            drawLogo(canvas);
            cycle = (cycle + 0.015f) % 360.0f; //Roughly 1/60th
        }
    }

    public void drawAxes(Canvas theCanvas)
    {
        paintLogo.setColor(Color.RED);
        theCanvas.drawLine(0,HalfAppletHeight,30*HalfAppletWidth,HalfAppletHeight, paintLogo); // Horizontal X Axes
        theCanvas.drawLine(15* HalfAppletWidth,0,15* HalfAppletWidth,canvasHeight, paintLogo); // Vertical Y Axes
    }

    //Draw the bitmap for the GameNews logo, and also do any rotation/scaling/translation here
    public void drawLogo(Canvas theCanvas)
    {
        Resources res = thisContext.getResources();
        Bitmap bitmapLogo = BitmapFactory.decodeResource(res,R.drawable.gn_app_title_image);

        Matrix bitmapMatrix = new Matrix();
        bitmapMatrix.postTranslate(-bitmapLogo.getWidth()/2,-bitmapLogo.getHeight()/2); //Move the centre of the bitmap to canvas 0,0
        bitmapMatrix.postScale(1.5f,1.5f); //Scale it up a bit
        bitmapMatrix.postRotate(cycle*550); //Rotate it
        bitmapMatrix.postTranslate(HalfAppletWidth + (float)Math.cos(cycle)*100.0f,HalfAppletHeight+ (float)Math.sin(cycle)*100.0f); //Move it to the centre of the canvas

        theCanvas.drawBitmap(bitmapLogo,bitmapMatrix,paintLogo);
    }
}