package by.bsu.famcs.lab4calc;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements GestureOverlayView.OnGesturePerformedListener {

    GestureLibrary gLib;
    GestureLibrary gSignLib;
    GestureOverlayView firstGo;
    GestureOverlayView secondGo;
    GestureOverlayView operationGo;
    EditText firstOperand;
    TextView operation;
    TextView result;
    EditText secondOperand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstOperand = (EditText) findViewById(R.id.editText2);
        firstOperand.setEnabled(false);

        operation = (TextView) findViewById(R.id.textView4);

        secondOperand = (EditText) findViewById(R.id.editText3);
        secondOperand.setEnabled(false);

        result = (TextView) findViewById(R.id.textView10);

        gLib = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!gLib.load()) {
            finish();
        }

        gSignLib = GestureLibraries.fromRawResource(this, R.raw.gesture_sign);
        if (!gSignLib.load()) {
            finish();
        }

        firstGo = (GestureOverlayView) findViewById(R.id.firstOperandGo);
        firstGo.addOnGesturePerformedListener(this);

        operationGo = (GestureOverlayView) findViewById(R.id.operationGo);
        operationGo.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gSignLib.recognize(gesture);

                if (predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    if (prediction.score > 1.0 && operation.getText().length() == 0) {
                       operation.setText(prediction.name);
                    }
                }
            }
        });

        secondGo = (GestureOverlayView) findViewById(R.id.secondOperandGo);
        secondGo.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gLib.recognize(gesture);

                if (predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    if (prediction.score > 1.0) {
                        secondOperand.setText(secondOperand.getText() + prediction.name);
                    }
                }
            }
        });

    }

    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);

        if (predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            if (prediction.score > 1.0) {
                firstOperand.setText(firstOperand.getText() + prediction.name);
            }
        }
    }

    public void onClick(View v) {
        double first = Double.parseDouble(firstOperand.getText().toString());
        double second = Double.parseDouble(secondOperand.getText().toString());

        switch (operation.getText().toString()) {
            case "+":
                result.setText(String.valueOf(first + second));
                break;
            case "-":
                result.setText(String.valueOf(first - second));
                break;
            case "*":
                result.setText(String.valueOf(first * second));
                break;
            case "/":
                result.setText(String.valueOf(first / second));
                break;
        }
    }
}
