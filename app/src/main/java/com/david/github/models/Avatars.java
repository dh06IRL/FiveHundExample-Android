
package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatars {

    @SerializedName("default")
    @Expose
    public Default _default;
    @Expose
    public Large large;
    @Expose
    public Small small;
    @Expose
    public Tiny tiny;

}
