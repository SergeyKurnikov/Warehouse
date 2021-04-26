package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.warehouse.NumberUtil.isNumberInputCorrect;
import static com.example.warehouse.TextUtil.isTextCorrect;
import static com.example.warehouse.DateUtil.isDateCorrect;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    /**Экземпляры класса EditText для полей имени и емайла*/
    EditText edtId, edtName, edtType, edtNumber, edtProvider, edtDate;

    /**Экземпляры класса Button для кнопок чтения добавления и удаления*/
    Button btnRead, btnAdd, btnClear, btnUpdate, btnDelete, btnClearOutput;

    /**Экземпляры класса TextView для поля вывода текстовых данных*/
    TextView tvAllData;

    /**Экземпляры класса DBHelper для взаимодействия с базой данных*/
    DBHelper dbHelper;

    /**
     *  Начальная установка объектов при инициализации главной Activity
     *  @param savedInstanceState - объект класса Bundle для хранения состояния приложения
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Инциализация текстовых полей
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtType = findViewById(R.id.edtType);
        edtNumber = findViewById(R.id.edtNumber);
        edtProvider = findViewById(R.id.edtProvider);
        edtDate = findViewById(R.id.edtDate);
        tvAllData = findViewById(R.id.tvDataAll);

        //Инциализация кнопок
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnClearOutput = findViewById(R.id.btnClearOutput);
        btnClearOutput.setOnClickListener(this);

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


    /**
     * Общий обработчик, который отлавливает нажатия на различные кнопки
     * @param v - объект класса View для хранения состояния приложения
     */
    @Override
    public void onClick(View v) {
        String _id = edtId.getText().toString();
        String name = edtName.getText().toString();
        String type = edtType.getText().toString();
        String number = edtNumber.getText().toString();
        String provider = edtProvider.getText().toString();
        String date = edtDate.getText().toString();

        // Создание экземпляра класса DSQLiteDatabase
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String allLines = "";
        // Наполнение обработчика кнопок
        switch (v.getId()) {
            // Логика кнопки добавить
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_ID, _id);
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_TYPE, type);
                contentValues.put(DBHelper.KEY_NUMBER, number);
                contentValues.put(DBHelper.KEY_PROVIDER, provider);
                contentValues.put(DBHelper.KEY_DATE, date);

                Log.d("LOG_TAG", "Метод проверки вернул" + checkUserExists(_id, name));
                if(!isNumberInputCorrect(_id)){
                    tvAllData.setText("Идентефикатор не может быть пустым или равняться нулю!");
                    break;
                }
                if(!isTextCorrect(name)){
                    tvAllData.setText("Некоректное имя!");
                    break;
                }
                if(!isTextCorrect(type)){
                    tvAllData.setText("Некоректный тип!");
                    break;
                }
                if(!isNumberInputCorrect(number)){
                    tvAllData.setText("Количество не может быть пустым или равняться нулю!");
                    break;
                }
                if(!isTextCorrect(provider)){
                    tvAllData.setText("Некоректный поставщик!");
                    break;
                }
                if(!isDateCorrect(date)){
                    tvAllData.setText("Некорректная дата!\n Допустимые форматы:\n " +
                            "XX-XX-XXXX\n " +
                            "XX.XX.XXXX\n " +
                            "XX/XX/XXXX");
                    break;
                }

                if (checkUserExists(_id, name)) {
                   Toast toast = Toast.makeText(getBaseContext(), "Дублирование позиции!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    tvAllData.setText("Такая позиция уже есть!");
                    break;
                }
                database.insert(DBHelper.TABLE_COMPLETE, null, contentValues);
                // Логика кнопки прочитать
            case R.id.btnRead:
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
            // Логика кнопки очистить
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_COMPLETE, null, null);
                break;
            // Логика кнопки обновить
            case R.id.btnUpdate:
                if (_id.equalsIgnoreCase("")) {
                    break;
                }
                contentValues.put(DBHelper.KEY_ID, _id);
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_TYPE, type);
                contentValues.put(DBHelper.KEY_NUMBER, number);
                contentValues.put(DBHelper.KEY_PROVIDER, provider);
                contentValues.put(DBHelper.KEY_DATE, date);
                database.update(DBHelper.TABLE_COMPLETE, contentValues,
                        DBHelper.KEY_ID + " = " + _id, null);
                break;

            // Логика кнопки удалить
            case R.id.btnDelete:
                if (_id.equalsIgnoreCase("")) {
                    break;
                }
                int delCount = database.delete(DBHelper.TABLE_COMPLETE,
                        DBHelper.KEY_ID + "= ?", new String[]{_id});
                Log.d("LOG_TAG", "удалено строк = " + delCount);
                break;
            // Логика кнопки очистить вывод
            case R.id.btnClearOutput:
                tvAllData.setText("");
                break;
        }
        dbHelper.hashCode();
    }

    /**
     * Метод валидации новой комплектующей при добавлении по двум признакам
     * @param _id - id комплектубщей;
     * @param name - наименование коомплектующей;
     */
    public boolean checkUserExists(String _id, String name) {
        boolean flag;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor;
        String SQLQuery = String.format("SELECT * FROM complete WHERE _id='%s' OR name='%s'", _id, name);
        cursor = database.rawQuery(SQLQuery, null);
        flag = cursor.getCount() > 0;
        cursor.close();
        return flag;
    }
}