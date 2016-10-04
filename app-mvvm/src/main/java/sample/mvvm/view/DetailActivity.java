package sample.mvvm.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import org.parceler.Parcels;

import sample.github.model.Repository;
import sample.mvvm.R;
import sample.mvvm.databinding.DetailActivityBinding;
import sample.mvvm.viewmodel.DetailViewModel;

public class DetailActivity extends BaseActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, Parcels.wrap(repository));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DetailActivityBinding binding =  DataBindingUtil.setContentView(this, R.layout.detail_activity);
        Repository repository = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_REPOSITORY));
        binding.setViewModel(new DetailViewModel(component, repository));

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(repository.name);
    }

}
