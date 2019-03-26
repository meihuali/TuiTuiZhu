package com.tonghechuanmei.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tonghechuanmei.android.R;

/**
 * @author shandirong
 * @date 2018/3/21
 */

public class AlertUtils {

    private static AlertDialog alertDialog;

    public static AlertDialog normalAlert(final Activity context, String title, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(title);
        builder.setView(view);
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_background);
        }
        view.findViewById(R.id.sure).setOnClickListener(listener);
        view.findViewById(R.id.cancel).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.72);
        alertDialog.getWindow().setAttributes(p);
        return alertDialog;
    }

    public static AlertDialog getDialog(Context context, int resId) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.tipsDialog_style).create();     //AlertDialog.THEME_TRADITIONAL表示默认的背景为透明
        dialog.setView(new EditText(context));
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        dialog.show();
        dialog.setContentView(resId);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int screenWidth = ScreenUtil.Companion.getInstance(context).getScreenWidth();
        attributes.width = screenWidth;
        window.setAttributes(attributes);
        return dialog;
    }

    public static void editAlert(final Activity context, final String hint,
                                 final OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_task_number, null);
        final EditText editText = view.findViewById(R.id.title);
        editText.setHint(hint);
        builder.setView(view);
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_background);
        }
        view.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
                } else {
                    listener.onNumber(editText.getText().toString());
                }
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.72);
        alertDialog.getWindow().setAttributes(p);
    }

    public static void firstLoginAlert(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_first_login, null);
        builder.setView(view);
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_background);
        }
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.72);
        alertDialog.getWindow().setAttributes(p);
    }


    public static void exchangeAlert(final Activity context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exchange, null);
        builder.setView(view);
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_background);
        }
        view.findViewById(R.id.btn).setOnClickListener(listener);
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.72);
        alertDialog.getWindow().setAttributes(p);
    }

    public static void refuseAlert(final Activity context, final OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_refuse, null);
        final EditText editText = view.findViewById(R.id.et);
        final CheckBox button1 = view.findViewById(R.id.button_1);
        final CheckBox button2 = view.findViewById(R.id.button_2);
        builder.setView(view);
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.sh_wtgyy);
        }
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button1.isChecked() && !button2.isChecked() &&
                        editText.getText().toString().isEmpty()) {
                    Toast.makeText(context, "请选择或者输入原因", Toast.LENGTH_SHORT).show();
                } else {
                    if (button1.isChecked()) {
                        listener.onNumber(button1.getText().toString());
                    } else if (button2.isChecked()) {
                        listener.onNumber(button2.getText().toString());
                    } else {
                        listener.onNumber(editText.getText().toString());
                    }
                }
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    editText.setText("");
                }
                button2.setChecked(false);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    editText.setText("");
                }
                button1.setChecked(false);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    button1.setChecked(false);
                    button2.setChecked(false);
                }
            }
        });
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.77);
        alertDialog.getWindow().setAttributes(p);
    }

    public static void showUpload(final Activity context, final String url, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null);
        TextView t = view.findViewById(R.id.text);
        if (TextUtils.isEmpty(content)) {
            t.setText("新版本更新");
        } else {
            t.setText(content);
        }
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        view.findViewById(R.id.btn).setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateService.class);
            intent.putExtra("url", url);
            context.startService(intent);
//            context.startService(new Intent(context, UpdateAppService.class));
//            DownloadAppUtils.download(context, url, versionCode);
            view.findViewById(R.id.btn).setEnabled(false);
            ((TextView) view.findViewById(R.id.btn)).setText("正在下载...");
        });
        alertDialog.show();

        WindowManager m = context.getWindowManager();
        //为获取屏幕宽、高
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.747);
        alertDialog.getWindow().setAttributes(p);
    }

    public static void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public interface OnClickListener {
        void onNumber(String number);
    }
}
