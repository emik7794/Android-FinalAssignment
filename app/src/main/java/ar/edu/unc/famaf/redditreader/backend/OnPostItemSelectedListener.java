package ar.edu.unc.famaf.redditreader.backend;


import ar.edu.unc.famaf.redditreader.model.PostModel;

public interface OnPostItemSelectedListener {
    void onPostItemPicked(PostModel post);
}
