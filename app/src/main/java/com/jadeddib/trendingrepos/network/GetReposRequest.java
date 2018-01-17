package com.jadeddib.trendingrepos.network;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by jad on 16/01/2018.
 */

public class GetReposRequest extends StringRequest {

    public static final String GET_REPOS_REQUEST_URL = "https://api.github" +
            ".com/search/repositories?q=created:>";

    public GetReposRequest(String startDate, int page, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {

        super(Method.GET, GET_REPOS_REQUEST_URL + startDate + "&sort=stars&order=desc&page="+page,
                listener,
                errorListener);
    }

}
