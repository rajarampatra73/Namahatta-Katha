package com.Gangasagar.namahatta_katha;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class LottiLoadingDialog extends Dialog {
    public LottiLoadingDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View lview = LayoutInflater.from(context).inflate(R.layout.paper_looading_dialog,null);
        setContentView(lview);
    }
}
