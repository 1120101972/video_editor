
package org.zpdian.coverflow;

import org.zpdian.coverflow.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class GallaryBrowser extends Activity implements OnClickListener {

    private ImageButton mDisplay;

    private ImageButton mWrite;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CoverFlow cf = (CoverFlow) this.findViewById(R.id.cover_flow);
        ImageAdapter adapter = new ImageAdapter(this);
        cf.setAdapter(adapter);
        cf.setGravity(Gravity.CENTER_HORIZONTAL);
        mDisplay = (ImageButton) findViewById(R.id.display);
        mWrite = (ImageButton) findViewById(R.id.write);
        mDisplay.setOnClickListener(this);
        mWrite.setOnClickListener(this);
    }

    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.display:
//                Log.d("myTag", "left button");
//                break;
//            case R.id.write:
//                Log.d("myTag", "right button");
//                break;
//            default:
//                break;
//        }
    }

}
