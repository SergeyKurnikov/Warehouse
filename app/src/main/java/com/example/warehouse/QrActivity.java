package com.example.warehouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity implements View.OnClickListener {

    /**Экземпляры класса Button для кнопки сканирования*/
    Button scanBtn;

    /**Экземпляры класса TextView для полей вывода расшифровки кода, формата кода,
     * текстовых данных из базы
     */
    TextView tvScanContent, tvScanFormat, tvAllData2;

    /**Экземпляры класса DBHelper для взаимодействия с базой данных*/
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        tvScanContent = findViewById(R.id.tvScanContent);
        tvScanFormat = findViewById(R.id.tvScanFormat);
        tvAllData2 = findViewById(R.id.tvDataAll2);

        scanBtn = findViewById(R.id.btnScan);
        scanBtn.setOnClickListener(this);
        //Инициализация экземплра класса DBHelper
        dbHelper = new DBHelper(this);
    }
    /**
     * Метод, который берёт данные из ресурсов меню и
     * преобразует их в пункты меню на экране
     * @param menu - объект класса Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qr, menu);
        return true;
    }
    /**
     * Получение идентификаторов выбранного пункта меню через разные обработчики,
     * переход в другие Activity
     * @param item - объект класса MenuItem, для работы с конкретным пунктом меню
     */
    public void onMainMenuClick(MenuItem item) {
        Intent intent = new Intent(QrActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onAdminMenuClick(MenuItem item) {
        Intent intent = new Intent(QrActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Наведите прямоугольник на QR-код");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null){
                Toast.makeText(getBaseContext(), "Отменено", Toast.LENGTH_LONG).show();
            } else{
                tvScanFormat.setText(result.getFormatName());
                tvScanContent.setText(result.getContents());

                String name = tvScanContent.getText().toString();
                // Создание экземпляра класса DSQLiteDatabase
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                String allLines = "";
                Cursor cursor = database.query(DBHelper.TABLE_COMPLETE, new String[] {"_id","name","type", "number", "provider", "date"}, DBHelper.KEY_NAME + " = ?", new String[] {name}, null, null, null);
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int providerIndex = cursor.getColumnIndex(DBHelper.KEY_PROVIDER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    // Перебирание и вывод строк таблицы
                    do {
                        allLines += cursor.getInt(idIndex) + ") "
                                + cursor.getString(nameIndex) + " "
                                + cursor.getString(typeIndex) + " "
                                + cursor.getString(numberIndex) + "шт. "
                                + cursor.getString(providerIndex) + " "
                                + cursor.getString(dateIndex) + "\n";
                    } while (cursor.moveToNext());
                    tvAllData2.setText(allLines);
                }
                cursor.close();
                dbHelper.hashCode();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}