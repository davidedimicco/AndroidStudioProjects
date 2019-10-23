package it.unimi.dimicco.davide.myfirstchronometer;

import android.content.Context;

/**
 * Created by Davide on 02/09/2017.
 */

public class Chronometer implements Runnable{

    public static final long MILLIS_TO_MINUTES=60000;
    public static final long MILLIS_TO_HOURS=3600000;
    private Context context;
    private long startTime;
    private boolean isRunning;

    public Chronometer(Context ctx){
        context=ctx;
    }

    public void start(){
        startTime=System.currentTimeMillis();
        isRunning=true;
    }

    public void stop(){
        isRunning=false;
    }

    @Override
    public void run() {
        while(isRunning){
            long since =System.currentTimeMillis()-startTime;
            int seconds=(int)((since/1000)%60);
            int minutes=(int)((since/MILLIS_TO_MINUTES)%60);
            int hours=(int)((since/MILLIS_TO_HOURS)%24);
            int millis=(int)(since%1000);
            ((MainActivity)context).updateTimerText(
                    String.format("%02d:%02d:%02d:%03d",hours,minutes,seconds,millis)
            );
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
