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
	private Path path = new Path();
	public Button btnEraseAll;
	public Button btnColor;
	public LayoutParams params;
	public LayoutParams params2;
	private ColorPickerDialog dialog;


	public BrushView(final Context context) {
		super(context);
		brush.setAntiAlias(true);
		brush.setColor(Color.BLUE);
		brush.setStyle(Paint.Style.STROKE);
		brush.setStrokeJoin(Paint.Join.ROUND);
		brush.setStrokeWidth(10f);
//		 params = new LayoutParams(400 ,
//		 LayoutParams.WRAP_CONTENT);
		params = new LayoutParams(new LinearLayout.LayoutParams(100,
                LinearLayout.LayoutParams.WRAP_CONTENT));

		btnEraseAll = new Button(context);
		btnEraseAll.setText("Erase Everything!!");
		 btnEraseAll.setLayoutParams(params);
		
		btnEraseAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// reset the path
				path.reset();
				// invalidate the view
				postInvalidate();

			}
		});
//		 params2 = new LayoutParams(40,40);
//		 LayoutParams.WRAP_CONTENT);
		params2 = new LayoutParams(new LinearLayout.LayoutParams(100,
                LinearLayout.LayoutParams.WRAP_CONTENT));
		btnColor = new Button(context);
		btnColor.setText("Choose Color!!");
		 btnColor.setLayoutParams(params2);

		btnColor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				dialog = new ColorPickerDialog(context,
						new ColorPickerDialog.OnColorChangedListener() {

							@Override
							public void colorChanged(int color) {
								brush.setColor(color);
							}
						}, Color.BLUE);
				dialog.show();

			}
		});
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
