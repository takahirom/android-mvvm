package sample.mvvm.di;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import sample.github.GithubApiInterceptor;
import sample.github.GithubApiService;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new GithubApiInterceptor())
                .build();
    }

    @Provides
    @ApplicationScope
    public Retrofit githubRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public GithubApiService githubApiService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }

    @Provides
    @ApplicationScope
    public EventBus eventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @ApplicationScope
    public Scheduler subscribeScheduler() {
        return Schedulers.io();
    }

}
