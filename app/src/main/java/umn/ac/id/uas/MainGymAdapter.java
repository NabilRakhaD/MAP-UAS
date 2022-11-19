package umn.ac.id.uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainGymAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Gym> listgymMain;

    public MainGymAdapter(Context context, ArrayList<Gym> listgymMain){
        this.context = context;
        this.listgymMain = listgymMain;
    }

    public int getCount(){
        return listgymMain.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.list_gym_main, container, false);

        ImageView image = view.findViewById(R.id.imageView7);
        TextView nama = view.findViewById(R.id.namagymMain);
        TextView jarak = view.findViewById(R.id.jarakMain);

        Gym gym = listgymMain.get(position);
        image.setImageResource(gym.getGympic());
        nama.setText(gym.getNama());
        jarak.setText(gym.getJarak());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToDetailGym = new Intent(view.getContext(), DetailGymActivity.class);
                MoveToDetailGym.putExtra("gym", listgymMain.get(position));
                view.getContext().startActivity(MoveToDetailGym);
            }
        });

        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }
}
