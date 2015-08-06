
package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @Expose
    public String format;
    @SerializedName("https_url")
    @Expose
    public String httpsUrl;
    @Expose
    public Integer size;
    @Expose
    public String url;

}
