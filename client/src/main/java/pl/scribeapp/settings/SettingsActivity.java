package pl.scribeapp.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import pl.scribeapp.R;

public class SettingsActivity extends PreferenceActivity {
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {       
		 super.onCreate(savedInstanceState);       
		 addPreferencesFromResource(R.xml.preferences);		 
	 }
	 
}
