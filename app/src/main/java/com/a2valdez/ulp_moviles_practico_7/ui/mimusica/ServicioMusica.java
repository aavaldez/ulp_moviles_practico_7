package com.a2valdez.ulp_moviles_practico_7.ui.mimusica;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;

import com.a2valdez.ulp_moviles_practico_7.R;

public class ServicioMusica extends Service {
    private MediaPlayer mp;

    public ServicioMusica() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.musica_4_3_1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        //return super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}