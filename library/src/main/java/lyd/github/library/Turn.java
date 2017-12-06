package lyd.github.library;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by shawn on 17/11/29.
 */

public class Turn {

    /**
     * 绘制范围
     */
    private RectF rectF;
    /**
     * 半径
     */
    private float radius;
    /**
     * true 顺时针 false逆时针
     */
    private boolean isClockwise;

    private Paint paint;

    /**
     * 控件属性
     */
    private TurnAttrs turnAttrs;

    public Turn(TurnAttrs turnAttrs, float radius, int color, boolean isClockwise) {
        this.turnAttrs = turnAttrs;
        this.radius = radius;
        this.isClockwise = isClockwise;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(turnAttrs.strokeWidth);
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas, float angle) {
        initRectF(canvas);
        if (isClockwise) {
            if (angle >= turnAttrs.maxRange + 360) {

            } else if (angle > 360) {
                canvas.drawArc(rectF, 180 + (angle - turnAttrs.maxRange), 540 - (180 + (angle - turnAttrs.maxRange)), false, paint);
            } else if (angle > turnAttrs.maxRange) {
                canvas.drawArc(rectF, 180 + (angle - turnAttrs.maxRange), turnAttrs.maxRange, false, paint);
            } else {
                canvas.drawArc(rectF, 180, angle, false, paint);
            }
        } else {
            if (angle >= turnAttrs.maxRange + 360) {

            } else if (angle > 360) {
                canvas.drawArc(rectF, angle - turnAttrs.maxRange, 360 - (angle - turnAttrs.maxRange), false, paint);
            } else if (angle > turnAttrs.maxRange) {
                canvas.drawArc(rectF, angle - turnAttrs.maxRange, turnAttrs.maxRange, false, paint);
            } else {
                canvas.drawArc(rectF, 0, angle, false, paint);
            }
        }
    }

    /**
     * 初始绘制范围
     * 通过圆心和半径大小计算获得
     *
     * @param canvas
     */
    private void initRectF(Canvas canvas) {
        if (rectF == null) {
            int centerX = canvas.getWidth() / 2;
            int centerY = canvas.getHeight() / 2;
            rectF = new RectF();
            rectF.left = centerX - radius;
            rectF.top = centerY - radius;
            rectF.right = centerX + radius;
            rectF.bottom = centerY + radius;
        }
    }
}
