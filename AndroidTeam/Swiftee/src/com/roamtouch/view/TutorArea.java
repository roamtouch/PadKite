package com.roamtouch.view;

import java.util.ArrayList;
import java.util.Set;

import com.roamtouch.swiftee.BrowserActivity;
import com.roamtouch.gestures.Gesture;
import com.roamtouch.gestures.GestureLibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class TutorArea extends LinearLayout implements OnClickListener {

	private GestureLibrary mLibrary;
	private Context mContext;
	private BrowserActivity parent;
	private int gestureCount;
	private Object str[];
	
	public TutorArea(Context context, AttributeSet attrs) {
		super(context, attrs);	
		mContext = context;
	}
	public void setGestureLibrary(GestureLibrary l) {
		mLibrary = l;
		initView();
	}
	
    private static final float BITMAP_RENDERING_WIDTH = 4;

    private static final boolean BITMAP_RENDERING_ANTIALIAS = true;
    private static final boolean BITMAP_RENDERING_DITHER = true;
	
	private Bitmap toBitmap(Gesture gesture, int width, int height, int inset, int color)
	{
		final Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        final Paint paint = new Paint();
        paint.setAntiAlias(BITMAP_RENDERING_ANTIALIAS);
        paint.setDither(BITMAP_RENDERING_DITHER);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(BITMAP_RENDERING_WIDTH);

        final Path path = gesture.toPath();
        final RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        final float sx = (width - 2 * inset) / bounds.width();
        final float sy = (height - 2 * inset) / bounds.height();
        final float scale = sx > sy ? sy : sx;
        paint.setStrokeWidth(BITMAP_RENDERING_WIDTH / scale);

        path.offset(-bounds.left + (width - bounds.width() * scale) / 2.0f,
                -bounds.top + (height - bounds.height() * scale) / 2.0f);

        canvas.translate(inset, inset);
        canvas.scale(scale, scale);

        canvas.drawPath(path, paint);

        return bitmap;	
	}
	
	private void initView(){
		this.removeAllViews();
		Set<String> s=mLibrary.getGestureEntries();
		str = s.toArray();
		gestureCount = str.length;
		
		LayoutParams params=new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		for(int i=0;i<gestureCount;i++){
			Button b=new Button(mContext);
			b.setId(i);
			b.setLayoutParams(params);
			b.setText(str[i].toString());
			ArrayList<Gesture> list = mLibrary.getGestures(str[i].toString());
			Bitmap bit = toBitmap(list.get(0), 50, 50, 0, Color.BLACK);
			BitmapDrawable d = new BitmapDrawable(bit);
			b.setCompoundDrawablesWithIntrinsicBounds(null,d, null, null);
			b.setOnClickListener(this);
			this.addView(b);
		}
	}
	public void onClick(View v) {
			ArrayList<Gesture> list = mLibrary.getGestures(str[v.getId()].toString());			
			parent.drawGestureToEducate(list.get(0), str[v.getId()].toString());
			
	}
	public void setParent(BrowserActivity parent) {
		this.parent = parent;
	}
	
}
