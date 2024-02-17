package com.example.fragmentworking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MapActivity extends AppCompatActivity {
    private Matrix matrix = new Matrix();                  // Создаем матрицу для изображения
    private float mScale = 1f;                             // Масштаб изображения

    private ScaleGestureDetector mScaleGestureDetector;    // Слушатель жестов для изменения масштаба(зуминга)
    private GestureDetector gestureDetector;              // Слушатель жесто
    ImageView imageView;
    float currentTranslateX;                              // Изменение координат картинки
    float currentTranslateY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        imageView = findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.navigation); // Создаем картинку ищ ресурсов

        Display display = getWindowManager().getDefaultDisplay(); // Получаем параметры экрана девайса
        Point size = new Point();                                 // Класс для получения пикселей
        display.getSize(size);                                    // Получаем размеры девайса
        int screenWidth = size.x;                                 // Получаем высоту и ширини девайса
        int screenHeight = size.y;

        float scaleFactorX = (float) screenWidth / bitmap.getWidth();    // Создаем переменную-пропорцию реального изобрадения и экрана
        float scaleFactorY = (float) screenHeight / bitmap.getHeight();

        matrix.postScale(scaleFactorX, scaleFactorY);                   // Создаем матрицу с пропорцией

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), // Создаем картинку подогнанную для экрана девайса
                bitmap.getHeight(), matrix, true);

        imageView.setImageBitmap(scaledBitmap);      // Подгружаем изменнеую картинку


        gestureDetector = new GestureDetector(this, new GestureListener()); // Инициализация слушателей жестов
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.
                SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {        // Прослушивание зуминга - изменение размера картинки

                float scale = 1 - detector.getScaleFactor();            // Определение жеста пользователя, т.е. насколько сильно он развел пальцы
                float prevScale = mScale;                               // Запоминаем результат
                mScale += scale;                                        // Готовимся к масштабированию изображения

                if (mScale > 1f) // Если изображение становится меньше экрана, то игнорируем зуминг
                    mScale = 1f;


                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, // Начинаем анамицию зуминга
                        1f / mScale, 1f / prevScale, 1f / mScale,
                        detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                imageView.startAnimation(scaleAnimation);      // Назначаем анимацию изображению

                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {  // Передаем прикосновене в слушатель жестов
        return gestureDetector.onTouchEvent(event); //|| super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) { // Назначаем слушателям пикосновения
        super.dispatchTouchEvent(ev);

        mScaleGestureDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {  // Класс для обработки жестов
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        // Метод реагирующий на скроллинг
        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            float diffX = (e2.getX() - e1.getX()) / 2;   // Определяем разницу между перым и последним касанием экрана
            float diffY = (e2.getY() - e1.getY()) / 2;
            //   currentTranslateX = e1.getX();
            // currentTranslateY = e1.getY();

            Log.d("Координаты e2: ", " " + e2.getX() + " " + e2.getY());   // Трассировка значений
            Log.d("Координаты e1: ", " " + e1.getX() + " " + e1.getY());
            float newTranslateX = currentTranslateX + diffX;                       // Назначаем сдвиг для матрицы
            float newTranslateY = currentTranslateY + diffY;
            Log.d("Координаты y: ", " " + currentTranslateY + " " + diffY);
            Log.d("Координаты x: ", " " + currentTranslateX + " " + diffX);

            // if(Math.abs(diffX)> 150 || Math.abs(diffY)>150) { // Опять назначаем анимацию для картинки
            ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(MapActivity.this, "translateX", currentTranslateX, newTranslateX);
            ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(MapActivity.this, "translateY", currentTranslateY, newTranslateY);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(translateXAnimator, translateYAnimator);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new DecelerateInterpolator());

            animatorSet.start();
            //   }
            return true;
        }


    }

    // Методы для вычисления новых значений матрицы - сдвиг картинки
    public void setTranslateX(float translateX) {
        currentTranslateX = translateX;
        matrix.postTranslate(translateX, currentTranslateY);
        imageView.setImageMatrix(matrix);
    }

    public void setTranslateY(float translateY) {
        currentTranslateY = translateY;
        matrix.setTranslate(currentTranslateX, translateY);
        imageView.setImageMatrix(matrix);
    }
}