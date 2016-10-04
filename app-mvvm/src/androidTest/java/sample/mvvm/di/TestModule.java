package sample.mvvm.di;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import retrofit2.mock.BehaviorDelegate;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import sample.github.GithubApiService;
import sample.mvvm.Application;
import sample.mvvm.repository.TestGithubApiService;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
public class TestModule extends ApplicationModule {

    protected TestGithubApiService githubApiService;

    public TestModule(TestGithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @Override
    public GithubApiService githubApiService(Retrofit retrofit) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(0, TimeUnit.SECONDS);
        behavior.setVariancePercent(100);
        behavior.setFailurePercent(0);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<GithubApiService> delegate = mockRetrofit.create(GithubApiService.class);
        githubApiService.setDelegate(delegate);

        return githubApiService;
    }

    @Override
    public Scheduler subscribeScheduler() {
        return Schedulers.immediate();
    }

}
