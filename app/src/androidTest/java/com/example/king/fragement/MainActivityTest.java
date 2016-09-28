package com.example.king.fragement;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.test.ActivityUnitTestCase;
import android.view.View;

import com.example.king.fragement.main.MainActivity1;

/**
 * Created by Kings on 2016/4/9.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity1> {
    public MainActivityTest() {
        super(MainActivity1.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void testButtonHasClickListener(){
        startActivity(new Intent(getInstrumentation().getTargetContext(),MainActivity1.class),null,null);
//        startActivity(new Intent(Intent.ACTION_MAIN),null,null);
        View testButton = getActivity().findViewById(R.id.fab1);
        assertTrue("Button is Missing onclick Listner!",testButton.hasOnClickListeners());
    }

}
