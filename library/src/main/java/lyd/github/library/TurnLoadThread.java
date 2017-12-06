package lyd.github.library;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shawn on 17/11/28.
 */

public class TurnLoadThread extends Thread {

    private SurfaceHolder holder;
    /**
     * 正在运行中
     */
    public boolean isRunning;
    /**
     * 记录现在走到第几帧
     */
    private int frame = 0;
    /**
     * 一次旋转动画所占的所有帧
     */
    private float allFrame;
    /**
     * 圆环集合
     */
    private List<Turn> turnList;
    /**
     * 控件属性
     */
    private TurnAttrs turnAttrs;
    /**
     * 控件背景
     */
    private int backgroundColor;

    public TurnLoadThread(TurnAttrs turnAttrs, SurfaceHolder holder, int backgroundColor) {
        this.turnAttrs = turnAttrs;
        this.holder = holder;
        this.backgroundColor = backgroundColor;
        allFrame = (360 + turnAttrs.maxRange) / 10;
        turnList = new ArrayList<>();
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                synchronized (holder) {
                    canvas = holder.lockCanvas();
                    frame = frame + 1;
                    onDraw(canvas);
                    Thread.sleep(turnAttrs.speed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void onDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        /**
         * 判断设置颜色是否为透明色
         */
        if (backgroundColor == Color.TRANSPARENT) {
            canvas.drawColor(backgroundColor, PorterDuff.Mode.CLEAR);
        } else {
            canvas.drawColor(backgroundColor);
        }
        for (int i = 0; i < turnList.size(); i++) {
            turnList.get(i).draw(canvas, frame % allFrame * 10 + 10);
        }
    }

    /**
     * 清空所有
     */
    public void cleanAllTurn() {
        turnList.clear();
    }

    /**
     * 添加
     *
     * @param turn
     */
    public void addTurn(Turn turn) {
        turnList.add(turn);
    }

    /**
     * 获取列表
     *
     * @return
     */
    public List<Turn> getTurnList() {
        return turnList;
    }

}
