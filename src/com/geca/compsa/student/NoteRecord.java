package com.geca.compsa.student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.geca.compsa.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteRecord extends Activity implements OnClickListener {

	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "CompSa";
	private static final String AUDIO_RECORDER_FILE_EXT_AMR_NB = ".amr";
	private static final String AUDIO_RECORDER_FILE_EXT_MP3 = ".mp3";
	private MediaPlayer myPlayer;
	String filepath;
	String fileName, f;
	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	String read_data = null;
	private int currentFileIndex = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
			MediaRecorder.OutputFormat.THREE_GPP,
			MediaRecorder.OutputFormat.AMR_NB,
			MediaRecorder.OutputFormat.DEFAULT };
	private String file_exts[] = 
		   { AUDIO_RECORDER_FILE_EXT_MP4,
			AUDIO_RECORDER_FILE_EXT_3GP, 
			AUDIO_RECORDER_FILE_EXT_AMR_NB,
			AUDIO_RECORDER_FILE_EXT_MP3 };
	Button stopPlayBtn, playBtn, SaveFile, exit, bcut, bcopy, bpaste;
	Button playAll;
	String filepath2;
	EditText e1;
	Editable s1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noterec);
		e1 = (EditText) findViewById(R.id.editText1);
		editfile();
		setButtonHandlers();
		enableButtons(false);
		setFormatButtonCaption();
		SaveFile = (Button) findViewById(R.id.Save111);
		exit = (Button) findViewById(R.id.Exit111);
		playAll = (Button) findViewById(R.id.playl);
		playBtn = (Button) findViewById(R.id.play1);
		bcut = (Button) findViewById(R.id.bCut111);
		bcut.setOnClickListener(this);
		bcopy = (Button) findViewById(R.id.bCopy111);
		bcopy.setOnClickListener(this);
		bpaste = (Button) findViewById(R.id.bPaste111);
		bpaste.setOnClickListener(this);
	   run();
	}

	

	private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
	}

	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
		enableButton(R.id.btnFormat, !isRecording);
		enableButton(R.id.btnStop, isRecording);
	}

	private void setFormatButtonCaption() {
		((Button) findViewById(R.id.btnFormat))
				.setText(getString(R.string.audio_format) + " ("
						+ file_exts[currentFormat] + ")");
	}

	private String getFilename() {
		Bundle v = getIntent().getExtras();
		filepath = v.getString("path5");

		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		filepath2 = (file.getAbsolutePath()
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ System.currentTimeMillis() + file_exts[currentFormat]);
		return filepath2;
	}

	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();

			recorder = null;
		}
	}


	private void displayFormatDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String formats[] = { "MPEG 4", "3GPP", "AMR", "MP3" };

		builder.setTitle(getString(R.string.choose_format_title))
				.setSingleChoiceItems(formats, currentFormat,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								currentFormat = which;
								setFormatButtonCaption();

								dialog.dismiss();
							}
						}).show();
	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			//Toast.makeText(NoteRecord.this,
					//"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			//Toast.makeText(NoteRecord.this,
					//"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					//.show();
		}
	};

	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				Toast.makeText(NoteRecord.this, "Start Recording",
						Toast.LENGTH_SHORT).show();

				enableButtons(true);
				startRecording();

				break;
			}
			case R.id.btnStop: {
				Toast.makeText(NoteRecord.this, "Stop Recording",
						Toast.LENGTH_SHORT).show();
				enableButtons(false);
				stopRecording();

				break;
			}
			case R.id.btnFormat: {
				displayFormatDialog();

				break;
			}
			}
		}
	};

	public void play(View view) {

		try {
			myPlayer = new MediaPlayer();
			myPlayer.setDataSource(filepath2);
			myPlayer.prepare();
			myPlayer.start();

			playBtn.setEnabled(false);
			stopPlayBtn.setEnabled(true);

			Toast.makeText(getApplicationContext(),
					"Start play the recording...", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playAll(View view) {
		try {
			Class ourClass = Class
					.forName("com.geca.compsa.student.MusicPlayer");
			Intent i2 = new Intent(NoteRecord.this, ourClass);
			Bundle j = getIntent().getExtras();
			String MM = j.getString("pathMusic");
			i2.putExtra("vv1", MM);

			startActivity(i2);
			playAll.setEnabled(false);
			stopPlayBtn.setEnabled(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopPlay(View view) {
		try {
			if (myPlayer != null) {
				myPlayer.stop();
				myPlayer.release();
				myPlayer = null;
				playBtn.setEnabled(true);
				playAll.setEnabled(true);
				stopPlayBtn.setEnabled(false);

				Toast.makeText(getApplicationContext(),
						"Stop playing the recording...", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editfile()

	{

		Bundle v = getIntent().getExtras();
		fileName = v.getString("file5");
		Bundle v1 = getIntent().getExtras();
		f = v1.getString("path5");

		try {
			File myFile = new File(f + fileName);
			FileInputStream fis = new FileInputStream(myFile);

			byte[] dataArray = new byte[fis.available()];
			fis.read(dataArray);
			read_data = new String(dataArray);

			fis.close();

			e1.setText(read_data);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void SaveF(View v) {
		try {
			Bundle v2 = getIntent().getExtras();
			fileName = v2.getString("file5");
			Bundle v3 = getIntent().getExtras();
			f = v3.getString("path5");
			File myFile = new File(f + fileName);
			FileOutputStream fos = new FileOutputStream(myFile);

			String data = e1.getText().toString();
			fos.write(data.getBytes());
			fos.close();
			Toast.makeText(getApplicationContext(), "File is Saved",
					Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bCopy111:
			// String selectedText =
			// editbox2.getText().substring(editbox2.getSelectionStart(),
			// editbox2.getSelectionEnd());
			if (e1.getSelectionEnd() > e1.getSelectionStart()) {
				s1 = (Editable) e1.getText().subSequence(
						e1.getSelectionStart(), e1.getSelectionEnd());
			} else {
				s1 = (Editable) e1.getText().subSequence(e1.getSelectionEnd(),
						e1.getSelectionStart());
			}
			break;
		case R.id.bCut111:
			if (e1.getSelectionEnd() > e1.getSelectionStart()) {
				s1 = (Editable) e1.getText().subSequence(
						e1.getSelectionStart(), e1.getSelectionEnd());
			} else {
				s1 = (Editable) e1.getText().subSequence(e1.getSelectionEnd(),
						e1.getSelectionStart());
			}
			e1.getText().replace(
					Math.min(e1.getSelectionStart(), e1.getSelectionEnd()),
					Math.max(e1.getSelectionStart(), e1.getSelectionEnd()), "",
					0, 0);
			break;
		case R.id.bPaste111:
			e1.getText().replace(
					Math.min(e1.getSelectionStart(), e1.getSelectionEnd()),
					Math.max(e1.getSelectionStart(), e1.getSelectionEnd()), s1,
					0, s1.length());
			break;
		}
	}
	private void run() {
		// TODO Auto-generated method stub
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				play(v);
			}
		});
		playAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playAll(v);
			}
		});

		stopPlayBtn = (Button) findViewById(R.id.stopPlay);
		stopPlayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopPlay(v);
			}
		});
		SaveFile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveF(v);
			}

		});
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}