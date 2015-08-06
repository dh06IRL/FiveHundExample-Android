
package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    public Integer affection;
    @Expose
    public Avatars avatars;
    @Expose
    public String city;
    @Expose
    public String country;
    @SerializedName("cover_url")
    @Expose
    public String coverUrl;
    @Expose
    public String firstname;
    @Expose
    public String fullname;
    @Expose
    public Integer id;
    @Expose
    public String lastname;
    @SerializedName("store_on")
    @Expose
    public Boolean storeOn;
    @SerializedName("upgrade_status")
    @Expose
    public Integer upgradeStatus;
    @Expose
    public String username;
    @SerializedName("userpic_https_url")
    @Expose
    public String userpicHttpsUrl;
    @SerializedName("userpic_url")
    @Expose
    public String userpicUrl;
    @Expose
    public Integer usertype;


}
