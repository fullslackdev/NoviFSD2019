package dev.fullslack;

import ws.schild.jave.EncoderProgressListener;
import ws.schild.jave.MultimediaInfo;

public class ConvertProgressListener implements EncoderProgressListener {
    @Override
    public void message(String m) {
        System.out.println(m);
    }

    @Override
    public void progress(int p) {
        //Find 100% progress
        double progress = p / 10.00;
        System.out.println(progress+"%");
    }

    @Override
    public void sourceInfo(MultimediaInfo multimediaInfo) {
        System.out.println(multimediaInfo);
    }
}
