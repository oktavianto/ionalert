package id.ionbit.ionalert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.Objects;

import id.ionbit.ionalert.R;

public class IonAlert extends AlertDialog implements View.OnClickListener {
    private final AnimationSet mModalInAnim, mModalOutAnim, mErrorXInAnim;//,mSuccessLayoutAnimSet;
    private final Animation mOverlayOutAnim, mImageAnim;//,mSuccessBowAnim;

    private TextView mTitleTextView, mContentTextView;
    private ImageView mErrorX, mSuccessTick, mCustomImage;
    private Drawable mCustomImgDrawable;
    private Button mConfirmButton, mCancelButton;
    private LinearLayout mAlert;
    private int mColor;
    private int mCancelColor;
    private View mDialogView;//,mSuccessLeftMask,mSuccessRightMask;
    private View mCustomView;
    private FrameLayout mCustomViewContainer;
    ProgressBar mProgressBar;

    private String mTitleText, mContentText, mCancelText, mConfirmText;

    private boolean mShowCancel, mShowContent, mShowTitleText, mCloseFromCancel;
    private int contentTextSize = 0;

    private FrameLayout mErrorFrame, mSuccessFrame, mProgressFrame, mWarningFrame;
    //private SuccessTickView mSuccessTick;
    private IonAlert.ClickListener mCancelClickListener;
    private IonAlert.ClickListener mConfirmClickListener;

    private int mAlertType;
    public static final int NORMAL_TYPE = 0;


    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;


    public static boolean DARK_STYLE = false;
    private int mConfirmTextSize, mCancelTextSize;
    private String spinStyle = "RotatingPlane";
    private Sprite sprite;
    private String mSpinColor = String.format("#%06X", (0xFFFFFF & R.attr.colorPrimary));;

    public interface ClickListener {
        void onClick(IonAlert ionAlert);
    }

    public static final int INPUT_TYPE = 6;
    private EditText mEditText;


