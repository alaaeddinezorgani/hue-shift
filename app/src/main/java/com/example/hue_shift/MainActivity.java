package com.example.hue_shift;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button toggleButton;
    private SeekBar intensitySlider;
    private boolean isFilterOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);
        intensitySlider = findViewById(R.id.intensitySlider);

        intensitySlider.setProgress(50);

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
            );
            startActivity(intent);
            Toast.makeText(this, "Please grant overlay permission", Toast.LENGTH_LONG).show();
        }

        toggleButton.setOnClickListener(v -> {
            if (isFilterOn) {
                stopService(new Intent(this, FilterService.class));
                toggleButton.setText("Turn ON");
                isFilterOn = false;
            } else {
                startService(new Intent(this, FilterService.class));
                toggleButton.setText("Turn OFF");
                isFilterOn = true;
            }
        });

        intensitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                FilterService.setFilterIntensity(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}