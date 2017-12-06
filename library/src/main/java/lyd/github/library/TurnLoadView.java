package lyd.github.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by shawn on 17/11/28.
 */

public class TurnLoadView extends SurfaceView implements SurfaceHolder.Callback {

    private TurnLoadThread loadThread;
    /**
     * 控件属性
     */
    private TurnAttrs turnAttrs;
    /**
     * 控件背景
     */
    private int backgroundColor = Color.TRANSPARENT;

    public TurnLoadView(Context context) {
        this(context, null);
    }

    public TurnLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurnLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }

    /**
     * 初始view的属性
     */
    private void initAttrs(AttributeSet attrs) {
        turnAttrs = new TurnAttrs();
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TurnLoad);
        turnAttrs.maxRange = typedArray.getInteger(R.styleable.TurnLoad_maxRange, turnAttrs.maxRange);
        turnAttrs.strokeWidth = typedArray.getFloat(R.styleable.TurnLoad_strokeWidth, turnAttrs.strokeWidth);
        turnAttrs.speed = typedArray.getInteger(R.styleable.TurnLoad_speed, turnAttrs.speed);
        backgroundColor = typedArray.getColor(R.styleable.TurnLoad_backgroundColor, backgroundColor);
        typedArray.recycle();
    }

    /**
     * 初始view
     */
    private void initView() {
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (loadThread == null) {
            loadThread = new TurnLoadThread(turnAttrs, holder, backgroundColor);
        }
        loadThread.isRunning = true;
        loadThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        loadThread.isRunning = false;
        try {
            loadThread.join();
            loadThread = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空所有
     */
    public void cleanAllTurn() {
        loadThread.cleanAllTurn();
    }

    /**
     * 添加
     *
     * @param turn
     */
    public void addTurn(Turn turn) {
        loadThread.addTurn(turn);
    }

    /**
     * 获取列表
     *
     * @return
     */
    public List<Turn> getTurnList() {
        return loadThread.getTurnList();
    }
}
