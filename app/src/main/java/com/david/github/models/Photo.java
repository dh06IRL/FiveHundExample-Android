
package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photo {

    @Expose
    public String aperture;
    @Expose
    public String camera;
    @Expose
    public Integer category;
    @SerializedName("collections_count")
    @Expose
    public Integer collectionsCount;
    @SerializedName("comments_count")
    @Expose
    public Integer commentsCount;
    @Expose
    public Integer converted;
    @SerializedName("converted_bits")
    @Expose
    public Integer convertedBits;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("crop_version")
    @Expose
    public Integer cropVersion;
    @Expose
    public String description;
    @SerializedName("favorites_count")
    @Expose
    public Integer favoritesCount;
    @SerializedName("focal_length")
    @Expose
    public String focalLength;
    @SerializedName("for_sale")
    @Expose
    public Boolean forSale;
    @SerializedName("for_sale_date")
    @Expose
    public Object forSaleDate;
    @Expose
    public Integer height;
    @SerializedName("hi_res_uploaded")
    @Expose
    public Integer hiResUploaded;
    @SerializedName("highest_rating")
    @Expose
    public Double highestRating;
    @SerializedName("highest_rating_date")
    @Expose
    public String highestRatingDate;
    @Expose
    public Integer id;
    @SerializedName("image_format")
    @Expose
    public String imageFormat;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @Expose
    public List<Image> images = new ArrayList<Image>();
    @Expose
    public String iso;
    @Expose
    public Object latitude;
    @Expose
    public String lens;
    @SerializedName("license_requests_enabled")
    @Expose
    public Boolean licenseRequestsEnabled;
    @SerializedName("license_type")
    @Expose
    public Integer licenseType;
    @SerializedName("licensing_requested")
    @Expose
    public Boolean licensingRequested;
    @Expose
    public Object location;
    @Expose
    public Object longitude;
    @Expose
    public String name;
    @Expose
    public Boolean nsfw;
    @SerializedName("positive_votes_count")
    @Expose
    public Integer positiveVotesCount;
    @Expose
    public Boolean privacy;
    @Expose
    public Double rating;
    @SerializedName("request_to_buy_enabled")
    @Expose
    public Boolean requestToBuyEnabled;
    @SerializedName("sales_count")
    @Expose
    public Integer salesCount;
    @SerializedName("shutter_speed")
    @Expose
    public String shutterSpeed;
    @Expose
    public Integer status;
    @SerializedName("store_download")
    @Expose
    public Boolean storeDownload;
    @SerializedName("store_license")
    @Expose
    public Boolean storeLicense;
    @SerializedName("store_print")
    @Expose
    public Boolean storePrint;
    @SerializedName("taken_at")
    @Expose
    public String takenAt;
    @SerializedName("times_viewed")
    @Expose
    public Integer timesViewed;
    @Expose
    public String url;
    @Expose
    public User user;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("votes_count")
    @Expose
    public Integer votesCount;
    @Expose
    public Boolean watermark;
    @Expose
    public Integer width;

}
