package com.moonlikesinging.uiautomator2learn;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.moonlikesinging.uiautomator2learn.Utils.Utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class WeChatTest {

    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    private final String WeChat_PACKAGE = "com.tencent.mm";
    private final String AppName = "weixin.apk";


    @Before
    public void setup() throws Exception{

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();

        Utils utils = new Utils();
        if(utils.checkAppInstalled(WeChat_PACKAGE))
        {
            Log.i("TAG***", "app is installed");
            utils.StartAPP(WeChat_PACKAGE);
        }
        else
        {
//            utils.installApp(AppName);
//            utils.StartAPP(WeChat_PACKAGE);
            Log.w("TAG***","App is not installed");
        }
    }

    @Test
    public void test()
    {
        System.out.println("test111");
    }

}
