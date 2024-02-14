package com.example.fragmentworking;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class Repository {
    // public static - модификаторы глобальой переменной. Глобальная переменная - переменная доступная во всем проекте
    // static - модификатор делает переменную или метод часть класса, а не объекта
    public static FragmentManager fragmentManager;
    public static Fragment fragment;

    public static ArrayList<Integer> nubmers;

    // Если есть необходимость провести инициализацию статических данных в классе, это можно сделать при поощи блока static:
    static {  // Блок static. Будет вызван при загрузке класса
        nubmers = new ArrayList<>(); // Выделение памяти для массива
        nubmers.add(1);              // Его заполнение
        nubmers.add(2);
    }
}
