package com.telran.classwork.hairsalon2.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TelRan on 26.03.2017.
 */

public class ItemListTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;

    public ItemListTouchListener(Context context, final RecyclerView recyclerView, final ClickListener listener){
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (view!= null && listener!=null){

                    listener.onClickItem(view, recyclerView.getChildAdapterPosition(view));
                }
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener{
        void onClickItem(View view, int position);
    }
}
