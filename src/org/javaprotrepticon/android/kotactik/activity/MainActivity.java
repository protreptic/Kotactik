package org.javaprotrepticon.android.kotactik.activity;

import org.javaprotrepticon.android.androidutils.Fonts;
import org.javaprotrepticon.android.kotactik.R;
import org.javaprotrepticon.android.kotactik.fragment.LoginFragment;
import org.javaprotrepticon.android.kotactik.fragment.LogoutFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyCommunitiesFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyFeedbackFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyFriendsFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyMessagesFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyMusicFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyPhotosFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyProfileFragment;
import org.javaprotrepticon.android.kotactik.fragment.MySettingsFragment;
import org.javaprotrepticon.android.kotactik.fragment.MyVideosFragment;
import org.javaprotrepticon.android.kotactik.storage.model.Counters;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.dialogs.VKCaptchaDialog;

public class MainActivity extends ActionBarActivity {

    private int[] mMenuIcons = new int[] { 
    		R.drawable.profile,
    		R.drawable.profile_group,
    		R.drawable.file_picture,
    		R.drawable.file_video,
    		R.drawable.file_sound,
    		R.drawable.bubbles_alt,
    		R.drawable.building,
    		R.drawable.cog,
    		R.drawable.lock_open,
    		R.drawable.lock
    		};
    private int[] mMenuItems = new int[] { 
    		R.string.profile,
    		R.string.friends, 
    		R.string.pictures,
    		R.string.videos,
    		R.string.audios,
    		R.string.messages,
    		R.string.communities,
    		R.string.settings,
    		R.string.login,
    		R.string.logout
    		};
	
	public static class MenuItemViewHolder {

		public ImageView image1;
		public TextView text1;
		public TextView text2;
		
	}
	
    private class MenuItemAdapter extends BaseAdapter {

		private Typeface mRobotoCondensedBold;
		
		public MenuItemAdapter() {
			mRobotoCondensedBold = Fonts.get(getBaseContext()).getTypeface("RobotoCondensed-Regular"); 
		}
    	
		@Override
		public int getCount() {
			return mMenuItems.length;
		}
		
		@Override
		public Object getItem(int position) {
			return mMenuItems[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MenuItemViewHolder holder;
			
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.drawer_list_item, parent, false);
				
				holder = new MenuItemViewHolder();
				holder.image1 = (ImageView) convertView.findViewById(R.id.image1);
				
				holder.text1 = (TextView) convertView.findViewById(R.id.text1);
				holder.text1.setTypeface(mRobotoCondensedBold);
				
				holder.text2 = (TextView) convertView.findViewById(R.id.text2);
				holder.text2.setTypeface(mRobotoCondensedBold);
				
				convertView.setTag(holder); 
			} else {
				holder = (MenuItemViewHolder) convertView.getTag();
			}
			
			holder.text1.setText(getString((Integer) getItem(position))); 
			holder.image1.setImageDrawable(getResources().getDrawable(mMenuIcons[position])); 
			holder.text2.setText(""); 
			
			switch (position) {
				case 1: {
					if (countersObject.getFriends() != null) {
						holder.text2.setText(countersObject.getFriends().toString()); 
					}
				} break;
				case 5: {
					if (countersObject.getMessages() != null) {
						holder.text2.setText(countersObject.getMessages().toString()); 
					}
				} break;
				default: {
					
				} break;
			}
			
