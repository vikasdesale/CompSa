package com.geca.compsa.student;

import java.util.ArrayList;
import java.util.HashMap;

import com.geca.compsa.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MusicPlayerPlayList extends ListActivity {
	// Songs list
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState)throws IndexOutOfBoundsException,IllegalStateException  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mplaylist);

		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
		Bundle vg=getIntent().getExtras();
	       String f2=vg.getString("vvv");
		MusicPlayerSongsManager plm = new MusicPlayerSongsManager();
		plm.setPath(f2);
		// get all songs from sdcard
		this.songsList = plm.getPlayList();

		// looping through playlist
		for (int i = 0; i < songsList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> song = songsList.get(i);

			// adding HashList to ArrayList
			songsListData.add(song);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, songsListData,
				R.layout.mplaylist_item, new String[] { "songTitle" },
				new int[] { R.id.songTitle });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();
		// listening to single listitem click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)throws IndexOutOfBoundsException,IllegalStateException {
				// getting listitem index
				int songIndex = position;
               
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						MusicPlayer.class);
				// Sending songIndex to PlayerActivity
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				// Closing PlayListView
				finish();
			}
		});

	}
}
