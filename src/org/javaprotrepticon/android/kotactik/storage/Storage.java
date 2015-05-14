package org.javaprotrepticon.android.kotactik.storage;

import java.io.File;
import java.sql.SQLException;

import org.javaprotrepticon.android.androidutils.Apps;
import org.javaprotrepticon.android.kotactik.storage.model.Audio;
import org.javaprotrepticon.android.kotactik.storage.model.Community;
import org.javaprotrepticon.android.kotactik.storage.model.Counters;
import org.javaprotrepticon.android.kotactik.storage.model.Photo;
import org.javaprotrepticon.android.kotactik.storage.model.User;
import org.javaprotrepticon.android.kotactik.storage.model.Video;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Storage {
	
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private JdbcPooledConnectionSource mConnection;
	
	public static void dropStorage(Context context) {
		File file = new File(context.getDir("data", Context.MODE_PRIVATE).getPath() + "/" + context.getPackageName() + "-" + Apps.getVersionName(context) + ".h2.db");
		
		if (file.delete()) {
			//Toast.makeText(context, "База данных успешно удалена", Toast.LENGTH_LONG).show();
		}
	}
	
	public static void initialize(Context context) {
		Storage storage = new Storage(context);
		
		try {
			TableUtils.createTableIfNotExists(storage.getConnection(), User.class);
			TableUtils.createTableIfNotExists(storage.getConnection(), Audio.class);
			TableUtils.createTableIfNotExists(storage.getConnection(), Video.class);
			TableUtils.createTableIfNotExists(storage.getConnection(), Photo.class);
			TableUtils.createTableIfNotExists(storage.getConnection(), Community.class);
			TableUtils.createTableIfNotExists(storage.getConnection(), Counters.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		storage.closeConnection();
	}
	
	public Storage(Context context) {
		String storageFolder = context.getDir("data", Context.MODE_PRIVATE).getPath() + "/" + context.getPackageName() + "-" + Apps.getVersionName(context);
		
		try {
			mConnection = new JdbcPooledConnectionSource("jdbc:h2:" + storageFolder + ";AUTO_SERVER=TRUE;IGNORECASE=TRUE;"); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JdbcPooledConnectionSource getConnection() {
		return mConnection;
	}
	
	public void closeConnection() {
		try {
			mConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Dao<?, Integer> createDao(Class<?> type) {
		Dao<?, Integer> result = null;
		
		try {
			result = (Dao<?, Integer>) DaoManager.createDao(mConnection, type);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
