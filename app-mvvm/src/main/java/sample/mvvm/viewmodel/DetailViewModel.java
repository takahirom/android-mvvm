package sample.mvvm.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sample.github.model.Repository;
import sample.mvvm.R;
import sample.mvvm.di.ActivityComponent;

public class DetailViewModel extends BaseViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    public final Repository repository;
    public final String description;
    public final String homepage;
    public final int homepageVisibility;
    public final String language;
    public final int languageVisibility;
    public final int forkVisibility;
    public final String ownerAvatarUrl;
    public final ObservableField<String> ownerName;
    public final ObservableField<String> ownerEmail;
    public final ObservableField<String> ownerLocation;
    public final ObservableInt ownerEmailVisibility;
    public final ObservableInt ownerLocationVisibility;
    public final ObservableInt ownerLayoutVisibility;

    public DetailViewModel(ActivityComponent component, Repository repository) {
        super(component);

        this.repository = repository;

        this.description = repository.description;
        this.homepage = repository.homepage;
        this.homepageVisibility = repository.hasHomepage() ? View.VISIBLE : View.GONE;
        this.language = context.getString(R.string.text_language, repository.language);
        this.languageVisibility = repository.hasLanguage() ? View.VISIBLE : View.GONE;
        this.forkVisibility = repository.isFork() ? View.VISIBLE : View.GONE;
        this.ownerAvatarUrl = repository.owner.avatarUrl;

        this.ownerName = new ObservableField<>();
        this.ownerEmail = new ObservableField<>();
        this.ownerLocation = new ObservableField<>();
        this.ownerLayoutVisibility = new ObservableInt(View.INVISIBLE);
        this.ownerEmailVisibility = new ObservableInt(View.VISIBLE);
        this.ownerLocationVisibility = new ObservableInt(View.VISIBLE);

        loadFullUser(repository.owner.url);
    }

    private void loadFullUser(String url) {
        githubService.userFromUrl(url)
                .compose(lifecycleProvider.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(subscribeScheduler)
                .subscribe(user -> {
                    Log.i(TAG, "Full user data loaded " + user);
                    ownerName.set(user.name);
                    ownerEmail.set(user.email);
                    ownerLocation.set(user.location);
                    ownerEmailVisibility.set(user.hasEmail() ? View.VISIBLE : View.GONE);
                    ownerLocationVisibility.set(user.hasLocation() ? View.VISIBLE : View.GONE);
                    ownerLayoutVisibility.set(View.VISIBLE);
                });
    }

}
