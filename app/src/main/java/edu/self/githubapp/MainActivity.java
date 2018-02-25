package edu.self.githubapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 起動の際にgithub認証画面にアクセス*/
        String clientid = "55567c2247acc9d37bbf";
        String url = "https://github.com/login/oauth/authorize?client_id=" + clientid;
        Uri uri = Uri.parse(url);
        Intent login = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(login);
    }


}
