package ro.softwareinclude.custom_progress_dialog_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Sebastian Manolescu on 27.08.2014.
 */
public class DrawingView extends View {

    private static final int INVALIDATE_VIEW = 0;

    private int arcDrawValue = 0;
    private double progressValue = 0;

    private int screenWidth = 0;
    private int screenHeight = 0;

    private Context context;
    private Handler dataEventHandler;

    //progress properties
    private boolean showSecondCircle = false;
    private String arcColor;
    private String innerArcColor;

    public DrawingView(Context context) {
        super(context);
        this.context = context;
    }

    public DrawingView(Context context,boolean showSecondCircle, String arcColor, String innerArcColor) {
        super(context);
        this.context = context;
        this.showSecondCircle = showSecondCircle;
        this.arcColor = arcColor;
        this.innerArcColor = innerArcColor;
    }

    public void displayProgress() {
        getScreenSize();
        progressHandler();
        progressInit();
    }


    private void getScreenSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }


    /**
     * Handle events and update UI progress on screen
     */
    private void progressHandler(){
        dataEventHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int sentInt = msg.what;
                switch (sentInt) {

                    case INVALIDATE_VIEW:{
                            invalidate();
                        break;
                    }
                }
            }
        };
    }

    /**
     *Progress increase
     * Thread.sleep(50); - DEMO PROGRESS (ex: file size will replace the sleep)
     */
    private void progressInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(arcDrawValue<360){
                    arcDrawValue++;
                    progressValue = (arcDrawValue)/3.6;
                    try {
                        Thread.sleep(50);
                        dataEventHandler.sendMessage(Message.obtain(dataEventHandler,
                                INVALIDATE_VIEW, null));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Draw arc (progressbar)
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(18);
        paint.setColor(Color.parseColor(arcColor));
        RectF rectF = new RectF(screenWidth/2-100, screenHeight/2-100, screenWidth/2+100, screenHeight/2+100);
        canvas.drawArc(rectF, -90, arcDrawValue, false, paint);

        if(showSecondCircle) {
            //Draw arc (progressbar)
            Paint paintInnter = new Paint();
            paintInnter.setAntiAlias(true);
            paintInnter.setFlags(Paint.ANTI_ALIAS_FLAG);
            paintInnter.setStyle(Paint.Style.STROKE);
            paintInnter.setStrokeWidth(18);
            paintInnter.setColor(Color.parseColor(innerArcColor));
            RectF rectFInner = new RectF(screenWidth / 2 - 80, screenHeight / 2 - 80, screenWidth / 2 + 80, screenHeight / 2 + 80);
            canvas.drawArc(rectFInner, -90, arcDrawValue, false, paintInnter);
        }


        //Draw text (percentage value)
        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setLinearText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        PointF p = getTextCenterToDraw(String.valueOf((int)progressValue+"%"), rectF, textPaint);
        canvas.drawText(String.valueOf((int)progressValue+"%"), rectF.centerX(), p.y, textPaint);

    }

    /**
     * Center text vertical in the progress animation
     * @param text
     * @param region
     * @param paint
     * @return
     */
    public static PointF getTextCenterToDraw(String text, RectF region, Paint paint) {
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float x = region.centerX() - textBounds.width() * 0.4f;
        float y = region.centerY() + textBounds.height() * 0.4f;
        return new PointF(x, y);
    }
}
