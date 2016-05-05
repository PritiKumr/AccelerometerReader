package com.example.aswitharajendran.shake;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Aswitha Rajendran on 30-03-2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String>{

    Context ctx;
    BackgroundTask(Context ctx){

        this.ctx=ctx;

    }
    @Override
    protected String doInBackground(String... params) {

        String reg_url="https://shake-server.herokuapp.com/api/v1/get_value";
       // String login_url="http://192.168.1.100/webapp/login.php";

        String method=params[0];
        if(method.equals("Register")){
            String xxx=params[1];
            String yyy=params[2];
            String zzz=params[3];
            URL url= null;
            try {
                url = new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("x_axis", "UTF-8") + "=" + URLEncoder.encode(xxx, "UTF-8") + "&" +
                        URLEncoder.encode("y_axis", "UTF-8") + "=" + URLEncoder.encode(yyy, "UTF-8") + "&" +

                        URLEncoder.encode("z_axis", "UTF-8") + "=" + URLEncoder.encode(zzz, "UTF-8");
                bufferedWriter.write(data);
                String jsonReply;
                if(httpURLConnection.getResponseCode()==201 || httpURLConnection.getResponseCode()==200)
                {
                    boolean success = true;
                    InputStream response = httpURLConnection.getInputStream();
                    jsonReply = convertStreamToString(response);
                    Log.d("Response", jsonReply);
                    // Do JSON handling here....
                    boolean a = (jsonReply.toLowerCase()).contains("authenticated");
                    if (a == true){
                        String getter1 = "1";
                    }
                    else{
                        String getter1 = "0";
                    }
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "registration success";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

         catch (IOException e) {
            e.printStackTrace();
        }
}



return null;

        }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
protected void onPostExecute(String result) {
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }

@Override
protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        }
        }

