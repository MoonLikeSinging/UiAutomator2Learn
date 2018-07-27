package com.moonlikesinging.uiautomator2learn;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.moonlikesinging.uiautomator2learn.Utils.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CalculatorTest {

    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    private final String Calculator_PACKAGE = "com.android.calculator2";


    @Before
    public void setup(){

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();

        Utils utils = new Utils();
        utils.StartAPP(Calculator_PACKAGE);
    }

    @Test
    public void checkPreconditions(){

        mDevice.waitForWindowUpdate(Calculator_PACKAGE, 10000);
        UiObject2 button7 = mDevice.wait(Until.findObject(By.res("com.android.calculator2","digit_7")), 5000);
        UiObject2 buttonX = mDevice.wait(Until.findObject(By.res("com.android.calculator2","op_mul")), 5000);
        UiObject2 button6 = mDevice.wait(Until.findObject(By.res("com.android.calculator2","digit_6")), 5000);
        UiObject2 buttonEqual = mDevice.wait(Until.findObject(By.res("com.android.calculator2","eq")), 5000);
        UiObject2 result = mDevice.wait(Until.findObject(By.res("com.android.calculator2","result")), 5000);

        button7.click();
        buttonX.click();
        button6.click();
        buttonEqual.click();
        Assert.assertEquals(result.getText(), "42");
    }
}
