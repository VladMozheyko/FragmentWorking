package com.example.fragmentworking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        Правмло нельзя создавать объекты абстрактных классов и интерфейсов обычным способом, т. к. они
        содержат абстраткные(нереализованные методы), но можно создать объект асбтрактного класса или
        интерфейса при помощи анонимного класса - по факту мы просто берем и рееализуем абстрактные методы
        при создании объекта
         */


        Runnable runnable = new Runnable() { // Создаем объект итерфейса Runnable при помощи анонимного класса
            @Override
            public void run() {               // Абстрактный метод интерфейса Runnable
                try {                         // Начинаем блок проверки кода, который может некорреткно отработать
                    Thread.sleep(3000); // Сам код, который может вызвать
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class); // Указываем активность которую хотим вызвать
                    startActivity(intent);    // Вызываем другую активность
                    finish();                 // Закрываем текущую активность
                } catch (InterruptedException e) {
                    // Код, который будет вызван в случае ошибки в блоке try
                }
            }
        };

        Thread thread = new Thread(runnable);  // Создаем задачу
        thread.start();                        // Запускаем задачу

    }
}