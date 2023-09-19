package com.example.myapplication;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyView> {
     private  Context context;

    private final List<garbageclass> list;

    public Adapter(Context context,List<garbageclass> list) {
        this.list = list;
        this.context=context;
    }


    public class MyView extends RecyclerView.ViewHolder {
        AppCompatImageView image;
        TextView uname;
        TextView add;
        Button btnMap;

        public MyView(View view) {
            super(view);
            image = view.findViewById(R.id.img);
            uname = view.findViewById(R.id.name);
            add = view.findViewById(R.id.address);
            btnMap = view.findViewById(R.id.button1);
        }
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate item.xml using LayoutInflater
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview, parent, false);
        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        final garbageclass currentItem = list.get(position);
        //holder.add.setText(currentItem.getAddress());
        //holder.uname.setText(currentItem.getuName());
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.image);
//                .load(currentItem.getImageUrl())
//                .fitCenter()
//                .into(holder.image);

        holder.btnMap.setOnClickListener(view -> {
            Uri mapUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=");
            Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
            view.getContext().startActivity(intent);
        });
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }
}

