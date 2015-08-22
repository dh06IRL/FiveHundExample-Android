package com.david.github.services;

import com.david.github.utils.Constants;

import retrofit.RestAdapter;

/**
 * Created by davidhodge on 8/21/15.
 */
public class RestClient {

    private static Api REST_CLIENT;

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static Api get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(Api.class);
    }
}
