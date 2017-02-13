package com.example.indrianinatalia.database;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by indrianinatalia on 2/9/17.
 */

public class AdapterQ extends RecyclerView.Adapter<AdapterQ.ViewHolder> {

    Context context;
    List<String> id, name, gen, numb;
    View view;
    ViewHolder holder;
    int[] image = {R.drawable.boy, R.drawable.girl};
    String []menu = {"Call", "Edit", "Delete"};

    public AdapterQ(Context context, List<String> id, List<String> name, List<String> gen, List<String> numb) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.gen = gen;
        this.numb = numb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_view, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtName.setText(name.get(position));
        holder.txtGen.setText(gen.get(position));
        holder.txtNumb.setText(numb.get(position));
        if(holder.txtGen.getText().toString().equalsIgnoreCase("boy")){
            holder.imageView.setImageResource(image[0]);
        }else{
            holder.imageView.setImageResource(image[1]);
        }

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ListView lisview = new ListView(context);
                lisview.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, menu));
                final AlertDialog.Builder pesan = new AlertDialog.Builder(context);
                pesan
                        .setTitle("Opton")
                        .setView(lisview)
                        .setNegativeButton("Close", null).create().show();
                lisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int positionQ, long idQ) {
                        if(positionQ==0){
                            Intent intent = new Intent (Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:"+numb.get(position)));
                            context.startActivity(intent);
                        }else if(positionQ==1){
                            Intent intent = new Intent(context, AddActivity.class);
                            intent.putExtra("id", id.get(position));
                            context.startActivity(intent);
                        }
                    }
                });

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtNumb, txtGen;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgCon);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtGen = (TextView) itemView.findViewById(R.id.txtGen);
            txtNumb = (TextView) itemView.findViewById(R.id.txtNumb);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearUtama);
        }
    }
}
