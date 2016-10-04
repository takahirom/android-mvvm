package sample.mvvm.bundler;

import android.os.Bundle;

import org.parceler.Parcels;

import java.util.List;

import icepick.Bundler;

public class ParcelBundler implements Bundler<Object> {

    @Override
    public void put(String key, Object value, Bundle bundle) {
        bundle.putParcelable(key, Parcels.wrap(value));
    }

    @Override
    public List<?> get(String key, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(key));
    }

}
