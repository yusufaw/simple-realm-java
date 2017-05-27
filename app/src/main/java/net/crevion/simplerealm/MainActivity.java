package net.crevion.simplerealm;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
    }
}