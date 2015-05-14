package org.javaprotrepticon.android.kotactik.fragment;

import java.io.IOException;
import java.sql.SQLException;

import org.javaprotrepticon.android.kotactik.R;
import org.javaprotrepticon.android.kotactik.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.kotactik.storage.Storage;
import org.javaprotrepticon.android.kotactik.storage.model.Audio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;

public class MyMusicFragment extends BaseEntityListFragment<Audio> {

	private MediaPlayer mMediaPlayer = new MediaPlayer();
	
	@Override
	protected Adapter<?> createAdapter() {
		return new DefaultAdapter() {
			
			@Override
			public void onBindViewHolder(final DefaultViewHolder holder, int position) {
				final Audio audio = mEntityList.get(position);
				
				holder.online.setVisibility(View.GONE); 
				holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.file_sound)); 
				 
				holder.title.setText(audio.getArtist() + " - " + audio.getTitle());
				holder.title.setTypeface(mRobotoCondensedBold);
				
				holder.subtitle.setVisibility(View.GONE); 
				holder.description.setVisibility(View.GONE); 

				holder.itemView.setOnClickListener(new OnClickListener() { 
					
					@Override
					public void onClick(View view) {
				        try {
							if (mMediaPlayer.isPlaying()) {
								mMediaPlayer.release(); 
								mMediaPlayer = null;
							} 
							
							mMediaPlayer = new MediaPlayer();
							
							mMediaPlayer.setDataSource(audio.getUrl());
							mMediaPlayer.prepare();
							mMediaPlayer.start();
							
							holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.sign_right)); 
				        } catch (IOException e) {
				        	e.printStackTrace();
				        }
					}
				});
			}
			
		};
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.report_list_fragment_menu, menu);
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
					mEntityList.addAll(mQueryBuilder.where().like("artist", mQueryText).or().like("title", mQueryText).query());
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
				
				JSONArray items = responceNode.getJSONArray("items");
				
				Storage storage = new Storage(getActivity());
				
				@SuppressWarnings("unchecked")
				Dao<Audio, Integer> dao = (Dao<Audio, Integer>) storage.createDao(Audio.class);
				
				try {
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						
						Audio audio = new Audio();
						audio.setId(item.getInt("id")); 
						audio.setArtist(item.getString("artist"));
						audio.setTitle(item.getString("title"));
						audio.setUrl(item.getString("url")); 
						 
						dao.createIfNotExists(audio);
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
		
		VKRequest request = new VKRequest("audio.get");
		request.executeWithListener(mRequestListener);
	}
	
	@Override
	protected Class<Audio> getType() {
		return Audio.class;
	}
	
}
