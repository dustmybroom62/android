package edu.galileo.android.customview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import edu.galileo.android.customview.R;

/**
 * Created by Larry on 3/23/2018.
 */

public class MyButton extends AppCompatButton {

    private Context mcontext;

    public MyButton(Context context) {
        super(context);
        init(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mcontext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(isPressed()){
            setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.bt_pressed, null));
        } else {
            setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.bt_not_pressed, null));
        }
        setTextColor(Color.parseColor("#eceff1"));
        super.onDraw(canvas);
    }
}
