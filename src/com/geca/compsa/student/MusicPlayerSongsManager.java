package com.geca.compsa.student;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayerSongsManager {
	// SDCard Path

	String MEDIA_PATH;

	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	// Constructor
	public MusicPlayerSongsManager() {

	}

	/**
	 * Function to read all mp3 files from sdcard and store the details in
	 * ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList()throws IndexOutOfBoundsException,IllegalStateException  {
	
		File home = new File(MEDIA_PATH);
	
		if (home.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : home.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put(
						"songTitle",
						file.getName().substring(0,
								(file.getName().length() - 4)));
				song.put("songPath", file.getPath());

				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
	
		return songsList;
		
	
	}

	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3")||name.endsWith(".mp4") ||name.endsWith(".MP4") ||name.endsWith(".3gp") ||name.endsWith(".3GP")|| name.endsWith(".amr") ||name.endsWith(".AMR"));
		}
	}

	public void setPath(String f) {
		// TODO Auto-generated method stub
		MEDIA_PATH=f;
	}
	
}
