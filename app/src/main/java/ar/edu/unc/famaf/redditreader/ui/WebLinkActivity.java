package ar.edu.unc.famaf.redditreader.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import ar.edu.unc.famaf.redditreader.backend.Backend;

public class WebLinkActivity extends AppCompatActivity{

    public final static String WEB_LINK = "web_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        Intent intent = getIntent();
        String urlWebLink = intent.getExtras().getString(WEB_LINK);

        if(Backend.getInstance().isNetworkAvailable(this)) {
            try {
                webView.loadUrl(urlWebLink);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "No se pudo cargar el link seleccionado.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Conexion a Internet no disponible", Toast.LENGTH_LONG).show();
        }
    }

}
