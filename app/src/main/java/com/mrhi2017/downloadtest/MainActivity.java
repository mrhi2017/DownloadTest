package com.mrhi2017.downloadtest;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    DownloadManager downloadManager;

    DownloadCompleteReceiver completeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void clickDown(View v){

        Uri uri=Uri.parse("http://image.itdonga.com/files/2015/08/24/00005.JPG");

        //다운로드 요청객체
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setTitle(uri.getLastPathSegment());
        request.setDescription("이미지 다운로드 중입니다.");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        //파일이 저장될 경로
        //1. 공통문서 외부저장소
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"00005.JPG");

        //2. 내 애플리케이션 내부
        //request.setDestinationInExternalFilesDir(this, getExternalFilesDir(null).getAbsolutePath(), uri.getLastPathSegment());

        //3.특정 경로
//        File file= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), uri.getLastPathSegment());
//        Uri dstUri= Uri.fromFile(file);
//        request.setDestinationUri(dstUri);

        downloadManager.enqueue(request);


        completeReceiver = new DownloadCompleteReceiver();
        IntentFilter filter= new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(completeReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        if(completeReceiver!=null) unregisterReceiver(completeReceiver);
        super.onDestroy();
    }

    class DownloadCompleteReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show();
        }
    }
}
