package by.bsu.famcs.lab4notepad;

import android.annotation.TargetApi;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements GestureOverlayView.OnGesturePerformedListener{

    private EditText text;
    GestureLibrary gLib;
    GestureOverlayView textGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editText);
        text.setEnabled(false);

        gLib = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!gLib.load()) {
            finish();
        }

        textGo = (GestureOverlayView) findViewById(R.id.textGo);
        textGo.addOnGesturePerformedListener(this);
    }

    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);

        if (predictions.size() > 0 && text.isEnabled()) {
            Prediction prediction = predictions.get(0);
            if (prediction.score > 1.0) {
                text.setText(text.getText() + prediction.name);
            }
        }
    }

    public void onClickEdit(View v) {
        text.setEnabled(true);
    }

    public void onClickSave(View v){
        text.setEnabled(false);

       /* FileOutputStream fos = null;
        try {
            new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "by/bsu/famcs/lab4notepad/").mkdir();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "by/bsu/famcs/lab4notepad/text.txt");
            fos = new FileOutputStream(file);
            fos.write(text.getText().toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        text.setText("");
    }

    public void onClickUpload(View v) {
        /*BufferedReader br = null;
       try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "by/bsu/famcs/lab4notepad/text.txt"))));
            String line = br.readLine();
            text.setText(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
