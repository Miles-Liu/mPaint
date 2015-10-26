package com.milessoft.mpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class BrushView extends View {

	private Paint brush = new Paint();
	public Path path = new Path();
	public LayoutParams params;
	public LayoutParams params2;
	private ColorPickerDialog dialog;
	public float strokeWidth;

	Context context;

	public BrushView(final Context context) {
		super(context);
		strokeWidth = 5f;
		brush.setAntiAlias(true);
		brush.setColor(Color.BLUE);
		brush.setStyle(Paint.Style.STROKE);
		brush.setStrokeJoin(Paint.Join.ROUND);
		brush.setStrokeWidth(strokeWidth);
	}

	public void addStrokeWidth() {
		strokeWidth ++;
		brush.setStrokeWidth(strokeWidth);
	}

	public void minusStrokeWidth() {
		strokeWidth --;
		if (strokeWidth < 0) strokeWidth = 1f;
		brush.setStrokeWidth(strokeWidth);
	}
	public void chooseColor(Context context) {

		dialog = new ColorPickerDialog(context,
				new ColorPickerDialog.OnColorChangedListener() {

					@Override
					public void colorChanged(int color) {
						brush.setColor(color);
					}
				}, Color.BLUE);
		dialog.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pointX = event.getX();
		float pointY = event.getY();
		// Checks for the event that occurs
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(pointX, pointY);
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(pointX, pointY);
			break;
		default:
			return false;
		}
		// Force a view to draw again
		postInvalidate();
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, brush);
	}
}
