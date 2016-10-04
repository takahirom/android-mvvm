package sample.github;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GithubApiInterceptor implements Interceptor {

    private static final String TAG = GithubApiInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder reqBuilder = request.newBuilder();
        if (!TextUtils.isEmpty(BuildConfig.GITHUB_API_AUTH_TOKEN)) {
            reqBuilder.addHeader("Authorization", "token " + BuildConfig.GITHUB_API_AUTH_TOKEN);
        }

        Response response = chain.proceed(reqBuilder.build());

        String json = response.body().string();
        Log.d(TAG, String.format("JSON response is: %s", json));
        response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), json)).build();

        return response;
    }

}
