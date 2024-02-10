package com.example.fragmentworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentworking.fragments.ChooseFragment;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText edt;
    private TextView txt;

    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();             // Привязка разметки к коду

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(edt.getText().toString());  // Получаем возраст из editText для этого преобразуем(парсим) текст в число
                try {
                    boolean access = pass(age);
                    txt.setText(" " + access);
                    edt.setText("");
                    if(access){  // Если получили доступ, запускаем фрагмент
                        if(fragment != null){     /// Если фрагмент уже отобразился, то удаляем его
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.remove(fragment);
                            transaction.commit();
                        }
                        fragmentManager = getSupportFragmentManager();
                        fragment = new ChooseFragment();
                        fragmentManager.beginTransaction()
                                .add(R.id.fragment_container, fragment)
                                .commit();
                    }
                    else {
                        if(fragment!= null) {
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.remove(fragment);
                            transaction.commit();
                        }
                    }

                }
                catch (AgeException ex){
                    Toast.makeText(MainActivity.this, "Вы ввели некорректный возраст", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void init(){
        btn = findViewById(R.id.btn_age);
        txt = findViewById(R.id.txt_age);
        edt = findViewById(R.id.edt_age);
    }

    private boolean pass(int age) throws AgeException {
        if(age >= 18){
            return true;
        }
        else if(age > 0 && age < 18) {
            return false;
        }else
            throw new AgeException("Введен отрицательный возраст");
    }
}