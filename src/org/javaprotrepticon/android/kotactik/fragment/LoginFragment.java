package org.javaprotrepticon.android.kotactik.fragment;

import org.javaprotrepticon.android.kotactik.activity.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.VKSdk;
import org.javaprotrepticon.android.kotactik.R;

public class LoginFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        getView().findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.authorize(MainActivity.sMyScope, true, false);
            }
        });

        getView().findViewById(R.id.force_oauth_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.authorize(MainActivity.sMyScope, true, true);
            }
        });
    }
}
