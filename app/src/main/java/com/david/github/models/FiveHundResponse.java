
package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FiveHundResponse {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @Expose
    public List<Photo> photos = new ArrayList<Photo>();
    @SerializedName("total_items")
    @Expose
    public Integer totalItems;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

}
