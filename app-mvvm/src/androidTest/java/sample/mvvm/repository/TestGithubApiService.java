package sample.mvvm.repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.Url;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import rx.Observable;
import sample.github.GithubApiService;
import sample.github.model.Repository;
import sample.github.model.User;

public class TestGithubApiService implements GithubApiService {

    public Call<List<Repository>> repositories;
    public Call<User> user;

    private BehaviorDelegate<GithubApiService> delegate;
    public void setDelegate(BehaviorDelegate<GithubApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<List<Repository>> publicRepositories(@Path("username") String username) {

        if (repositories == null) {
            List<Repository> repositories = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Repository repository = new Repository();
                repository.id = i;
                repository.name = "test" + i;
                repository.description = "desc" + i;
                repository.forks = i;
                repository.watchers = i;
                repository.stars = i;
                repository.language = "language" + i;
                repository.homepage = "homepage" + i;
                repository.fork = false;

                User user = new User();
                user.id = i;
                user.name = "name" + i;
                user.url = "url" + i;
                user.email = "email" + i;
                user.login = "login" + i;
                user.location = "location" + i;
                user.avatarUrl = "avatarUrl" + i;
                repository.owner = user;
                repositories.add(repository);
            }
            User user = new User();
            user.id = 1;
            user.name = "name";
            user.url = "url";
            user.email = "email";
            user.login = "login";
            user.location = "location";
            user.avatarUrl = "avatarUrl";

            this.repositories = Calls.response(repositories);
        }

        return delegate.returning(repositories).publicRepositories(username);
    }

    @Override
    public Observable<User> userFromUrl(@Url String userUrl) {
        if (user == null) {
            User user = new User();
            user.id = 1;
            user.name = "name";
            user.url = "url";
            user.email = "email";
            user.login = "login";
            user.location = "location";
            user.avatarUrl = "avatarUrl";
            this.user = Calls.response(user);
        }

        return delegate.returningResponse(user).userFromUrl(userUrl);
    }

}