			return convertView;
		}
    	
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.main_activity);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(new DefaultDrawerListener());
        mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        
        mLeftDrawer = (LinearLayout) findViewById(R.id.left_drawer);
		
        mMenuItemAdapter = new MenuItemAdapter();
        
        mLeftDrawerList = (ListView) findViewById(R.id.left_drawer_list);
        mLeftDrawerList.setAdapter(mMenuItemAdapter); 
        mLeftDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        initToolbar();
        initVk();
	}

	private MenuItemAdapter mMenuItemAdapter;
	
    private final VKSdkListener sdkListener = new VKSdkListener() {
    	
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(sMyScope);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            new AlertDialog.Builder(VKUIHelper.getTopActivity()).setMessage(authorizationError.toString()).show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
        	selectItem(0);
        	
        	Log.d("", "Access token: " + newToken.accessToken);
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
        	selectItem(0);
        	
        	Log.d("", "Access token: " + token.accessToken);
        }
        
    };
	
    private Counters countersObject = new Counters();
    
    private void updateCounters() {
    	VKParameters vkParameters = new VKParameters();
		VKRequest request2 = new VKRequest("account.getCounters", vkParameters);
		request2.executeWithListener(new VKRequestListener() {
			
			public void onComplete(VKResponse response) {
				try {
					JSONObject counters = response.json.getJSONObject("response");
					
					countersObject.setMessages(counters.getInt("messages"));
					countersObject.setFriends(counters.getInt("friends")); 
					countersObject.setPhotos(counters.getInt("photos"));
					countersObject.setVideos(counters.getInt("videos"));
					countersObject.setNotes(counters.getInt("notes"));
					countersObject.setGifts(counters.getInt("gifts"));
					countersObject.setEvents(counters.getInt("events"));
					countersObject.setGroups(counters.getInt("groups"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				mMenuItemAdapter.notifyDataSetChanged();
			};
			
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		VKUIHelper.onActivityResult(this, requestCode, resultCode, data); 
	}
    
	private void initVk() {
        VKUIHelper.onCreate(this);
        VKSdk.initialize(sdkListener, "4894830");
        if (VKSdk.wakeUpSession()) {
        	selectItem(0);
        }
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
    @Override
    public void onBackPressed() {
    	if (mDrawerLayout.isDrawerOpen(mLeftDrawer)) {
    		mDrawerLayout.closeDrawer(mLeftDrawer); return;
    	}
    	
    	super.onBackPressed();
    }
    
	private void initToolbar() {
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mToolBar.setTitle("");
		mToolBar.setSubtitle("");
		mToolBar.setBackgroundColor(getResources().getColor(R.color.bg_actionbar));  
		
	    setSupportActionBar(mToolBar);
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
        
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
	    
	    return true;
	}

	protected ActionBarDrawerToggle mDrawerToggle;
	protected DrawerLayout mDrawerLayout;
	protected LinearLayout mLeftDrawer;
	protected ListView mRightDrawer;
	protected ListView mLeftDrawerList;
	
	protected Toolbar mToolBar;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case android.R.id.home: {
				mDrawerLayout.openDrawer(mLeftDrawer);
			} break;
			default: {
				super.onOptionsItemSelected(item);
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        
        VKUIHelper.onResume(this);
        
        if (VKSdk.isLoggedIn()) {
        	selectItem(0); 
        } else {
        	selectItem(9);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        VKUIHelper.onDestroy(this);
    }
	
    private MyProfileFragment mProfileFragment;
    private MyFriendsFragment mFriendsFragment;
    private MyPhotosFragment mPhotosFragment;
    private MyVideosFragment mVideosFragment;
	private MyMusicFragment mMusicFragment;
	private MyMessagesFragment mMessagesFragment;
	private MyCommunitiesFragment mCommunitiesFragment;
	private MyFeedbackFragment mFeedbackFragment;
	private MySettingsFragment mSettingsFragment;
	
	private LoginFragment mLoginFragment;
	private LogoutFragment mLogoutFragment;
	
	private void selectItem(int position) {
    	mLeftDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mLeftDrawer);
    	      
        Fragment fragment = null;
        
        switch (position) {
			case 0: {
				if (mProfileFragment == null) {
					mProfileFragment = new MyProfileFragment();
				}
				
				fragment = mProfileFragment;
			} break;
			case 1: {
				if (mFriendsFragment == null) {
					mFriendsFragment = new MyFriendsFragment();
				}
				
				fragment = mFriendsFragment;
			} break;
			case 2: {
				if (mPhotosFragment == null) {
					mPhotosFragment = new MyPhotosFragment();
				}
				
				fragment = mPhotosFragment;
			} break;
			case 3: {
				if (mVideosFragment == null) {
					mVideosFragment = new MyVideosFragment();
				}
				
				fragment = mVideosFragment;
			} break;
			case 4: {
				if (mMusicFragment == null) {
					mMusicFragment = new MyMusicFragment();
				}
				
				fragment = mMusicFragment;
			} break;
			case 5: {
				if (mMessagesFragment == null) {
					mMessagesFragment = new MyMessagesFragment();
				}
				
				fragment = mMessagesFragment;
			} break;
			case 6: {
				if (mCommunitiesFragment == null) {
					mCommunitiesFragment = new MyCommunitiesFragment();
				}
				
				fragment = mCommunitiesFragment;
			} break;
			case 7: {
				if (mFeedbackFragment == null) {
					mFeedbackFragment = new MyFeedbackFragment();
				}
				
				fragment = mFeedbackFragment;
			} break;
			case 8: {
				if (mSettingsFragment == null) {
					mSettingsFragment = new MySettingsFragment();
				}
				
				fragment = mSettingsFragment;
			} break;
			case 9: {
				if (mLoginFragment == null) {
					mLoginFragment = new LoginFragment();
				}
				
				fragment = mLoginFragment;
			} break;
			case 10: {
				if (mLogoutFragment == null) {
					mLogoutFragment = new LogoutFragment();
				}
				
				fragment = mLogoutFragment;
			} break;
			default: {
				return;
			}
		}
        
        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
        	getSupportFragmentManager().popBackStackImmediate();
        }
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
    
	public class DefaultDrawerListener implements DrawerLayout.DrawerListener {
		
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
            
            updateCounters();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }
	
    public static final String[] sMyScope = new String[] {
        VKScope.FRIENDS,
        VKScope.WALL,
        VKScope.PHOTOS,
        VKScope.NOHTTPS,
        VKScope.AUDIO,
        VKScope.GROUPS,
        VKScope.MESSAGES,
        VKScope.VIDEO,
        VKScope.PAGES
    };

}
