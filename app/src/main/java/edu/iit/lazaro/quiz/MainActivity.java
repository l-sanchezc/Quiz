package edu.iit.lazaro.quiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Activity context;
    TextView txtview;
    ProgressDialog pd;

    ArrayList<String> stringList = new ArrayList<String>();

    static int questionNum = 0;

    private RadioGroup radioQuestions;
    private RadioButton radioButton;
    private RatingBar rb;
    private int right;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        BackgroundTask bt = new BackgroundTask();
        bt.execute("http://www.papademas.net/sample.txt"); //grab url

    }//end onCreate

    //background process to download the file from internet
    public class BackgroundTask extends AsyncTask<String, Integer, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            //display progress dialog
            pd = new ProgressDialog(context);
            pd.setTitle("Reading the text file");
            pd.setMessage("Please wait.");
            pd.setCancelable(true);
            pd.setIndeterminate(false);
            pd.show();

        }

        protected Void doInBackground(String... params) {
            URL url;
            String StringBuffer = null;
            try {
                //create url object to point to the file location on internet
                url = new URL(params[0]);
                //make a request to server
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //get InputStream instance
                InputStream is = con.getInputStream();
                //create BufferedReader object
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                //read content of the file line by line & add it to Stringbuffer
                while ((StringBuffer = br.readLine()) != null) {
                    stringList.add(StringBuffer);  //add to Arraylist
                }

                br.close();

            } catch (Exception e) {
                e.printStackTrace();
                //close dialog if error occurs
                if (pd != null) pd.dismiss();
            }

            return null;

        }

        protected void onPostExecute(Void result) {
            //close dialog
            if (pd != null)
                pd.dismiss();
            txtview = (TextView) findViewById(R.id.textView1);
            //display read text in TextView
            txtview.setText(stringList.get(0));
            rb = (RatingBar) findViewById(R.id.ratingBar);
            rb.setVisibility(View.INVISIBLE);
            startQuiz();

        }
    }//end BackgroundTask class

    public void startQuiz() {
        buttonListener();
    }

    public void buttonListener() {

        Button btnDisplay;
        radioQuestions = (RadioGroup) findViewById(R.id.radioQuestions);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioQuestions.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                switch (questionNum) {
                    case 0:
                        right=0;
                        //verify if result matches the right button selection
                        //i.e., (True or false!)
                        if (radioButton.getText().equals("True")){
                            right++;
                            Toast.makeText(MainActivity.this,
                                    " Right!", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this,
                                    " Wrong!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //verify if result matches the right button selection
                        //i.e., (True or false!)
                        if (radioButton.getText().equals("False")){
                            right++;
                            Toast.makeText(MainActivity.this,
                                    " Right!", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this,
                                    " Wrong!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //verify if result matches the right button selection
                        //i.e., (True or false!)
                        if (radioButton.getText().equals("True")){
                            right++;
                            Toast.makeText(MainActivity.this,
                                    " Right!", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this,
                                    " Wrong!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //verify if result matches the right button selection
                        //i.e., (True or false!)
                        if (radioButton.getText().equals("False")){
                            right++;
                            Toast.makeText(MainActivity.this,
                                    " Right!", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this,
                                    " Wrong!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        //verify if result matches the right button selection
                        //i.e., (True or false!)
                        if (radioButton.getText().equals("False")){
                            right++;
                            Toast.makeText(MainActivity.this,
                                    " Right!", Toast.LENGTH_SHORT).show();}
                        else
                            Toast.makeText(MainActivity.this,
                                    " Wrong!", Toast.LENGTH_SHORT).show();
                        rb.setRating(right);
                        rb.setVisibility(View.VISIBLE);
                        break;
                }//end switch
            }
        });
        imageListener();
    }//end buttonListener

    public void imageListener() {

        image = (ImageView) findViewById(R.id.imageView1);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // get new question for viewing
                if (questionNum == 4)
                    //reset count to -1 to start first question again
                    questionNum = -1;
                txtview.setText(stringList.get(++questionNum));
                //reset radio button (radioTrue) to default
                radioQuestions.check(R.id.radioTrue);
                rb.setVisibility(View.INVISIBLE);
            }
        });
    }//end imageListener

}//end activity