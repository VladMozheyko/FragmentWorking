package com.example.fragmentworking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentworking.R;
import com.example.fragmentworking.adapters.MenuAdapter;
import com.example.fragmentworking.models.MenuItem;

import java.util.ArrayList;

public class BeverageFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beverage_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.beverages_list);

        ArrayList<MenuItem> beverages = new ArrayList<>();
        beverages.add(new MenuItem("Пина колада", R.drawable.beverage1, "Легкий и " +
                "невероятно приятный напиток. С чудным послевкусием", 400));
        beverages.add(new MenuItem("Золотая фея", R.drawable.beverage2, "Сладкий и" +
                "нежный напиток", 500));
        beverages.add(new MenuItem("Голубая лагуна", R.drawable.beverage3, "Невероятно " +
                "комбинированный вкус джина и вишенки", 700));
        beverages.add(new MenuItem("Маргарита", R.drawable.beverage4, "Нежный и" +
                " вкусный котейль", 300));

        ArrayList<MenuItem> orders = new ArrayList<>();
        MenuAdapter adapter = new MenuAdapter(getActivity(), beverages);
        adapter.setListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                orders.add(beverages.get(position));        // Добавляем в заказы напиток
                Toast.makeText(getActivity(), "Сумма заказа: " + String.valueOf(countSum(orders)),
                        Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    private int countSum(ArrayList<MenuItem> orders) {
        int res = 0;
        for (int i = 0; i < orders.size(); i++) {
            res += orders.get(i).getPrice();
        }
        return res;
    }
}
