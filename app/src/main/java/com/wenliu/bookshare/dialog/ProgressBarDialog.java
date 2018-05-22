package com.wenliu.bookshare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.wenliu.bookshare.R;

public class ProgressBarDialog extends Dialog{

    public ProgressBarDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_progress_bar);
    }
}
