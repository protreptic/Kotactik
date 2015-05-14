package org.javaprotrepticon.android.kotactik.fragment;

import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;

public class UserFragment extends Fragment {
	
	private TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		textView = new TextView(getActivity());
		
		return textView;
	}
	
	private VKRequestListener mRequestListener = new VKRequestListener() {
		
		@Override
		public void onComplete(VKResponse response) {
			try {
				textView.setText(response.json.toString(6)); 
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}

		@Override
		public void onError(VKError error) {
			Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
		}

		@Override
		public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
			Toast.makeText(getActivity(), String.format("Attempt %d/%d failed\n", attemptNumber, totalAttempts), Toast.LENGTH_LONG).show();
		}
		
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Integer userId = getArguments().getInt("user_id");
		
		VKParameters vkParameters = new VKParameters();
		vkParameters.put(VKApiConst.USER_IDS, userId);
		vkParameters.put(VKApiConst.FIELDS, "id,first_name,last_name,photo_50,status,online");
		
		VKRequest request = new VKRequest("users.get", vkParameters);
		request.executeWithListener(mRequestListener);
	}
	
}
