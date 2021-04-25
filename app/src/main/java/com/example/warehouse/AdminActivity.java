package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    /**
     * Метод, который берёт данные из ресурсов меню и
     * преобразует их в пункты меню на экране
     * @param menu - объект класса Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }
    /**
     * Получение идентификаторов выбранного пункта меню через разные обработчики,
     * переход в другие Activity
     * @param item - объект класса MenuItem, для работы с конкретным пунктом меню
     */
    public void onMainMenuClick(MenuItem item) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onQrMenuClick(MenuItem item) {
        Intent intent = new Intent(AdminActivity.this, QrActivity.class);
        startActivity(intent);
    }
}