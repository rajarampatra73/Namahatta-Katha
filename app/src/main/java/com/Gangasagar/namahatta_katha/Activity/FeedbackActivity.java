package com.Gangasagar.namahatta_katha.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Gangasagar.namahatta_katha.LottiLoadingDialog;
import com.Gangasagar.namahatta_katha.MainActivity;
import com.Gangasagar.namahatta_katha.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private EditText email, name, message, number;
    private Button send;
    private FirebaseFirestore db;
    private LottiLoadingDialog dialog;
 //   private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.feedback));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dialog = new LottiLoadingDialog(FeedbackActivity.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);

        email = findViewById(R.id.support_input_email);
        name = findViewById(R.id.support_input_name);
        number = findViewById(R.id.support_input_number);
        message = findViewById(R.id.support_input_message);
        send = findViewById(R.id.support_button);

        //hide btn
        email.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);
        number.addTextChangedListener(textWatcher);
        message.addTextChangedListener(textWatcher);

        db = FirebaseFirestore.getInstance();

        send.setOnClickListener(v -> {
            dialog.show();
            insetData();
        });

    }

    private void insetData() {
        try {
            //castom Toast sms
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.toast_thank, (ViewGroup) findViewById(R.id.toast));
            final Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);

            Map<String, String> items = new HashMap<>();
            items.put("name", name.getText().toString().trim());
            items.put("email", email.getText().toString().trim());
            items.put("message", message.getText().toString().trim());
            items.put("number", number.getText().toString().trim());

            db.collection("feedback").add(items).addOnCompleteListener(task -> {
                toast.show();
                // Toast.makeText(getApplicationContext(), "Send Your Feedback Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                notification();
                dialog.dismiss();

            });
        } catch (android.content.ActivityNotFoundException e) {
            Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
            Toast.makeText(this, "Internet Problem", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkInputs() {
        if (!TextUtils.isEmpty(name.getText())){
            if (!TextUtils.isEmpty(email.getText() )){
                if (!TextUtils.isEmpty(number.getText()) && number.length() >= 10){
                    if (!TextUtils.isEmpty(message.getText()) && message.length() >= 1){
                        send.setEnabled(true);
                        send.setTextColor(Color.rgb(255,255,255));

                    }else {
                        send.setEnabled(false);
                        send.setTextColor(Color.argb(50,255,255,255));
                    }
                }else {
                    send.setEnabled(false);
                    send.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                send.setEnabled(false);
                send.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            send.setEnabled(false);
            send.setTextColor(Color.argb(50,255,255,255));
        }
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            send.setEnabled(false);
            send.setTextColor(Color.argb(50, 255, 255, 255));
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           checkInputs();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @SuppressLint("MissingPermission")
    private void notification() {
        String string = name.getText().toString().trim();
        String sms = getString(R.string.feed_noti);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText(getString(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText(string + " " + sms);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}