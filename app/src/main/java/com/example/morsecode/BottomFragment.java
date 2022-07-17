package com.example.morsecode;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BottomFragment extends Fragment {

    private ImageButton imageButton1, imageButton2, imageButton3;
    private EditText editTextTextMultiLine, editTextTextMultiLine2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        imageButton1 = (ImageButton) view.findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) view.findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) view.findViewById(R.id.imageButton3);
        editTextTextMultiLine = (EditText) getActivity().findViewById(R.id.editTextTextMultiLine);
        editTextTextMultiLine2 = (EditText) getActivity().findViewById(R.id.editTextTextMultiLine2);
        addListenerOnImageButton_m();
        addListenerOnImageButton_b();
        addListenerOnImageButton_s();
        //return inflater.inflate(R.layout.fragment_bottom, container, false);
        return view;
    }

    public void addListenerOnImageButton_m(){
        imageButton1.setOnLongClickListener(
                new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        editTextTextMultiLine.append("-");
                        return true;
                    }
                }

        );
        imageButton1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editTextTextMultiLine.append(".");
                    }
                }
        );

    }

    public void addListenerOnImageButton_s(){
        imageButton3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editTextTextMultiLine.append(" ");
                    }
                }
        );

    }
    

    public void addListenerOnImageButton_b(){
        imageButton2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 300);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            private Handler mHandler;

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    String str = editTextTextMultiLine.getText().toString().trim();
                    if(str.length()!=0){
                        str  = str.substring( 0, str.length() - 1 );
                        editTextTextMultiLine.setText ( str );
                    }
                    mHandler.postDelayed(this, 300);
                }
            };

        });
        imageButton2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = editTextTextMultiLine.getText().toString().trim();
                        if(str.length()!=0){
                            str  = str.substring( 0, str.length() - 1 );
                            editTextTextMultiLine.setText ( str );
                        }

                    }
                }
        );

    }
}