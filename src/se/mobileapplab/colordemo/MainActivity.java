package se.mobileapplab.colordemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSeekBarChangeListener {
	TextView _colorDesc;
	View _color;
	SeekBar _red;
	SeekBar _green;
	SeekBar _blue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_colorDesc = (TextView)findViewById(R.id.color_desc);
		_color = findViewById(R.id.color);
		_red = (SeekBar)findViewById(R.id.red);
		_green = (SeekBar)findViewById(R.id.green);
		_blue = (SeekBar)findViewById(R.id.blue);
		
		_red.setOnSeekBarChangeListener(this);
		_green.setOnSeekBarChangeListener(this);
		_blue.setOnSeekBarChangeListener(this);
		_colorDesc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String link = String.format("http://www.color-hex.com/color/%02x%02x%02x", 
					_red.getProgress(), _green.getProgress(), _blue.getProgress());
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
				startActivity(intent);
			}
		});
		
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		int color = prefs.getInt("color", 0);
		_red.setProgress(Color.red(color));
		_green.setProgress(Color.green(color));
		_blue.setProgress(Color.blue(color));
		updateView();
	}
	
	private void updateView() {
		int color = Color.rgb(_red.getProgress(), _green.getProgress(), _blue.getProgress());
		_color.setBackgroundColor(color);
		
		String colorDesc = String.format("# %02x %02x %02x", 
			_red.getProgress(), _green.getProgress(), _blue.getProgress());
		_colorDesc.setText(colorDesc);
		
		SharedPreferences.Editor prefs = getPreferences(MODE_PRIVATE).edit();
		prefs.putInt("color", color);
		prefs.commit();
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		updateView();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
