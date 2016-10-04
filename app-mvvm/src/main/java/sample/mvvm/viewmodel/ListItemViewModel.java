package sample.mvvm.viewmodel;

import android.databinding.Bindable;
import android.view.View;

import sample.github.model.Repository;
import sample.mvvm.R;
import sample.mvvm.di.ActivityComponent;
import sample.mvvm.view.DetailActivity;

public class ListItemViewModel extends BaseViewModel {

    private Repository repository;

    public ListItemViewModel(ActivityComponent component, Repository repository) {
        super(component);
        this.repository = repository;
    }

    @Bindable
    public String getName() {
        return repository.name;
    }

    @Bindable
    public String getDescription() {
        return repository.description;
    }

    @Bindable
    public String getStarts() {
        return context.getString(R.string.text_stars, repository.stars);
    }

    @Bindable
    public String getWatchers() {
        return context.getString(R.string.text_watchers, repository.watchers);
    }

    @Bindable
    public String getForks() {
        return context.getString(R.string.text_forks, repository.forks);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        // 個別の変更通知が必要な場合
        // import sample.mvvm.BR;
        // notifyPropertyChanged(BR.name);
        // notifyPropertyChanged(BR.description);
        // notifyPropertyChanged(BR.starts);
        // notifyPropertyChanged(BR.watchers);
        super.notifyChange();
    }

    public void onItemClick(View view) {
        context.startActivity(DetailActivity.newIntent(context, repository));
    }

}
