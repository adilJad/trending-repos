package com.jadeddib.trendingrepos.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jadeddib.trendingrepos.R;
import com.jadeddib.trendingrepos.model.Repo;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by jad on 16/01/2018.
 */

public class RepoItemAdapter extends ArrayAdapter<Repo> {

    LayoutInflater mInflater;
    List<Repo> mRepos;


    public RepoItemAdapter(@NonNull Context context, @NonNull List<Repo> repos) {
        super(context, R.layout.list_repo_item, repos);

        mRepos = repos;
        mInflater = LayoutInflater.from(context);
    }

    public void addReposToList(List<Repo> repos) {
        mRepos.addAll(repos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRepos.size();
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable
                                View convertView,
                        @NonNull
                                ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_repo_item, parent, false);
        }

        Repo repo = mRepos.get(position);

        TextView tvRepoTitle = (TextView) convertView.findViewById(R.id.tv_repo_title);
        TextView tvRepoDesc = (TextView) convertView.findViewById(R.id.tv_repo_description);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
        TextView tvRatings = (TextView) convertView.findViewById(R.id.tv_ratings);
        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);

        tvRepoTitle.setText(repo.getTitle());
        tvRepoDesc.setText(repo.getDescription());
        tvUserName.setText(repo.getUsername());
        tvRatings.setText(repo.getRatings());

        new DownLoadImageTask(ivAvatar).execute(repo.getAvatarUrl());

        return convertView;
    }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap avatar = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                avatar = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return avatar;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
