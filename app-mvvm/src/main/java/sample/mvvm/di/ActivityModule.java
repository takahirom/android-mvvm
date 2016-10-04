package sample.mvvm.di;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import dagger.Module;
import dagger.Provides;
import sample.mvvm.view.BaseActivity;

@Module
public class ActivityModule {

    private BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Context activityContext() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public LifecycleProvider<ActivityEvent> lifecycleProvider() {
        return activity;
    }

}
