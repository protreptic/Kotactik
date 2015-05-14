package org.javaprotrepticon.android.kotactik.fragment;

import org.javaprotrepticon.android.kotactik.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;

public class MyProfileFragment extends Fragment {
	
	private ImageView userAvatar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.profile_fragment_layout, container, false);
		
		userAvatar = (ImageView) relativeLayout.findViewById(R.id.videoPreview);
		
		return relativeLayout;
	}
	
	private VKRequestListener mRequestListener = new VKRequestListener() {
		
		@Override
		public void onComplete(VKResponse response) {
			try {
				JSONArray users = response.json.getJSONArray("response");
				JSONObject item = users.getJSONObject(0);
				
				mImageLoader.displayImage(item.getString("photo_max"), userAvatar);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		
	};
	
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).build();
		
		mImageLoader.init(config);
		
		VKParameters vkParameters = new VKParameters();
		vkParameters.put(VKApiConst.FIELDS, "id,first_name,last_name,photo_100,photo_50,status,online,photo_max");
		
		VKRequest request = new VKRequest("users.get", vkParameters);
		request.executeWithListener(mRequestListener);
	}
	
}
