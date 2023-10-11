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
    private List<String> imageUrls;

  //  private final List<garbageClass> list;



//    public Adapter(Context context,List<garbageClass> list) {
//        this.list = list;
//        this.context=context;
//    }
public Adapter(Context context,List<String> imageUrls) {
    this.imageUrls = imageUrls;
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

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context).load(imageUrl).into(holder.image);
        //final garbageClass currentItem = list.get(position);

       // holder.add.setText(currentItem.getAddress());
       // holder.uname.setText(currentItem.getuName());
        //Glide.with(context).load(currentItem).into(holder.image);


        holder.btnMap.setOnClickListener(view -> {
            Uri mapUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=");
            Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
            view.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        //return list.size();
        return imageUrls.size();
    }

}

