package com.example.arushi.androidassignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,unixtimestamp;
    Button b1,b2,b3,b4;
    final static String uri1="https://jsonplaceholder.typicode.com/comments";
    final static String uri2="https://jsonplaceholder.typicode.com/photos";
    final static String uri3="https://jsonplaceholder.typicode.com/todos";
    final static String uri4="https://jsonplaceholder.typicode.com/posts";
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.text);
        tv2=findViewById(R.id.text2);
        tv3=findViewById(R.id.text3);
        tv4=findViewById(R.id.text4);
        unixtimestamp=findViewById(R.id.unixtmp);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(uri1,1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(uri2,2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(uri3,3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(uri4,4);
            }
        });

        final Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                Long epoch=System.currentTimeMillis()/1000;
                unixtimestamp.setText(epoch.toString());
                handler.postDelayed(this,500); // set time here to refresh textView
            }
        });
        mydatabase = openOrCreateDatabase("url_data", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS WebData(id integer primary key,data mediumtext,st timestamp,ed timestamp);");
        fetch(uri1,1);
        fetch(uri2,2);
        fetch(uri3,3);
        fetch(uri4,4);
    }

    public void fetch(final String input_url,final int p)
    {
        class Work extends AsyncTask<String,Void,Void> {

            String message;
            Boolean flag;
            String starttime,endtime,json,startsave,endsave;

            @Override
            protected void onPreExecute() {
                flag=true;
                starttime=new Timestamp(currentTimeMillis()).toString();
                String s="Start: "+starttime+"\nEnd:\nStart Save:\nEnd Save:";
                if(p==1)
                    tv1.setText(s);
                else if(p==2)
                    tv2.setText(s);
                else if(p==3)
                    tv3.setText(s);
                else if(p==4)
                    tv4.setText(s);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(!flag)
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                else
                {
                    endtime=new Timestamp(currentTimeMillis()).toString();
                    String s="Start: "+starttime+"\nEnd: "+endtime+"\nStart Save:\nEnd Save:";
                    if(p==1)
                        tv1.setText(s);
                    else if(p==2)
                        tv2.setText(s);
                    else if(p==3)
                        tv3.setText(s);
                    else if(p==4)
                        tv4.setText(s);

                    startsave=new Timestamp(currentTimeMillis()).toString();
                    s="Start: "+starttime+"\nEnd: "+endtime+"\nStart Save: "+startsave+"\nEnd Save:";
                    if(p==1)
                        tv1.setText(s);
                    else if(p==2)
                        tv2.setText(s);
                    else if(p==3)
                        tv3.setText(s);
                    else if(p==4)
                        tv4.setText(s);
                    int id;
                    Cursor resultSet = mydatabase.rawQuery("select max(id) as MID from WebData",null);
                    resultSet.moveToFirst();
                    if(resultSet.isNull(0))
                        id=1;
                    else id=(resultSet.getInt(0))+1;

                    mydatabase.execSQL("INSERT INTO WebData VALUES(" + id + ",'" + json + "','"+starttime+"','"+endtime+"');");
                    endsave=new Timestamp(currentTimeMillis()).toString();
                    s="Start: "+starttime+"\nEnd: "+endtime+"\nStart Save: "+startsave+"\nEnd Save: "+endsave;
                    if(p==1)
                        tv1.setText(s);
                    else if(p==2)
                        tv2.setText(s);
                    else if(p==3)
                        tv3.setText(s);
                    else if(p==4)
                        tv4.setText(s);
                }
            }

            @Override
            protected Void doInBackground(String... params) {

                try {
                    URL url = new URL(input_url);
                    URLConnection yc = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    json = new String();
                    String ir;
                    while ((ir = in.readLine()) != null)
                        json = json.concat(ir);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                }

                return null;
            }
        }
        Work wrk=new Work();
        wrk.execute();
    }
}