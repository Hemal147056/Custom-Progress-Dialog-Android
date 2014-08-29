package ro.softwareinclude.custom_progress_dialog_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Sebastian Manolescu
 */

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }


    public void initData() {
        ((Button) findViewById(R.id.showDialog)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.showDialog: {
                CustomProgressDialog customProgressDialog = new CustomProgressDialog(this);
                customProgressDialog.show();
                break;
            }

        }
    }
}
