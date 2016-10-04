package sample.mvvm.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import icepick.State;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sample.mvvm.R;
import sample.mvvm.di.ActivityComponent;
import sample.mvvm.event.RepositoriesChangeEvent;

public class ListViewModel extends BaseViewModel {

    private static final String TAG = ListViewModel.class.getSimpleName();

    @State
    public String username;
    @State
    public ObservableInt searchButtonVisibility;
    @State
    public ObservableInt progressVisibility;
    @State
    public ObservableInt recyclerViewVisibility;
    @State
    public ObservableInt infoTextVisibility;
    @State
    public ObservableField<String> infoMessage;

    public ListViewModel(ActivityComponent component) {
        super(component);
        this.username = "";
        this.searchButtonVisibility = new ObservableInt(View.GONE);
        this.progressVisibility = new ObservableInt(View.INVISIBLE);
        this.recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        this.infoTextVisibility = new ObservableInt(View.VISIBLE);
        this.infoMessage = new ObservableField<>(context.getString(R.string.text_info));
    }

    public void onClickSearch(View view) {
        loadGithubRepos(username);
    }

    public boolean onUsernameEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String username = view.getText().toString();
            if (!TextUtils.isEmpty(username)) {
                loadGithubRepos(username);
                return true;
            }
        }
        return false;
    }

    public TextWatcher getUsernameChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = charSequence.toString();
                searchButtonVisibility.set(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void loadGithubRepos(String username) {
        progressVisibility.set(View.VISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);
        infoTextVisibility.set(View.INVISIBLE);

        githubService.publicRepositories(username)
                .compose(lifecycleProvider.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(subscribeScheduler)
                .doAfterTerminate(() -> progressVisibility.set(View.INVISIBLE))
                .subscribe(repositories -> {
                    Log.i(TAG, "Repose loaded " + repositories);
                    eventBus.post(new RepositoriesChangeEvent(repositories));
                    if (repositories.isEmpty()) {
                        infoMessage.set(context.getString(R.string.text_empty_repos));
                        infoTextVisibility.set(View.VISIBLE);
                    } else {
                        recyclerViewVisibility.set(View.VISIBLE);
                    }
                }, e -> {
                    Log.e(TAG, "Error loading GitHub repos ", e);
                    if (e instanceof HttpException && ((HttpException) e).code() == 404) {
                        infoMessage.set(context.getString(R.string.error_username_not_found));
                    } else {
                        infoMessage.set(context.getString(R.string.error_loading));
                    }
                    infoTextVisibility.set(View.VISIBLE);
                });
    }

}
