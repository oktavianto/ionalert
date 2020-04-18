package id.ionbit.ionalertsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import androidx.core.content.ContextCompat;

import id.ionbit.ionalert.IonAlert;

public class SampleActivity extends Activity implements View.OnClickListener{

    private int i = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //setTheme(R.style.Theme_AppCompat);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        findViewById(R.id.basic_test).setOnClickListener(this);
        findViewById(R.id.under_text_test).setOnClickListener(this);
        findViewById(R.id.error_text_test).setOnClickListener(this);
        findViewById(R.id.success_text_test).setOnClickListener(this);
        findViewById(R.id.warning_confirm_test).setOnClickListener(this);
        findViewById(R.id.warning_cancel_test).setOnClickListener(this);
        findViewById(R.id.custom_img_test).setOnClickListener(this);
        findViewById(R.id.progress_dialog).setOnClickListener(this);

        findViewById(R.id.checkbox1).setOnClickListener(this);
     //findViewById(R.id.edit_text_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basic_test:
                IonAlert sd = new IonAlert(this);
                sd.setTitleText("Title");
                sd.setContentText("Content");
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.show();
                break;
            case R.id.under_text_test:
                new IonAlert(this)
                        .setTitleText("Title Text")
                        .setContentText("It's pretty, isn't it?")
                        .showCancelButton(true)
                        .show();

                break;
            case R.id.error_text_test:
                new IonAlert(this, IonAlert.ERROR_TYPE)
                        .setTitleText("Terjadi Kesalahan")
                        .setContentText("Jemput penumpang, ketika sampai di tanda biru bunyikan KLAKSON dan tunggu penumpang sampai naik.")
                        .show();
                break;
            case R.id.success_text_test:
                new IonAlert(this, IonAlert.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();

                break;
            case R.id.warning_confirm_test:
                new IonAlert(this, IonAlert.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmTextSize(12)
                        .setConfirmClickListener(sDialog -> sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(IonAlert.SUCCESS_TYPE))
                        .show();
                break;
            case R.id.warning_cancel_test:
                new IonAlert(this, IonAlert.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No!")
                        .setConfirmText("Yes, please!")
                        .setConfirmTextSize(12)
                        .showCancelButton(true)
                        .setCancelClickListener(sDialog -> sDialog.setTitleText("Cancelled!")
                                .setContentText("Your imaginary file is safe :)")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(IonAlert.ERROR_TYPE))
                        .setConfirmClickListener(sDialog -> sDialog
                                .setTitleText("Your file deleted!")
                                .setContentText("Success delete file.")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                 .changeAlertType(IonAlert.SUCCESS_TYPE))
                        .show();
                break;
            case R.id.custom_img_test:
                new IonAlert(this, IonAlert.CUSTOM_IMAGE_TYPE)
                        .setTitleText("IonAlert")
                        .setContentText("Here's a custom image.")
                        .setCustomImage(R.mipmap.ic_launcher)
                        .show();
                break;
            case R.id.progress_dialog:
                new IonAlert(this, IonAlert.PROGRESS_TYPE)
                    .setSpinKit("DoubleBounce")
                    .showCancelButton(false)
                    .show();
                break;
            case R.id.checkbox1:
                IonAlert.DARK_STYLE = ((CheckBox) v).isChecked();
                break;
        }
    }
}
