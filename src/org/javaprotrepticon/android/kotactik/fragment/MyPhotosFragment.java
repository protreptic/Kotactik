package org.javaprotrepticon.android.kotactik.fragment;

import java.sql.Date;
import java.sql.SQLException;

import org.javaprotrepticon.android.kotactik.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.kotactik.storage.Storage;
import org.javaprotrepticon.android.kotactik.storage.model.Photo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;

public class MyPhotosFragment extends BaseEntityListFragment<Photo> {

	@Override
	protected Adapter<?> createAdapter() {
		return new DefaultAdapter() {
			
			@Override
			public void onBindViewHolder(DefaultViewHolder holder, int position) {
				final Photo photo = mEntityList.get(position);
				
				holder.title.setVisibility(View.GONE); 
				holder.subtitle.setVisibility(View.GONE); 
				holder.description.setVisibility(View.GONE); 

				//mImageLoader.displayImage(photo.getPhoto130(), holder.imageView);
			}
			
		};
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			default: {
				super.onOptionsItemSelected(item);
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void refreshData() {
		new DataLoader() {
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					mEntityList.addAll(mDao.queryForAll());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
		}.execute();
	}

	private VKRequestListener mRequestListener = new VKRequestListener() {
		
		@Override
		public void onComplete(VKResponse response) {
			try {
				JSONObject responceNode = response.json.getJSONObject("response");
				
				JSONArray photos = responceNode.getJSONArray("items");
				
				Storage storage = new Storage(getActivity());
				
				@SuppressWarnings("unchecked")
				Dao<Photo, Integer> dao = (Dao<Photo, Integer>) storage.createDao(Photo.class);
				
				try {
					for (int i = 0; i < photos.length(); i++) {
						JSONObject item = photos.getJSONObject(i);
						
						Photo photo = new Photo();
						photo.setId(item.getInt("id")); 
						photo.setPhoto75(item.getString("photo_75")); 
						photo.setPhoto130(item.getString("photo_130")); 
						photo.setPhoto604(item.getString("photo_604")); 
//						photo.setPhoto807(item.getString("photo_807"));  
//						photo.setPhoto1280(item.getString("photo_1280")); 
//						photo.setPhoto2560(item.getString("photo_2560")); 
						photo.setWidth(item.getInt("width"));  
						photo.setHeight(item.getInt("height"));  
						photo.setText(item.getString("text"));  
						photo.setDate(new Date(item.getLong("date")));
    						
						dao.createIfNotExists(photo);  
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				storage.closeConnection();
				
				refreshData();
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
		
		VKParameters vkParameters = new VKParameters();
		vkParameters.put(VKApiConst.ALBUM_ID, "wall");		
		
		VKRequest request = new VKRequest("photos.get", vkParameters);
		request.executeWithListener(mRequestListener);
	}
	
	@Override
	protected Class<Photo> getType() {
		return Photo.class;
	}
	
}
