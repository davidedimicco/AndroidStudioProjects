package it.unimi.dimicco.davide.myapproximatedrisikometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button computeBtn=(Button)findViewById(R.id.computeBtn);
        final EditText inputEditText=(EditText)findViewById(R.id.inputEditText);
        final TextView resultTextView=(TextView)findViewById(R.id.resultTextView);
        computeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=Integer.parseInt(inputEditText.getText().toString());
                double result=0;
                if(n>=4) {
                    result=((n-4)*(1.71))+5.40;
                }
                else if (n==3)
                    result=3.91;
                else if(n==2)
                    result=1.55;
                else if(n==1)
                    result=0.52;
                resultTextView.setText(Double.toString(result));
            }
        });
    }
}

