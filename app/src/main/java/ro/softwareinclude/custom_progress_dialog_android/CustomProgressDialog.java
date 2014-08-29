package ro.softwareinclude.custom_progress_dialog_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Sebastian Manolescu on 29.08.2014.
 */
public class CustomProgressDialog extends ProgressDialog {

    private Context context;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawingView drawingView = new DrawingView(context,true,"#51A0E0","#63C1CE");
        setContentView(drawingView);
        drawingView.displayProgress();

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
