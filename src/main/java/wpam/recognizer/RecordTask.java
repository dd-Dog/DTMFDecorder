package wpam.recognizer;

import java.util.concurrent.BlockingQueue;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.util.Log;

public class RecordTask extends AsyncTask<Void, Object, Void> {


    private static final String TAG = "RecordTask";
    int mFrequency = 16000;
    int mChannelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int mAudioEncoding = AudioFormat.ENCODING_PCM_16BIT;

    int mBlockSize = 1024;

    Controller mController;
    BlockingQueue<DataBlock> mBlockingQueue;

    public RecordTask(Controller controller, BlockingQueue<DataBlock> blockingQueue) {
        this.mController = controller;
        this.mBlockingQueue = blockingQueue;
    }

    @Override
    protected Void doInBackground(Void... params) {

        int bufferSize = AudioRecord.getMinBufferSize(mFrequency, mChannelConfiguration, mAudioEncoding);

        Log.d(TAG, "audio source =" + mController.getAudioSource());
        AudioRecord audioRecord = new AudioRecord(mController.getAudioSource(), mFrequency, mChannelConfiguration, mAudioEncoding, bufferSize);

        try {
            short[] buffer = new short[mBlockSize];
            audioRecord.startRecording();
            while (mController.isStarted()) {
                int bufferReadSize = audioRecord.read(buffer, 0, mBlockSize);
                DataBlock dataBlock = new DataBlock(buffer, mBlockSize, bufferReadSize);
                mBlockingQueue.put(dataBlock);
            }

        } catch (Throwable t) {
            Log.e("AudioRecord", "Recording Failed");
            t.printStackTrace();
        }

        audioRecord.stop();

        return null;
    }
}