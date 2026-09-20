package com.example.grandmaclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import java.util.Calendar;
import java.util.Locale;

public class ClockView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Calendar calendar = Calendar.getInstance();

    public ClockView(Context context) { super(context); paint.setTypeface(Typeface.create("sans", Typeface.NORMAL)); setBackgroundColor(0xFF000000); }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2f, cy = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) * 0.40f;

        paint.setStyle(Paint.Style.STROKE); paint.setColor(0xFFFFFFFF); paint.setStrokeWidth(Math.max(3f, radius * 0.012f));
        canvas.drawCircle(cx, cy, radius, paint);

        for (int i = 0; i < 60; i++) {
            double a = Math.toRadians(i * 6 - 90);
            float outer = radius * 0.97f;
            float inner = radius * (i % 5 == 0 ? 0.89f : 0.94f);
            paint.setStrokeWidth(i % 5 == 0 ? Math.max(5f, radius * 0.018f) : Math.max(2f, radius * 0.007f));
            canvas.drawLine(cx + (float)Math.cos(a)*inner, cy + (float)Math.sin(a)*inner,
                    cx + (float)Math.cos(a)*outer, cy + (float)Math.sin(a)*outer, paint);
        }

        paint.setStyle(Paint.Style.FILL); paint.setTypeface(Typeface.create("sans", Typeface.BOLD));
        paint.setTextSize(radius * 0.16f); paint.setTextAlign(Paint.Align.CENTER);
        String[] nums={"12","1","2","3","4","5","6","7","8","9","10","11"};
        for(int i=0;i<12;i++){
            double a=Math.toRadians(i*30-90);
            float nr=radius*0.76f;
            float y=cy+(float)Math.sin(a)*nr-(paint.ascent()+paint.descent())/2f;
            canvas.drawText(nums[i],cx+(float)Math.cos(a)*nr,y,paint);
        }

        calendar.setTimeInMillis(System.currentTimeMillis());
        float h=calendar.get(Calendar.HOUR); float m=calendar.get(Calendar.MINUTE); float s=calendar.get(Calendar.SECOND); float ms=calendar.get(Calendar.MILLISECOND);
        drawHand(canvas,cx,cy,radius*0.48f,(h+m/60f)*30f-90,Math.max(8f,radius*0.045f),0xFFFFFFFF);
        drawHand(canvas,cx,cy,radius*0.68f,(m+s/60f)*6f-90,Math.max(5f,radius*0.025f),0xFFFFFFFF);
        drawHand(canvas,cx,cy,radius*0.74f,(s+ms/1000f)*6f-90,Math.max(2f,radius*0.009f),0xFFFF3333);
        paint.setColor(0xFFFF3333); canvas.drawCircle(cx,cy,Math.max(7f,radius*0.025f),paint);
        postInvalidateDelayed(100);
    }

    private void drawHand(Canvas c,float cx,float cy,float len,float deg,float width,int color){
        double a=Math.toRadians(deg); float endX=cx+(float)Math.cos(a)*len, endY=cy+(float)Math.sin(a)*len;
        paint.setStyle(Paint.Style.STROKE); paint.setStrokeCap(Paint.Cap.ROUND); paint.setStrokeWidth(width); paint.setColor(color);
        c.drawLine(cx,cy,endX,endY,paint);
    }
}
