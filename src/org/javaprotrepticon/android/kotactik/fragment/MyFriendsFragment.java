package org.javaprotrepticon.android.kotactik.fragment;

import java.sql.SQLException;

import org.javaprotrepticon.android.kotactik.R;
import org.javaprotrepticon.android.kotactik.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.kotactik.storage.Storage;
import org.javaprotrepticon.android.kotactik.storage.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;

public class MyFriendsFragment extends BaseEntityListFragment<User> {

	@Override
	protected Adapter<?> createAdapter() {
		return new RecyclerView.Adapter<UserViewHolder2>() {
			
			@Override
			public int getItemCount() {
				return mEntityList.size();
			}
			
			@Override
			public UserViewHolder2 onCreateViewHolder(ViewGroup parent, int position) {
				return new UserViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item2, parent, false));
			}

			@Override
			public void onBindViewHolder(UserViewHolder2 holder, int position) {
				final User user = mEntityList.get(position);
				
				holder.userAvatar.setImageDrawable(null);
				  
				holder.userName.setText(user.getFirstName() + "\n" + user.getLastName());
				holder.userName.setTypeface(mRobotoCondensedBold);
				
				holder.itemView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Bundle arguments = new Bundle();
						arguments.putInt("user_id", user.getId());

						Fragment fragment = new UserFragment();
						fragment.setArguments(arguments); 
						
			            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
			            fragmentTransaction.replace(R.id.content_frame, fragment);
			            fragmentTransaction.addToBackStack(null);
			            fragmentTransaction.commit();	
					}
				});
				
				imageLoader.displayImage(user.getPhoto100(), holder.userAvatar);
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
					mQueryBuilder.where().like("firstName", mQueryText).or().like("lastName", mQueryText);
					mQueryBuilder.orderBy("lastName", true);
					
					mEntityList.addAll(mQueryBuilder.query());
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
				
				JSONArray users = responceNode.getJSONArray("items");
				
				Storage storage = new Storage(getActivity());
				
				@SuppressWarnings("unchecked")
				Dao<User, Integer> dao = (Dao<User, Integer>) storage.createDao(User.class);
				
				try {
					for (int i = 0; i < users.length(); i++) {
						JSONObject item = users.getJSONObject(i);
						
						User user = new User();
						user.setId(item.getInt("id")); 
						user.setFirstName(item.getString("first_name"));
						user.setLastName(item.getString("last_name"));
						user.setPhoto50(item.getString("photo_50")); 
						user.setPhoto100(item.getString("photo_100")); 
						user.setOnline(item.getInt("online")); 
						user.setStatus(item.getString("status")); 
						
						dao.createOrUpdate(user);
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
	public void onResume() {
		super.onResume();
		
		VKParameters vkParameters = new VKParameters();
		vkParameters.put(VKApiConst.FIELDS, "id,first_name,last_name,photo_100,photo_50,status,online,photo_max");
		
		VKRequest request = new VKApiFriends().get(vkParameters);
		request.executeWithListener(mRequestListener);
	}
	
	@Override
	protected Class<User> getType() {
		return User.class;
	}
	
	public static class UserViewHolder extends RecyclerView.ViewHolder {
		
		private ImageView userAvatar;
		private TextView userName;
		private TextView userCompany;
		private TextView userStatus;
		
		public UserViewHolder(View itemView) {
			super(itemView); 
			
			userAvatar = (ImageView) itemView.findViewById(R.id.userAvatar);
			
			userName = (TextView) itemView.findViewById(R.id.userName);
			userCompany = (TextView) itemView.findViewById(R.id.userCompany);
			userStatus = (TextView) itemView.findViewById(R.id.userStatus);
		}
		
	}
	
	public static class UserViewHolder2 extends RecyclerView.ViewHolder {
		
		private ImageView userAvatar;
		private TextView userName;
		
		public UserViewHolder2(View itemView) {
			super(itemView); 
			
			userAvatar = (ImageView) itemView.findViewById(R.id.userAvatar);
			
			userName = (TextView) itemView.findViewById(R.id.userCompany);
		}
		
	}
	
}
