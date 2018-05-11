package com.ruixiangtong.xzc.setting.zxing.scan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.xzc.setting.R;
import com.ruixiangtong.xzc.setting.zxing.scan.camera.CameraManager;
import com.ruixiangtong.xzc.setting.zxing.scan.decoding.CaptureActivityHandler;
import com.ruixiangtong.xzc.setting.zxing.scan.decoding.InactivityTimer;
import com.ruixiangtong.xzc.setting.zxing.scan.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends ActionBarActivity implements Callback {
    public static final String QR_RESULT = "RESULT";

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    // private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    CameraManager cameraManager;

    private Toolbar toolbar;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_capture);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);

        //设置toolbar显示
        setSupportActionBar(toolbar);
        setTitle("扫一扫");

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        toolbar.setNavigationIcon(R.drawable.af_abs_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // CameraManager.init(getApplication());
        cameraManager = new CameraManager(getApplication());

        viewfinderView.setCameraManager(cameraManager);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            // CameraManager.get().openDriver(surfaceHolder);
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        showResult(obj, barcode);
    }

    private void showResult(final Result rawResult, Bitmap barcode) {

//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//		Drawable drawable = new BitmapDrawable(barcode);
//		builder.setIcon(drawable);
//
//		builder.setTitle("类型:" + rawResult.getBarcodeFormat() + "\n 结果：" + rawResult.getText());
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				Intent intent = new Intent();
//				intent.putExtra("result", rawResult.getText());
//				setResult(RESULT_OK, intent);
//				finish();
//			}
//		});
//		builder.setNegativeButton("重新扫描", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				cameraManager.startPreview();
//				restartPreviewAfterDelay(0L);
//			}
//		});
//		builder.setCancelable(false);
//		builder.show();
//		cameraManager.stopPreview();
//		XToast.show(this, rawResult.getText());


//		cameraManager.closeDriver();
//
//		restartPreviewAfterDelay(2000L);

//		LogUtil.getUtils().i("掃描結果====" + rawResult.getText());
//		 Intent intent = new Intent();
//		 intent.putExtra(QR_RESULT, rawResult.getText());
//		 setResult(RESULT_OK, intent);
//		 finish();

        LogUtil.getUtils().d("rawResult  : " + rawResult);

        String qrMessage = rawResult.getText();
        LogUtil.getUtils().d("scan result : " + qrMessage);
        if (TextUtils.isEmpty(qrMessage)) return;

//        qrMessage = "{'cardNo':'78646a6178646a61365231303219447d'," +
//                "'businessIdentity’:'USBKeyUnlock'," +
//                "'cardSn':'621000000014'}";

        //测试代码
        //判断是否是扫一扫添加好友或者扫描解锁需要的正确内容
        if (qrMessage.startsWith("1#") || qrMessage.contains("businessIdentity")) {
            if (qrMessage.contains("businessIdentity")) {//假设是解锁所需内容

//                QRType businessType = QRUtil.getQRBusinessIdentity(qrMessage);
//                switch (businessType) {
//                    case Error:
//                        XToast.show(this, "二维码扫描失败，请重试");
//                        break;
//                    case USBKeyUnlock:
//                        QRHandleEvent event = new QRHandleEvent();
//                        event.setMessage(qrMessage);
//                        event.setActivity(this);
//                        event.setContext(this);
//                        BusProvider.getMainProvider().post(event);
//                        break;
//                    default:
//                        break;
//                }
            } else if (rawResult.getText().startsWith("1#")) {//扫一扫添加好友
                showResultBack(rawResult.getText());
            }

        } else {
//            XToast.showLong(this, "无效的二维码");
            restartPreviewAfterDelay(3000);

        }
    }



    /**
     * 传递扫描结果回
     *
     * @param message
     */
    public void showResultBack(String message) {
        Intent intent = new Intent();
        intent.putExtra(QR_RESULT, message);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd("qrbeep.ogg");
                this.mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}