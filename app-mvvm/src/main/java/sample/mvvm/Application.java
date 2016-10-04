package sample.mvvm;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import sample.mvvm.di.ApplicationComponent;
import sample.mvvm.di.ApplicationModule;
import sample.mvvm.di.DaggerApplicationComponent;

public class Application extends android.app.Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((Application) context.getApplicationContext()).component;
    }

    @VisibleForTesting
    public void setComponent(ApplicationComponent appComponent) {
        this.component = appComponent;
    }

}
