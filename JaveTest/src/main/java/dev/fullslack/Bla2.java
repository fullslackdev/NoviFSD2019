package dev.fullslack;

import ws.schild.jave.*;

//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

public class Bla2 {
    public static void main(String[] args) {
        try {
            convert();
            //getInfo();
            getInfo("D:\\Test\\valkyrie.ogg");
            //getSupportedEncodingFormats();
            //getAudioEncoders();
        } catch (EncoderException ex) {
            ex.printStackTrace();
        }
    }

    public static void convert() throws EncoderException {
        convert("valkyrie.wav","valkyrie.ogg");
    }

    public static void convert(String inputFileName, String outputFileName) throws EncoderException {
        ConvertProgressListener listener = new ConvertProgressListener();

        File source = new File("D:\\Test\\"+inputFileName);
        File target = new File("D:\\Test\\"+outputFileName);

        //Audio attributes
        AudioAttributes audio = new AudioAttributes();
        //audio.setCodec("libmp3lame");
        audio.setCodec("libvorbis");
        audio.setBitRate(320000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        //Encoding attributes
        EncodingAttributes attrs = new EncodingAttributes();
        //attrs.setFormat("mp3");
        attrs.setFormat("ogg");
        attrs.setAudioAttributes(audio);

        //Encode
        Encoder encoder = new Encoder();

        MultimediaObject multimediaObject = new MultimediaObject(source);

        encoder.encode(multimediaObject, target, attrs, listener);
    }

    public static void getInfo() throws EncoderException {
        getInfo("bla.mp3");
    }

    public static void getInfo(String filenameToCheck) throws EncoderException {
        File source = new File(filenameToCheck);
        MultimediaObject multimediaObject = new MultimediaObject(source);
        MultimediaInfo info = multimediaObject.getInfo();

        System.out.println(info.getFormat());
        System.out.println(info.getAudio().getBitRate());
    }

    public static void getSupportedEncodingFormats() throws EncoderException {
        System.out.println("getSupportedEncodingFormats");
        Encoder instance = new Encoder();
        String[] result = instance.getSupportedEncodingFormats();
        System.out.println(Arrays.toString(result));
        //assertTrue("No supported encoding formats found", result != null && result.length > 0);
    }

    public static void getAudioEncoders() throws EncoderException {
        System.out.println("getAudioEncoders");
        Encoder instance = new Encoder();
        String[] result = instance.getAudioEncoders();
        System.out.println(Arrays.toString(result));
    }
}
