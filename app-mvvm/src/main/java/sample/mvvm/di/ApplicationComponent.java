package sample.mvvm.di;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ActivityComponent activityComponent(ActivityModule module);
}
