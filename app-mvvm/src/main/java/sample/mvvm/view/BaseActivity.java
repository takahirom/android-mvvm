package sample.mvvm.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import sample.mvvm.Application;
import sample.mvvm.di.ActivityComponent;
import sample.mvvm.di.ActivityModule;

public class BaseActivity extends RxAppCompatActivity {

    protected ActivityComponent component;

    @Inject
    protected EventBus eventBus;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = Application.getComponent(this).activityComponent(new ActivityModule(this));
        this.component.inject(this);
    }

}


