package lyd.github.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
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
    /**
     * 圆环颜色
     */
    private String turnColorStr;

    private List<Turn> turnList;

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
        initTurn();
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
        turnColorStr = typedArray.getString(R.styleable.TurnLoad_turnColorList);
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

    private void initTurn() {
        turnList = new ArrayList<>();
        if (TextUtils.isEmpty(turnColorStr)) {
            return;
        }
        String[] colorStrs = turnColorStr.split(",");
        boolean isStartTop = true;
        for (int i = 0; i < colorStrs.length; i++) {
            turnList.add(new Turn((i + 1) / (float) colorStrs.length, Color.parseColor(colorStrs[i]), isStartTop));
            isStartTop = !isStartTop;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (loadThread == null) {
            loadThread = new TurnLoadThread(turnAttrs, holder, turnList, backgroundColor);
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
     * 添加
     *
     * @param turn
     */
    public void addTurn(int index, Turn turn) {
        turnList.add(index, turn);
    }

    /**
     * 获取列表
     *
     * @return
     */
    public List<Turn> getTurnList() {
        return turnList;
    }

    /**
     * 设置圆环最大的幅度
     *
     * @param maxRange
     */
    public void setMaxRange(int maxRange) {
        this.turnAttrs.maxRange = maxRange;
    }

    /**
     * 设置圆环宽度
     *
     * @param strokeWidth
     */
    public void setStrokeWidth(float strokeWidth) {
        this.turnAttrs.strokeWidth = strokeWidth;
    }

    /**
     * 设置速度
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.turnAttrs.speed = speed;
    }

    /**
     * 设置背景色
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    /**
     * 设置圆环颜色
     *
     * @param colors
     */
    public void setTurnColorList(int... colors) {
        boolean isStartTop = true;
        for (int i = 0; i < colors.length; i++) {
            turnList.add(new Turn((i + 1) / (float) colors.length, colors[i], isStartTop));
            isStartTop = !isStartTop;
        }
    }
}
