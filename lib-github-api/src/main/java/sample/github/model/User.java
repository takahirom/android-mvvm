package sample.github.model;

import android.text.TextUtils;

import com.squareup.moshi.Json;

import org.parceler.Parcel;

@Parcel
public class User {

    public long id;
    public String name;
    public String url;
    public String email;
    public String login;
    public String location;
    @Json(name = "avatar_url")
    public String avatarUrl;

    public User() {
    }

    public boolean hasEmail() {
        return !TextUtils.isEmpty(email);
    }

    public boolean hasLocation() {
        return !TextUtils.isEmpty(location);
    }

}
