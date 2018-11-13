package com.assignment.leaf.leaf;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BackgroundTask extends AsyncTask<String,String,String> {
    Context context;
    RecyclerView mListView;
    public BackgroundTask(){}
    public BackgroundTask(Context context,RecyclerView mListView){
        this.context=context;
        this.mListView=mListView;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.mProgress.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL myUrl=new URL(strings[0]);
            HttpURLConnection connection=(HttpURLConnection) myUrl.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            Log.d("ajeet", buffer.toString());
            String json=buffer.toString();
            JSONObject jObject=null;
            JSONObject jObj= new JSONObject(json);
            JSONArray array = jObj.getJSONArray("results");
            for(int i=0;i<array.length();i++){
                JSONObject o= array.getJSONObject(i);
                JSONObject Name=o.getJSONObject("name");
                JSONObject PicUrl=o.getJSONObject("picture");

                JSONObject dobb=o.getJSONObject("dob");

                String email=o.getString("email");


                String thumbnail=PicUrl.getString("thumbnail");
                String  large=PicUrl.getString("large");
                String  medium=PicUrl.getString("medium");

                String first=Name.getString("first");
                String  last=Name.getString("last");
                String  title=Name.getString("title");

                  String dob=dobb.getString("date");
                  int age=dobb.getInt("age");
                 String phone=o.getString("phone");
                Model model=new Model(first,last,title,phone,thumbnail,medium,large,email,dob,26);
                MainActivity.dataList.add(model);








//


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            MainActivity.mProgress.hide();
        } catch (IOException e) {
            e.printStackTrace();
            MainActivity.mProgress.hide();
        } catch (JSONException e) {
            e.printStackTrace();
            MainActivity.mProgress.hide();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.mProgress.hide();
        MyAdapter myAdapter=new MyAdapter(MainActivity.dataList,context);
        mListView.setAdapter(myAdapter);
    }
}
