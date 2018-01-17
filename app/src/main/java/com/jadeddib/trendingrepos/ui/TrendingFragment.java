package com.jadeddib.trendingrepos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jadeddib.trendingrepos.R;
import com.jadeddib.trendingrepos.model.Repo;
import com.jadeddib.trendingrepos.network.GetReposRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jad on 16/01/2018.
 */

public class TrendingFragment extends Fragment {

    List<Repo> mRepos;
    private int currentPage = 1;
    private RepoItemAdapter mRepoItemAdapter;
    ListView lvRepos;



    public TrendingFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("LIST_STATE", lvRepos.onSaveInstanceState());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
                                     Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_trending, container, false);
        if (savedInstanceState == null) {
            lvRepos =
                    view.findViewById(R.id.lv_repos);
            mRepos = new ArrayList<Repo>();
            mRepoItemAdapter =
                    new RepoItemAdapter(getContext(),new ArrayList<Repo>());
            lvRepos.setAdapter(mRepoItemAdapter);
            getReposFromGithub(currentPage);
            lvRepos.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    currentPage++;
                    getReposFromGithub(currentPage);
                    return true;
                }
            });
        } else {
            lvRepos.onRestoreInstanceState(savedInstanceState.getParcelable("LIST_STATE"));
        }
        return view;
    }

    public void getReposFromGithub(final int page) {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray items = jsonResponse.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        Repo repo = new Repo();
                        repo.setTitle(items.getJSONObject(i).getString("name"));
                        repo.setAvatarUrl(items.getJSONObject(i).getJSONObject("owner").getString
                                ("avatar_url"));
                        repo.setUsername(items.getJSONObject(i).getJSONObject("owner").getString
                                ("login"));
                        repo.setDescription(items.getJSONObject(i).getString("description"));
                        repo.setRatings(items.getJSONObject(i).getInt("stargazers_count"));
                        mRepos.add(repo);
                    }
                    if (page == 1) {
                        mRepoItemAdapter.addAll(mRepos);
                    } else {
                        mRepoItemAdapter.clear();
                        mRepoItemAdapter.addAll(mRepos);
                        mRepoItemAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long todayInSeconds = today.getTime() / 1000;
        long thirtyDaysInSeconds = (60 * 60 * 24 * 30);
        long startDateInSeconds = todayInSeconds - thirtyDaysInSeconds;
        Date nd = new Date(startDateInSeconds * 1000);
        String startDate = sdf.format(nd);

        GetReposRequest getReposRequest = new GetReposRequest(startDate, currentPage, listener,
                errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(getReposRequest);
    }
}
