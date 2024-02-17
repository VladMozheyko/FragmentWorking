package com.example.fragmentworking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentworking.R;
import com.example.fragmentworking.models.Model;

import java.util.ArrayList;

// Наследуемся внутреннего класса RecyclerView - Adapte, в скобочках привязываем к нему ViewHolder
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

    public interface OnItemClickListener{   // Интерфейс для обработки нажатий на элеент спсика
        void onItemClick(int position);
    }

    private OnItemClickListener listener; // Экземпляр интерфейса для обработки нажатий на элемент списка


    private LayoutInflater inflater;          // Создаем поля класса для доступа  ним в пределах класса

    private ArrayList<Model> models;

    // Создаем объект, в который передаем модели, чтобы их отображть пользователю и контекст, чтобы
    // понимать где выводить
    public ChooseAdapter(Context context, ArrayList<Model> models) {
        this.models = models;
        inflater = LayoutInflater.from(context);
    }


    /**
     * Слушатель нажатий на элемент списка
     * @param listener экземпляр интерефейса-слушателя нажатий
     */
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // При создании объекта этот метод создаст ViewHolder
    @NonNull
    @Override
    public ChooseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choose_item, parent, false);  // Привязываемся к разметке - получаем элементы, в которые выводим модель
        return new ViewHolder(view); // Передаем разметку в класс, который выведет в нее данные модель
     }

    @Override
    public void onBindViewHolder(@NonNull ChooseAdapter.ViewHolder holder, int position) { // Метод отвечает за связку модели и виджетов
        Model model = models.get(position);               // Получаем индекс модели для конкретного элемента списка
        holder.textView.setText(model.getTitle());        // Выводим текст в название элемента списка
        holder.imageView.setImageResource(model.getImg());// Выводим картинку в название элемента списка
        holder.itemView.setOnClickListener(new View.OnClickListener() { // Добавляем слушателя для всего элемента списка
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();                  // Метод возвращает размер списка
    }

    public class ViewHolder extends RecyclerView.ViewHolder {  // Класс для управления виджетами

        ImageView imageView;                                   // Виджеты
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.choose_img); //Привязка виджетов к разметке
            textView = itemView.findViewById(R.id.choose_txt);
        }
    }
}
