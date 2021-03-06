package ar.edu.unc.famaf.redditreader.backend;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class GetTopPostsTask extends AsyncTask<RedditDBHelper, Void, List<PostModel>> {

    GetTopPostsTask(int tabID) {
        this.tabID = tabID;
    }

    public int tabID;

    @Override
    protected List<PostModel> doInBackground(RedditDBHelper... redditDBHelpers) {

        InputStream input;
        RedditDBHelper dbreddit = redditDBHelpers[0];

        ArrayList<String> urlArray = new ArrayList<>();
        urlArray.add(0, "https://www.reddit.com/hot.json?limit=50");
        urlArray.add(1, "https://www.reddit.com/new.json?limit=50");
        urlArray.add(2, "https://www.reddit.com/top.json?limit=50");

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL
                    (urlArray.get(tabID)).openConnection();
            conn.setRequestMethod("GET");
            input = conn.getInputStream();
            Parser parserJson = new Parser();
            Listing listing = parserJson.readJsonStream(input);
            if (listing != null) {
                SQLiteDatabase db = dbreddit.getWritableDatabase();
                //dbreddit.onUpgrade(db, RedditDBHelper.VERSION, RedditDBHelper.VERSION);
                String query_aux = RedditDBHelper.POST_TABLE_TABID + " = " + "'" + tabID + "'";
                db.delete(RedditDBHelper.POST_TABLE, query_aux, null);
                for (int i = 0; i < listing.getChildren().size(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(dbreddit.POST_TABLE_TITLE, listing.getChildren().get(i).getTitle());
                    values.put(dbreddit.POST_TABLE_AUTHOR, listing.getChildren().get(i).getAuthor());
                    values.put(dbreddit.POST_TABLE_DATE, listing.getChildren().get(i).getDate());
                    values.put(dbreddit.POST_TABLE_COMMENTS, listing.getChildren().get(i).getComments());
                    values.put(dbreddit.POST_TABLE_URLSTRING, listing.getChildren().get(i).getUrlString());
                    values.put(dbreddit.POST_TABLE_SUBREDDIT, listing.getChildren().get(i).getSubreddit());
                    values.put(dbreddit.POST_TABLE_WEBLINK, listing.getChildren().get(i).getWebLink());
                    values.put(dbreddit.POST_TABLE_COMMENTSLINK, listing.getChildren().get(i).getCommentsLink());
                    values.put(dbreddit.POST_TABLE_TABID, String.valueOf(tabID));
                    long insertId = db.insert(dbreddit.POST_TABLE, null, values);

                }
                dbreddit.close();

                return listing.getChildren();

            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}



