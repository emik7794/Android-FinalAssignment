package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.OnPostItemSelectedListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {


    List<PostModel> postModelList = new ArrayList<>();
    OnPostItemSelectedListener onPostItemSelectedListener;
    int TAB_ID = 1;
    int pos_aux = -1;
    static final int HOT = 0;
    static final int NEW = 1;
    static final int TOP = 2;
    private View view;
    ListView postsLV = null;


    public NewsActivityFragment() {
    }
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsActivityFragment newInstance(int sectionNumber) {
        NewsActivityFragment fragment = new NewsActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {
            pos_aux = getArguments().getInt(ARG_SECTION_NUMBER);
        } catch (Exception e) {
            pos_aux = -1;
            e.printStackTrace();
        }
        switch (pos_aux) {
            case HOT:
                TAB_ID = HOT;
                break;
            case NEW:
                TAB_ID = NEW;
                break;
            case TOP:
                TAB_ID = TOP;
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, container, false);
        postsLV = (ListView) view.findViewById(R.id.postsLV);

        try {
            pos_aux = getArguments().getInt(ARG_SECTION_NUMBER);
        }catch (Exception e){
            pos_aux = -1;
        }

        if (pos_aux == -1) return view;

        postsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostModel postModel = (PostModel) postsLV.getItemAtPosition(i);
                onPostItemSelectedListener.onPostItemPicked(postModel);
            }
        });


        final PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_row, postModelList);
        postsLV.setAdapter(adapter);

        final PostsIteratorListener postsIteratorListener = new PostsIteratorListener() {

            @Override
            public void nextPosts(List<PostModel> posts) {
                postModelList.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        };

        Backend.getInstance().getNextPosts(postsIteratorListener, getContext(), true, TAB_ID);

        // Attach the listener to the AdapterView onCreate
        postsLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Backend.getInstance().getNextPosts(postsIteratorListener, getContext(), false, TAB_ID);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

        });



        return view;
    }

    // Called once the fragment is associated with its activity.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onPostItemSelectedListener = (OnPostItemSelectedListener) activity;
    }

}

