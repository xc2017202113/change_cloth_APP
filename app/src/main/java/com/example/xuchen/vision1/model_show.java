package com.example.xuchen.vision1;


import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.threed.jpct.Object3D;

public class model_show extends AppCompatActivity {

    public final static int MSG_LOAD_MODEL_SUC=0;

    private GLSurfaceView myGLView;

    private RenderView myRenderer;

    private Button btnLoad;

    private Button btnLeft;

    private Button btnRight;

    private Button btnTop;

    private Button btnDown;

    private static final String TAG = "model_show";

    private Thread threadLoadModel;

    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_model_show);

        handler=new Handler(){

            @Override

            public void handleMessage(Message msg){
                Log.d(TAG, "model_show:handleMessage: "+msg.what);
                switch (msg.what){
                    case MSG_LOAD_MODEL_SUC:
                        Toast.makeText(model_show.this, "模型加载成功", Toast.LENGTH_SHORT).show();
                        Object3D object3D=(Object3D) msg.obj;
                        myRenderer.myWorld.addObject(object3D);
                        break;
                }
            }

        };

        btnLoad=findViewById(R.id.btnLoadModel);

        btnLeft=findViewById(R.id.btnLeft);

        btnRight=findViewById(R.id.btnRight);

        btnTop=findViewById(R.id.btnTop);

        btnDown=findViewById(R.id.btnDown);
        btnLoad.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Toast.makeText(model_show.this, "开始加载模型", Toast.LENGTH_SHORT).show();
                threadLoadModel.start();

            }

        });
        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                myRenderer.applyTranslation(-10,0,0);
            }

        });
        btnRight.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                myRenderer.applyTranslation(10,0,0);
            }

        });
        btnTop.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                myRenderer.applyTranslation(0,-10,0);
            }

        });
        btnDown.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                myRenderer.applyTranslation(0,10,0);

            }

        });
        myGLView = (GLSurfaceView) this.findViewById(R.id.surfaceView);
        myGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        myGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        myGLView.setZOrderOnTop(true);
        myRenderer = new RenderView(this);
        myGLView.setRenderer(myRenderer);
        threadLoadModel=new Thread(new Runnable() {

            @Override

            public void run() {
                myRenderer.addObject(model_show.this);
            }

        });



    }



    @Override

    protected void onPause() {

        super.onPause();

        myGLView.onPause();

    }



    @Override

    protected void onResume() {

        super.onResume();

        myGLView.onResume();

    }

}

