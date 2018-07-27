package com.moonlikesinging.uiautomator2learn.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    private final String TAG = getClass().getName();

    public void StartApp(UiDevice uiDevice, String AppPackageName, String AppLaunchActivity)
    {
        Log.i(TAG, "Start App");
        try{
            uiDevice.executeShellCommand("am start -n " + AppPackageName + "/" + AppLaunchActivity);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void ClosedApp(UiDevice uiDevice, String AppPackageNmae) {
        Log.i(TAG, "close App: ");
        try {
            uiDevice.executeShellCommand("am force-stop " + AppPackageNmae);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartAPP(String sPackageName){
        Context mContext = InstrumentationRegistry.getContext();

        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(sPackageName);  //启动app
        mContext.startActivity(myIntent);
    }

//    public static ArrayList<String> execCMD(String command) throws Exception
//    {
//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec(command);
//
//        try (InputStream inputStream = process.getInputStream();
//             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//             BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
//        {
//            ArrayList<String> result = new ArrayList<>();
//            String line;
//            while ((line = bufferedReader.readLine()) !=null)
//            {
//                result.add(line);
//            }
//            return result;
//
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public String execCMD(String command){

        String result;

        try {
            Process p = Runtime.getRuntime().exec(command);
            StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
            StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
            new Thread(errorStream).start();
            new Thread(outputStream).start();
            p.waitFor();
            result = command + "\n" + outputStream.output.toString() + errorStream.output.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class StreamCaptureThread implements Runnable {

        InputStream stream;
        StringBuilder output;
        public StreamCaptureThread(InputStream stream) {
            this.stream = stream;
            this.output = new StringBuilder();
        }
        public void run(){
            try {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(this.stream));
                    String line = br.readLine();
                    while (line != null) {
                        if (line.trim().length() > 0) {
                            output.append(line).append("\n");
                        }
                        line = br.readLine();
                    }
                } finally {
                    if (stream != null) {
                        stream.close();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public boolean checkAppInstalled(String PackageName) throws Exception
    {
        String str = execCMD("pm list packages");
        String[] s = str.split("\n");
        ArrayList<String> result = new ArrayList<>(Arrays.asList(s));
        Log.i("Here***", result.toString());
        return result.contains("package:"+PackageName);
    }

//    public void installApp(String AppName) throws Exception
//    {
//        String AppPath = "/storage/emulated/0/Download/" +AppName;
//        String str =execCMD("pm install -r "+AppPath);
//        String[] s = str.split("\n");
//        ArrayList<String> result = new ArrayList<>(Arrays.asList(s));
//        Log.i("Here***", result.toString());
//        while(!result.contains("Success"))
//        {
//            Thread.sleep(5*1000);
//            Log.i("Here***", "Thread sleep 5 sec");
//        }
//    }
}
