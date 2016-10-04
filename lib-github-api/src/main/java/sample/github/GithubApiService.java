package sample.github;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;
import sample.github.model.Repository;
import sample.github.model.User;

public interface GithubApiService {

    @GET("users/{username}/repos")
    Observable<List<Repository>> publicRepositories(@Path("username") String username);

    @GET
    Observable<User> userFromUrl(@Url String userUrl);

}
