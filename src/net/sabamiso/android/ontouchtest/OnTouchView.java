package net.sabamiso.android.ontouchtest;

import net.sabamiso.java.utils.FPSCounter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchView extends View {

	boolean mousePressed = false;
	float mouseX, mouseY;

	FPSCounter fps = new FPSCounter();

	Paint paint_circle;
	Paint paint_text;

	float max_fps;

	public OnTouchView(Context context) {
		super(context);

		paint_circle = new Paint();
		paint_circle.setColor(Color.GREEN);

		paint_text = new Paint();
		paint_text.setTextSize(50);
		paint_text.setAntiAlias(true);
		paint_text.setColor(Color.BLACK);

	}

	protected void onDraw(Canvas canvas) {

		if (max_fps < fps.fps())
			max_fps = fps.fps();

		if (mousePressed) {
			canvas.drawCircle(mouseX, mouseY, 100, paint_circle);
			canvas.drawText(
					String.format("mouseX=%.1f, mouseY=%.1f", mouseX, mouseY),
					20, 60, paint_text);
			canvas.drawText(
					String.format("fps=%.2f, max_fps=%.2f", fps.fps(), max_fps),
					20, 120, paint_text);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		mouseX = evt.getX();
		mouseY = evt.getY();

		int action = evt.getAction();

		switch (action) {
		case android.view.MotionEvent.ACTION_DOWN:
			mousePressed = true;
			fps.clear();
			max_fps = 0.0f;
			invalidate();
			break;
		case android.view.MotionEvent.ACTION_UP:
			mousePressed = false;
			fps.clear();
			max_fps = 0.0f;
			invalidate();
			break;
		case android.view.MotionEvent.ACTION_MOVE:
			fps.check();
			invalidate();
			break;
		}
		return true;
	}
}
