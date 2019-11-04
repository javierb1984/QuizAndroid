package com.example.quiz;

import android.os.Handler;
import android.widget.TextView;

public class Chronometer implements Runnable {

    private String tag;
    private int seconds, minutes;
    private TextView showView;
    private String output;
    private boolean stop;
    private Handler write;

    public Chronometer(TextView showView, String tag, Handler write) {
        this.write = write;
        this.tag = tag;
        this.showView = showView;
        this.seconds = 0;
        this.minutes = 0;
        this.stop = false;
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                Thread.sleep(1000);
                output = "";

                seconds++;
                if(seconds == 60){
                    minutes++;
                    seconds = 0;
                }

                output += tag+": ";

                if(minutes > 9){
                    output += minutes;
                    stop = true;
                }
                else output += "0"+minutes;

                output += ":";

                if(seconds > 9) output += seconds;
                else output += "0"+seconds;

                write.post(new Runnable() {
                    @Override
                    public void run() {
                        showView.setText(output);
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return;
        }
    }

    public String stop(){
        this.stop = true;
        return output;
    }
}