    public IonAlert(Context context) {
        this(context, NORMAL_TYPE);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        mDialogView = Objects.requireNonNull(getWindow()).getDecorView().findViewById(android.R.id.content);
        mAlert = findViewById(R.id.alert_background);
        mTitleTextView = findViewById(R.id.title_text);
        mContentTextView = findViewById(R.id.content_text);
        mErrorFrame = findViewById(R.id.error_frame);
        mErrorX = mErrorFrame != null ? mErrorFrame.findViewById(R.id.error_x) : null;
        mEditText = findViewById(R.id.edit_text);
        mSuccessFrame = findViewById(R.id.success_frame);
        mProgressFrame = findViewById(R.id.progress_dialog);
        mProgressBar = findViewById(R.id.spin_kit);
        mSuccessTick = mSuccessFrame.findViewById(R.id.success_x);

        mCustomImage = findViewById(R.id.custom_image);
        mWarningFrame = findViewById(R.id.warning_frame);
        mCustomViewContainer = findViewById(R.id.custom_view_container);

        mConfirmButton = findViewById(R.id.custom_confirm_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        setCustomView(mCustomView);
        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setConfirmButtonColor(mColor);
        setCancelButtonColor(mCancelColor);

        showCancelButton(mShowCancel);
        setConfirmTextSize(mConfirmTextSize);
        setCancelTextSize(mCancelTextSize);
        setSpinKit(spinStyle);
        setSpinColor(mSpinColor);

        changeAlertType(mAlertType, true);
    }

    public IonAlert(Context context, int alertType) {
        super(context, DARK_STYLE ? R.style.alert_dialog_dark : R.style.alert_dialog_light);

        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mAlertType = alertType;
        mImageAnim = AnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        //mSuccessBowAnim = AnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        //mSuccessLayoutAnimSet = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        Objects.requireNonNull(mModalOutAnim).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(() -> {
                    if (mCloseFromCancel) {
                        IonAlert.super.cancel();
                    } else {
                        IonAlert.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = Objects.requireNonNull(getWindow()).getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);

        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void restore() {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);
        mConfirmButton.setVisibility(View.VISIBLE);

        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
    }

    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mImageAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startAnimation(mImageAnim);
            mSuccessFrame.startAnimation(mImageAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        if (mDialogView != null) {
            if (!fromCreate) {
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mAlert.setVisibility(View.VISIBLE);
                    mAlert.setBackgroundResource(R.drawable.red_background);
                    mErrorFrame.setVisibility(View.VISIBLE);
                    mCancelButton.setTextColor(getContext().getResources().getColor(R.color.color_red));
                    mConfirmButton.setBackgroundColor(getContext().getResources().getColor(R.color.color_red));
                    break;
                case SUCCESS_TYPE:
                    mAlert.setVisibility(View.VISIBLE);
                    mAlert.setBackgroundResource(R.drawable.green_background);
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    mCancelButton.setTextColor(getContext().getResources().getColor(R.color.color_green));
                    mConfirmButton.setBackgroundColor(getContext().getResources().getColor(R.color.color_green));
                    break;
                case WARNING_TYPE:
                    mAlert.setVisibility(View.VISIBLE);
                    mAlert.setBackgroundResource(R.drawable.yellow_background);
                    mWarningFrame.setVisibility(View.VISIBLE);
                    mCancelButton.setTextColor(getContext().getResources().getColor(R.color.color_yellow));
                    mConfirmButton.setBackgroundColor(getContext().getResources().getColor(R.color.color_yellow));
                    break;
                case CUSTOM_IMAGE_TYPE:
                    mAlert.setVisibility(View.VISIBLE);
                    mAlert.setBackgroundResource(R.drawable.dialoglight);
                    mCustomImage.setVisibility(View.VISIBLE);
                    setConfirmButtonColor(mColor);
                    break;
                case PROGRESS_TYPE:
                    mAlert.setVisibility(View.VISIBLE);
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
                    break;
                case INPUT_TYPE:
                    mEditText.requestFocus();
                    //mEditTextFrame.setVisibility(View.VISIBLE);
                    setConfirmButtonColor(mColor);
                    break;
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public IonAlert setSpinColor(String color) {
        mSpinColor = color;
        if (mSpinColor != null && sprite != null) {
            sprite.setColor(Color.parseColor(mSpinColor));
        }
        return this;
    }

    public IonAlert setSpinKit(String style) {
        spinStyle = style;
        if (mProgressBar != null && spinStyle != null) {
            switch (spinStyle) {
                case "DoubleBounce":
                    sprite = new DoubleBounce();
                    break;
                case "Wave":
                    sprite = new Wave();
                    break;
                case "WanderingCubes":
                    sprite = new WanderingCubes();
                    break;
                case "Pulse":
                    sprite = new Pulse();
                    break;
                case "ChasingDots":
                    sprite = new ChasingDots();
                    break;
                case "ThreeBounce":
                    sprite = new ThreeBounce();
                    break;
                case "Circle":
                    sprite = new Circle();
                    break;
                case "CubeGrid":
                    sprite = new CubeGrid();
                    break;
                case "FadingCircle":
                    sprite = new FadingCircle();
                    break;
                case "FoldingCube":
                    sprite = new FoldingCube();
                    break;
                case "RotatingCircle":
                    sprite = new RotatingCircle();
                    break;
                case "RotatingPlane":
                default:
                    sprite = new RotatingPlane();
                    break;
            }
            setSpinColor(mSpinColor);
            mProgressBar.setIndeterminateDrawable(sprite);
        }
        return this;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }

    public IonAlert setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            showTitleText();
            mTitleTextView.setText(Html.fromHtml(mTitleText));
        }
        return this;
    }

    private void showTitleText() {
        mShowTitleText = true;
        if (mTitleTextView != null) {
            mTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    public IonAlert setCustomImage(int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    private IonAlert setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public IonAlert setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText();
            if (contentTextSize != 0) {
                mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, spToPx(contentTextSize, getContext()));
            }
            mContentTextView.setText(Html.fromHtml(mContentText));
            mCustomViewContainer.setVisibility(View.GONE);
        }
        return this;
    }

        public IonAlert showCancelButton(boolean isShow){
            mShowCancel = isShow;
            if (mCancelButton != null) {
                mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
            }
            return this;
        }

        private void showContentText() {
            mShowContent = true;
            if (mContentTextView != null) {
                mContentTextView.setVisibility(View.VISIBLE);
            }
        }

        public IonAlert setCancelClickListener (ClickListener listener){
            mCancelClickListener = listener;
            return this;
        }

        public IonAlert setConfirmClickListener (ClickListener listener){
            mConfirmClickListener = listener;
            return this;
        }

        protected void onStart () {
            mDialogView.startAnimation(mModalInAnim);
            playAnimation();
        }

        @Override
        public void cancel () {
            dismissWithAnimation(true);
        }

        private IonAlert setConfirmButtonColor(int color){
            mColor = color;
            if (mConfirmButton != null && mColor != 0) {
                mConfirmButton.setBackgroundColor(mColor);
            }
            return this;
        }

        private IonAlert setCancelButtonColor(int color){
            mCancelColor = color;
            if (mCancelButton != null && mCancelColor != 0) {
                mCancelButton.setTextColor(mCancelColor);
            }
            return this;
        }

        public void dismissWithAnimation () {
            dismissWithAnimation(false);
        }

        public void dismissWithAnimation ( boolean fromCancel){
            mCloseFromCancel = fromCancel;
            mConfirmButton.startAnimation(mOverlayOutAnim);
            mDialogView.startAnimation(mModalOutAnim);
        }

        public static int spToPx ( float sp, Context context){
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        }

        public int getAlertType () {
            return mAlertType;
        }

        public String getTitleText () {
            return mTitleText;
        }

        public boolean isShowTitleText () {
            return mShowTitleText;
        }

        public String getContentText () {
            return mContentText;
        }

        public String getInputText () {
            return mEditText.getText().toString();
        }

        public void setInputText (String text){
            mEditText.setText(text);
        }

        public boolean isShowCancelButton () {
            return mShowCancel;
        }

        public boolean isShowContentText () {
            return mShowContent;
        }

        public String getCancelText () {
            return mCancelText;
        }

        public IonAlert setCancelText (String text){
            mCancelText = text;
            if (mCancelButton != null && mCancelText != null) {
                showCancelButton(true);
                mCancelButton.setText(mCancelText);
//                CancelText.setText(mCancelText);
            }
            return this;
        }

        public String getConfirmText () {
            return mConfirmText;
        }

        public IonAlert setConfirmText (String text){
            mConfirmText = text;
            if (mConfirmButton != null && mConfirmText != null) {
                mConfirmButton.setText(mConfirmText);
            }
            return this;
        }

    public IonAlert setConfirmTextSize(int size){
        mConfirmTextSize = size;
        if (mConfirmButton != null && mConfirmTextSize != 0) {
            mConfirmButton.setTextSize(mConfirmTextSize);
        }
        return this;
    }

    public IonAlert setCancelTextSize(int size){
        mCancelTextSize = size;
        if (mCancelButton != null && mCancelTextSize != 0) {
            mCancelButton.setTextSize(mCancelTextSize);
        }
        return this;
    }

        public IonAlert setContentTextSize(int value){
            this.contentTextSize = value;
            return this;
        }

        public int getContentTextSize () {
            return contentTextSize;
        }

        public IonAlert setCustomView (View view){
            mCustomView = view;
            if (mCustomView != null && mCustomViewContainer != null) {
                mCustomViewContainer.addView(view);
                mCustomViewContainer.setVisibility(View.VISIBLE);
                //mContentTextView.setVisibility(View.GONE);
            }
            return this;
        }

        @Override
        public void onClick (View v){
            if (v.getId() == R.id.cancel_button) {
                if (mCancelClickListener != null) {
                    mCancelClickListener.onClick(IonAlert.this);
                } else {
                    dismissWithAnimation();
                }
            } else if (v.getId() == R.id.custom_confirm_button) {
                if (mConfirmClickListener != null) {
                    mConfirmClickListener.onClick(IonAlert.this);
                } else {
                    dismissWithAnimation();
                }
            }
        }
}