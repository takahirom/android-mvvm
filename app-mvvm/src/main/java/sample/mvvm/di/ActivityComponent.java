package sample.mvvm.di;

import dagger.Subcomponent;
import sample.mvvm.view.BaseActivity;
import sample.mvvm.viewmodel.BaseViewModel;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity viewModelActivity);

    void inject(BaseViewModel viewModel);

}
