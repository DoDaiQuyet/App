package com.example.appwarmingfire;

import static com.example.appwarmingfire.MyApplication.CHANNEL_ID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements  IDataListener {

    TextView tvName1;
    TextView tvName2 ;
    TextView tvName3;
    TextView tvName4 ;
    TextView tvHumidity;
    TextView tvTemperature;
    TextView tvGas ;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {
                    Toast.makeText(this, "Vui lòng cấp đủ quyền", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mapping();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Hiển thị tên lên màn hình
        initName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS);
        }
        // Lấy dữ liệu từ firebase
        ControllerFirebaseData controllerFirebaseData = new ControllerFirebaseData(this);
        controllerFirebaseData.fetchHumidity();
        controllerFirebaseData.fetchTemperature();
        controllerFirebaseData.fetchGas();
    }

    void mapping(){
        tvName1 = findViewById(R.id.tv1);
        tvName2 = findViewById(R.id.tv2);
        tvName3 = findViewById(R.id.tv3);
        tvName4 = findViewById(R.id.tv4);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvTemperature = findViewById(R.id.tv_temper);
        tvGas = findViewById(R.id.tv_gas);
    }

    void initName(){
        tvName1.setText("Nguyễn Duy Anh");
        tvName2.setText("Đỗ Đại Quyết");
        tvName3.setText("Nguyễn Mạnh Quân");
        tvName4.setText("Bùi Thanh Tú");
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void getHumidity(float data) {
        tvHumidity.setText(data+"%");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTemperature(float data) {
        tvTemperature.setText(data+" \u00B0C");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getGas(int data) {
        tvGas.setText(data+"");
        if (!MyApplication.getInstance().getMFirstBoot()){
            if (data >= 2000){
                showNotification();
            }
        }else {
            MyApplication.getInstance().setMFirstBoot(false);
        }
    }

    @SuppressLint("MissingPermission")
    public void showNotification(){
//        Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show();
        Notification newMessageNotification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            newMessageNotification = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Cảnh báo")
                    .setContentText("Có nguy cơ cháy nổ")
                    .build();
            Toast.makeText(this, "Thông báo 1", Toast.LENGTH_SHORT).show();

        }

        // Issue the notification.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(100), newMessageNotification);
    }
}