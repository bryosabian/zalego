package com.sabiantools.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabiantools.R;

import tr.xip.errorview.ErrorView;

/**
 * Created by Brian Sabana on 19/01/2017.
 */
public class SabianErrorView {

    private SabianError error;

    private Context context;

    private boolean isOpen=false;

    private ViewGroup parentContainer=null;

    private FragmentManager errorManager;

    private FragmentTransaction errorTransaction;

    private AppCompatActivity activity;

    public SabianErrorView(AppCompatActivity activity){

        this.activity=activity;

        this.errorManager =this.activity.getSupportFragmentManager();

        this.error=new SabianError();

        error.setErrorParent(this);
    }
    public boolean showError(){

        if(this.parentContainer==null)
            return false;

        this.errorTransaction = errorManager.beginTransaction();

        errorTransaction.replace(parentContainer.getId(), error);

        errorTransaction.commit();

        for (int i = 0; i < parentContainer.getChildCount(); i++)
            parentContainer.getChildAt(i).setVisibility(View.GONE);

        setIsOpen(true);

        return true;
    }
    public boolean hideError() {

        if(parentContainer==null)
            return false;

        errorTransaction= errorManager.beginTransaction();

        errorTransaction.remove(this.error).commit();

        for (int i = 0; i < parentContainer.getChildCount(); i++)
            parentContainer.getChildAt(i).setVisibility(View.VISIBLE);

        setIsOpen(false);

        return true;
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setParentContainer(ViewGroup parentContainer) {
        this.parentContainer = parentContainer;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ViewGroup getParentContainer() {
        return parentContainer;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public String getErrorTitle() {
        return error.getErrorTitle();
    }

    public void setErrorTitle(String errorTitle) {
        this.error.setErrorTitle(errorTitle);
    }

    public String getErrorSubTitle() {
        return error.getErrorSubTitle();
    }

    public void setErrorSubTitle(String errorSubTitle) {
        this.error.setErrorSubTitle(errorSubTitle);

    }

    public String getErroButtonText() {
        return this.error.getErrorButtonText();
    }

    public void setErroButtonText(String erroButtonText) {
        this.error.setErrorButtonText(erroButtonText);
    }

    public boolean isShowRetryButton() {
        return this.error.isShowRetryButton();
    }

    public void setShowRetryButton(boolean showRetryButton) {
        this.error.setShowRetryButton(showRetryButton);
    }

    public boolean isShowErrorSubTitle() {
        return this.error.isShowErrorSubTitle();
    }

    public void setShowErrorSubTitle(boolean showErrorSubTitle) {
        this.error.setShowErrorSubTitle(showErrorSubTitle);
    }

    public OnErrorButtonListener getOnErrorButtonListener() {
        return this.error.getOnErrorButtonListener();
    }

    public void setOnErrorButtonListener(OnErrorButtonListener onErrorButtonListener) {
        this.error.setOnErrorButtonListener(onErrorButtonListener);
    }

    public int getTitleColor() {
        return error.getTitleColor();
    }

    public void setTitleColor(int titleColor) {
        this.error.setTitleColor(titleColor);
    }
    public void setImage(int image){
        this.error.setImage(image);
    }

    public int getRetryButtonColor() {
        return this.error.getRetryButtonColor();
    }

    public void setRetryButtonColor(int retryButtonColor) {
        this.error.setRetryButtonColor(retryButtonColor);
    }

    public void setSubTtitleColor(int subTtitleColor) {
        this.error.setSubTitleColor(subTtitleColor);
    }

    public SabianErrorView getThisView(){
        return this;
    }

    public static class SabianError extends SabianFragment{

        private ErrorView errorView;

        private String errorTitle="Error";

        private String errorSubTitle="Error has occurred";

        private String errorButtonText="Retry";

        private boolean showRetryButton,showErrorSubTitle=true;

        private OnErrorButtonListener onErrorButtonListener;

        private int titleColor, subTitleColor,retryButtonColor=-1;

        private int image=-1;

        private SabianErrorView errorParent;


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            root = inflater.inflate(R.layout.sabian_error_view, container, false);

            init_elements();

            return root;
        }
        private SabianError getThis(){
            return this;
        }
        private SabianErrorView getErrorParent(){
            return errorParent;
        }

        private void init_elements() {

            errorView=(ErrorView)root.findViewById(R.id.err_SabianErrorView);

            ErrorView.Config.Builder config= ErrorView.Config.create();

            config.title(getErrorTitle()).subtitle(getErrorSubTitle()).retryText(getErrorButtonText());

            errorView.setOnRetryListener(new ErrorView.RetryListener() {
                @Override
                public void onRetry() {
                    if (onErrorButtonListener != null)
                        onErrorButtonListener.OnClick(getErrorParent());
                }
            });

            config.retryVisible(isShowRetryButton());

            if(getImage()!=-1)
                config.image(getImage());

            if(getTitleColor()!=-1)
                config.titleColor(getTitleColor());

            if(getRetryButtonColor()!=-1)
                config.retryTextColor(getRetryButtonColor());

            if(getSubTitleColor()!=-1)
                config.subtitleColor(getSubTitleColor());

            errorView.setOnRetryListener(new ErrorView.RetryListener() {
                @Override
                public void onRetry() {
                    if(onErrorButtonListener!=null)
                        onErrorButtonListener.OnClick(getErrorParent());
                }
            });

            errorView.setConfig(config.build());

        }
        public ErrorView getErrorView(){

            return errorView;
        }

        public String getErrorTitle() {
            return errorTitle;
        }

        public void setErrorTitle(String errorTitle) {
            this.errorTitle = errorTitle;
        }

        public String getErrorSubTitle() {
            return errorSubTitle;
        }

        public void setErrorSubTitle(String errorSubTitle) {
            this.errorSubTitle = errorSubTitle;
        }

        public String getErrorButtonText() {
            return errorButtonText;
        }

        public void setErrorButtonText(String errorButtonText) {
            this.errorButtonText = errorButtonText;
        }

        public boolean isShowRetryButton() {
            return showRetryButton;
        }

        public void setShowRetryButton(boolean showRetryButton) {
            this.showRetryButton = showRetryButton;
        }

        public boolean isShowErrorSubTitle() {
            return showErrorSubTitle;
        }

        public void setShowErrorSubTitle(boolean showErrorSubTitle) {
            this.showErrorSubTitle = showErrorSubTitle;
        }

        public OnErrorButtonListener getOnErrorButtonListener() {
            return onErrorButtonListener;
        }

        public void setOnErrorButtonListener(OnErrorButtonListener onErrorButtonListener) {
            this.onErrorButtonListener = onErrorButtonListener;
        }

        public int getTitleColor() {
            return titleColor;
        }

        public void setTitleColor(int titleColor) {
            this.titleColor = titleColor;
        }

        public int getRetryButtonColor() {
            return retryButtonColor;
        }

        public void setRetryButtonColor(int retryButtonColor) {
            this.retryButtonColor = retryButtonColor;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public void setErrorParent(SabianErrorView errorParent) {
            this.errorParent = errorParent;
        }

        public int getSubTitleColor() {
            return subTitleColor;
        }

        public void setSubTitleColor(int subTitleColor) {
            this.subTitleColor = subTitleColor;
        }
    }

    public interface OnErrorButtonListener{

        void OnClick(SabianErrorView error);
    }
}
