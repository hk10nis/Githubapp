package edu.self.githubapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class afterlogin extends AppCompatActivity {

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

                code = uri.getQueryParameter("code");
                TextView txtview = (TextView)findViewById(R.id.textview);
                txtview.setText(code);

                /* アクセストークンを入手するためにアクセス*/
                String clientid = "55567c2247acc9d37bbf";
                String clientsecret = "4e245a455b80a65b507bd87f09c2313e6a8f1077";
                String urlStr = "https://github.com/login/oauth/access_token?client_id=" + clientid + "&client_secret=" + clientsecret +"&code" + code ;
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

                txtview.setText(result);

            }
        }
    }



}
