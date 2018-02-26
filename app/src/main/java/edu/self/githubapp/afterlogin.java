package edu.self.githubapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class afterlogin extends AppCompatActivity {

    String accesstoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);

        Intent intent = getIntent();
        String action = intent.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            if(uri != null){
                //String stringUri;
                //stringUri = uri.toString();
                String code;
                String accesstoken;

                code = uri.getQueryParameter("code");
                TextView txtview = (TextView)findViewById(R.id.textview);
                txtview.setText(code);

                TextView txtview2 = (TextView)findViewById(R.id.textview2);
                accesstokenreceiver receiver = new accesstokenreceiver(txtview2);
                receiver.execute(code);

                TextView txtview3 = (TextView)findViewById(R.id.textView5);
                Eventreceiver eventreceiver = new Eventreceiver(txtview3);
                eventreceiver.execute();
            }
        }

    }

    private class accesstokenreceiver extends AsyncTask<String, String, String>{
        private TextView _act;
        public accesstokenreceiver(TextView act){
            _act = act;
        }

        public String doInBackground(String... params){
            String code = params[0];

            /* アクセストークンを入手するためにアクセス*/
            String clientid = "55567c2247acc9d37bbf";
            String clientsecret = "4e245a455b80a65b507bd87f09c2313e6a8f1077";
            String urlStr = "https://github.com/login/oauth/access_token?client_id=" + clientid + "&client_secret=" + clientsecret +"&code=" + code  ;
            //Uri uri1 = Uri.parse(urlStr);
            //Intent obtain_token = new Intent(Intent.ACTION_VIEW,uri1);
            //startActivity(obtain_token);
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try{
                URL url = new URL(urlStr);
                con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept", "application/json");
                con.connect();
                is = con.getInputStream();
                result = IOUtils.toString(is, StandardCharsets.UTF_8);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                if(is != null){
                    try{
                        is.close();
                    }
                    catch (IOException e){

                    }
                }
            }
            return result;
        }

        public void onPostExecute(String result){

            try {
                JSONObject rootJSON = new JSONObject(result);

                accesstoken = rootJSON.getString("access_token");

            }
            catch(JSONException ex) {
            }

            _act.setText(accesstoken);

        }
    }

    private class Eventreceiver extends AsyncTask<String, String, String>{
        private TextView _event;
        public Eventreceiver(TextView event){
            _event = event;
        }

        public String doInBackground(String... params){


            /* イベントを入手するためにアクセス*/
            String urlStr = "https://api.github.com/user?access_token="+accesstoken;
            String urlStr1 = "https://api.github.com/users/hk10nis/events";

            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try{
                URL url = new URL(urlStr1);
                con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                //con.setRequestProperty("Authorization", "token " +accesstoken);
                con.connect();
                is = con.getInputStream();
                result = IOUtils.toString(is, StandardCharsets.UTF_8);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                if(is != null){
                    try{
                        is.close();
                    }
                    catch (IOException e){

                    }
                }
            }
            result ="";
            return result;
        }

        public void onPostExecute(String result){
            _event.setText(result);

        }
    }

}
