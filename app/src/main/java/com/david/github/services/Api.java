package com.david.github.services;

import com.david.github.models.FiveHundResponse;
import com.david.github.utils.Constants;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by davidhodge on 7/23/15.
 */
public interface Api {

    @GET(Constants.DATA)
    void getData(Callback<FiveHundResponse> callback);

}
