package ar.edu.unc.famaf.redditreader.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.PreviewImageDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        Intent intent = getIntent();
        PostModel postModel = (PostModel) intent.getSerializableExtra(NewsActivity.POST_ID);

        String subreddit = postModel.getSubreddit();
        String date = postModel.getDate();
        String title = postModel.getTitle();
        String author = postModel.getAuthor();
        String urlPreview = postModel.getUrlString();
        final String webLink = postModel.getWebLink();

        TextView subredditTV = (TextView) findViewById(R.id.postDetailSubreddit);
        TextView dateTV = (TextView) findViewById(R.id.postDetailDate);
        TextView titleTV = (TextView) findViewById(R.id.postDetailTitle);
        TextView authorTV = (TextView) findViewById(R.id.postDetailAuthor);
        TextView webLinkTV = (TextView) findViewById(R.id.postDetailWebLink);

        subredditTV.setText("r/" + subreddit);
        dateTV.setText(date);
        titleTV.setText(title);
        authorTV.setText("by " + author);
        webLinkTV.setText("Sitio Web: " + webLink);

        Bitmap bitmap = new PreviewImageDBHelper(this).getImage(urlPreview);

        if (bitmap != null) {
            ImageView imageView = (ImageView) findViewById(R.id.postDetailPreview);
            imageView.setImageBitmap(bitmap);
        }

        webLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWebLinkActivity = new Intent(getApplicationContext(), WebLinkActivity.class);
                intentWebLinkActivity.putExtra(WebLinkActivity.WEB_LINK, webLink);
                startActivity(intentWebLinkActivity);
            }
        });



    }
}
