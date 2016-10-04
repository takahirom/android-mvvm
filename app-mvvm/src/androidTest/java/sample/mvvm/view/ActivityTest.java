package sample.mvvm.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;

import sample.mvvm.Application;
import sample.mvvm.di.ApplicationComponent;
import sample.mvvm.di.DaggerApplicationComponent;
import sample.mvvm.di.TestModule;
import sample.mvvm.repository.TestGithubApiService;

public abstract class ActivityTest<T extends Activity> {

    @Rule
    public ActivityTestRule<T> activityTestRule
            = new ActivityTestRule<>(activityClass(), true, false);

    protected TestGithubApiService githubApiService;

    protected abstract Class<T> activityClass();

    @Before
    public void setup() {
        setupComponent();
        launchActivity();
    }

    private void setupComponent() {
        this.githubApiService = new TestGithubApiService();
        Application app = getApplication();
        ApplicationComponent component = DaggerApplicationComponent
                .builder()
                .applicationModule(new TestModule(githubApiService))
                .build();
        app.setComponent(component);
    }

    protected void launchActivity() {
        activityTestRule.launchActivity(new Intent());
    }

    protected Application getApplication() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (Application) instrumentation.getTargetContext().getApplicationContext();
    }

}
