package com.example.warehouse;

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
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**Экземпляры класса EditText для полей имени и емайла*/
    EditText edtId, edtName, edtType, edtProvider;

    /**Экземпляры класса Button для кнопок чтения добавления и удаления*/
    Button btnAll, btnByName, btnByType, btnByProvider;

    /**Экземпляры класса TextView для поля вывода текстовых данных*/
    TextView tvAllData;

    /**Экземпляры класса DBHelper для взаимодействия с базой данных*/
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инциализация текстовых полей
        edtName = findViewById(R.id.edtName);
        edtType = findViewById(R.id.edtType);
        edtProvider = findViewById(R.id.edtProvider);
        tvAllData = findViewById(R.id.tvDataAll);

        //Инциализация кнопок
        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);
        btnByName = findViewById(R.id.btnByName);
        btnByName.setOnClickListener(this);
        btnByType = findViewById(R.id.btnByType);
        btnByType.setOnClickListener(this);
        btnByProvider = findViewById(R.id.btnByProvider);
        btnByProvider.setOnClickListener(this);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * Получение идентификаторов выбранного пункта меню через разные обработчики,
     * переход в другие Activity
     * @param item - объект класса MenuItem, для работы с конкретным пунктом меню
     */
    public void onAdminMenuClick(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }
    public void onQrMenuClick(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, QrActivity.class);
        startActivity(intent);
    }

    /**
     * Общий обработчик, который отлавливает нажатия на различные кнопки
     * @param v - объект класса View для хранения состояния приложения
     */
    @Override
    public void onClick(View v) {
        String name = edtName.getText().toString();
        String type = edtType.getText().toString();
        String provider = edtProvider.getText().toString();
        // Создание экземпляра класса DSQLiteDatabase
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String allLines = "";
        // Наполнение обработчика кнопок
        switch (v.getId()) {
            // Логика кнопки "ВСЕ"
            case R.id.btnAll:
                Cursor cursor = database.query(DBHelper.TABLE_COMPLETE, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int providerIndex = cursor.getColumnIndex(DBHelper.KEY_PROVIDER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    // Перебирание и вывод строк таблицы
                    do {
                        Log.d("myLog", "id = " + cursor.getInt(idIndex) +
                                ", наименование = " + cursor.getString(nameIndex) +
                                ", тип = " + cursor.getString(typeIndex) +
                                ", количество = " + cursor.getString(numberIndex) +
                                ", поставщик = " + cursor.getString(providerIndex) +
                                ", дата = " + cursor.getString(dateIndex));
                        allLines += cursor.getInt(idIndex) + ") "
                                + cursor.getString(nameIndex) + " "
                                + cursor.getString(typeIndex) + " "
                                + cursor.getString(numberIndex) + "шт. "
                                + cursor.getString(providerIndex) + " "
                                + cursor.getString(dateIndex) + "\n";
                    } while (cursor.moveToNext());
                    tvAllData.setText(allLines);
                }
                cursor.close();
                break;
            // Логика кнопки "ПО НАИМЕНОВАНИЮ"
            case R.id.btnByName:
                cursor = database.query(DBHelper.TABLE_COMPLETE, new String[] {"_id","name","type", "number", "provider", "date"}, DBHelper.KEY_NAME + " = ?", new String[] {name}, null, null, null);
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int providerIndex = cursor.getColumnIndex(DBHelper.KEY_PROVIDER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    // Перебирание и вывод строк таблицы
                    do {
                        Log.d("myLog", "id = " + cursor.getInt(idIndex) +
                                ", наименование = " + cursor.getString(nameIndex) +
                                ", тип = " + cursor.getString(typeIndex) +
                                ", количество = " + cursor.getString(numberIndex) +
                                ", поставщик = " + cursor.getString(providerIndex) +
                                ", дата = " + cursor.getString(dateIndex));
                        allLines += cursor.getInt(idIndex) + ") "
                                + cursor.getString(nameIndex) + " "
                                + cursor.getString(typeIndex) + " "
                                + cursor.getString(numberIndex) + "шт. "
                                + cursor.getString(providerIndex) + " "
                                + cursor.getString(dateIndex) + "\n";
                    } while (cursor.moveToNext());
                    tvAllData.setText(allLines);
                }
                cursor.close();
                break;
            // Логика кнопки "ПО ТИПУ"
            case R.id.btnByType:
                cursor = database.query(DBHelper.TABLE_COMPLETE, new String[] {"_id","name","type", "number", "provider", "date"}, DBHelper.KEY_TYPE + " = ?", new String[] {type}, null, null, null);
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int providerIndex = cursor.getColumnIndex(DBHelper.KEY_PROVIDER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    // Перебирание и вывод строк таблицы
                    do {
                        Log.d("myLog", "id = " + cursor.getInt(idIndex) +
                                ", наименование = " + cursor.getString(nameIndex) +
                                ", тип = " + cursor.getString(typeIndex) +
                                ", количество = " + cursor.getString(numberIndex) +
                                ", поставщик = " + cursor.getString(providerIndex) +
                                ", дата = " + cursor.getString(dateIndex));
                        allLines += cursor.getInt(idIndex) + ") "
                                + cursor.getString(nameIndex) + " "
                                + cursor.getString(typeIndex) + " "
                                + cursor.getString(numberIndex) + "шт. "
                                + cursor.getString(providerIndex) + " "
                                + cursor.getString(dateIndex) + "\n";
                    } while (cursor.moveToNext());
                    tvAllData.setText(allLines);
                }
                cursor.close();
                break;
            // Логика кнопки "ПО ПОСТАВЩИКУ"
            case R.id.btnByProvider:
                cursor = database.query(DBHelper.TABLE_COMPLETE, new String[] {"_id","name","type", "number", "provider", "date"}, DBHelper.KEY_PROVIDER + " = ?", new String[] {provider}, null, null, null);
                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int providerIndex = cursor.getColumnIndex(DBHelper.KEY_PROVIDER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    // Перебирание и вывод строк таблицы
                    do {
                        Log.d("myLog", "id = " + cursor.getInt(idIndex) +
                                ", наименование = " + cursor.getString(nameIndex) +
                                ", тип = " + cursor.getString(typeIndex) +
                                ", количество = " + cursor.getString(numberIndex) +
                                ", поставщик = " + cursor.getString(providerIndex) +
                                ", дата = " + cursor.getString(dateIndex));
                        allLines += cursor.getInt(idIndex) + ") "
                                + cursor.getString(nameIndex) + " "
                                + cursor.getString(typeIndex) + " "
                                + cursor.getString(numberIndex) + "шт. "
                                + cursor.getString(providerIndex) + " "
                                + cursor.getString(dateIndex) + "\n";
                    } while (cursor.moveToNext());
                    tvAllData.setText(allLines);
                }
                cursor.close();
                break;
        }
        dbHelper.hashCode();
    }
}