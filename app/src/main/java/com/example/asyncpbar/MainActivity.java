package com.example.asyncpbar;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    Button btn;
    private ProgressBar progressBar;
    TextView txt;
    Integer count = 1;
    EditText edit;
    TextView tView ;
    Integer sleepTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        btn = (Button) findViewById(R.id.btn);
        btn.setText("Start");
        txt = (TextView) findViewById(R.id.output);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit = (EditText) findViewById(R.id.editNumber);
                tView = (TextView) findViewById(R.id.tView);
                sleepTime = Integer.valueOf(edit.getText().toString());
                tView.setText("Sleep Time: " + String.valueOf(sleepTime));
                count = 1;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                switch (view.getId()){
                    case R.id.btn:
                        new MyTask().execute(100);
                        break;
                }
            }
        };
        btn.setOnClickListener(listener);
    }
    class MyTask extends AsyncTask<Integer, Integer, String> {




        @Override
        protected String doInBackground(Integer... params){
            for (; count <= params[0]; count++){
                try{
                    Thread.sleep(sleepTime);
                    publishProgress(count);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return "Task completed.";
        }
        @Override
        protected void onPostExecute(String result){
            progressBar.setVisibility(View.GONE);
            txt.setText(result);
            btn.setText("Restart");
            btn.setEnabled(true);
        }
        @Override
        protected void onPreExecute() {
            txt.setText("Task starting ...");
            btn.setEnabled(false);
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            txt.setText("Running ..." + values[0] + "%");
            progressBar.setProgress(values[0]);
        }
    }
}
