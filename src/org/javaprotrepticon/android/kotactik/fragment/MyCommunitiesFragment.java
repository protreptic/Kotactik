package org.javaprotrepticon.android.kotactik.fragment;

import java.sql.SQLException;

import org.javaprotrepticon.android.kotactik.R;
import org.javaprotrepticon.android.kotactik.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.kotactik.storage.Storage;
import org.javaprotrepticon.android.kotactik.storage.model.Community;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.MenuItem;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;

public class MyCommunitiesFragment extends BaseEntityListFragment<Community> {

	@Override
	protected Adapter<?> createAdapter() {
		return new DefaultAdapter() {
			
			@Override
			public void onBindViewHolder(DefaultViewHolder holder, int position) {
				final Community community = mEntityList.get(position);
				
				holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.cogs));
				
				holder.title.setText(community.getName());
				holder.title.setTypeface(mRobotoCondensedBold);
				
				holder.subtitle.setText("");
				holder.subtitle.setTypeface(mRobotoCondensedLight);
				
				holder.description.setText(""); 
				holder.description.setTypeface(mRobotoCondensedLight);

				//mImageLoader.displayImage(community.getPhoto50(), holder.imageView);
			}
			
		};
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case R.id.createReport: {
				
			} break;
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
					mEntityList.addAll(mQueryBuilder.where().like("name", mQueryText).query());
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
				
				JSONArray communities = responceNode.getJSONArray("items");
				
				Storage storage = new Storage(getActivity());
				
				@SuppressWarnings("unchecked")
				Dao<Community, Integer> dao = (Dao<Community, Integer>) storage.createDao(Community.class);
				
				try {
					for (int i = 0; i < communities.length(); i++) {
						JSONObject item = communities.getJSONObject(i);
						
						Community community = new Community();
						community.setId(item.getInt("id")); 
						community.setName(item.getString("name"));
						community.setPhoto50(item.getString("photo_50")); 
						 
						dao.createIfNotExists(community);
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
		vkParameters.put(VKApiConst.FIELDS, "id,name");
		vkParameters.put(VKApiConst.EXTENDED, 1);
		
		VKRequest request = new VKApiGroups().get(vkParameters);
		request.executeWithListener(mRequestListener);
	}
	
	@Override
	protected Class<Community> getType() {
		return Community.class;
	}
}
