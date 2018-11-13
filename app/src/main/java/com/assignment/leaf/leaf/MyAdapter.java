package com.assignment.leaf.leaf;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    public MyAdapter(){}
    private List<Model> myList=new ArrayList<>();
    private Context context;

    public MyAdapter(List<Model> myList,Context context){

        this.myList=myList;
        this.context=context;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {

        Model model=myList.get(i);
        MyViewHolder.namee.setText(model.getFirst()+" "+model.getLast());
        MyViewHolder.numberr.setText(model.getPhone());
        MyViewHolder.emaill.setText(model.getEmail());
        String date=model.getDob().substring(0,10);

        try {
            MyViewHolder.doob.setText(changeFormat(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ImageView im=myViewHolder.profile;

        Picasso.get().
                load(model.getLarge())
                .placeholder(R.drawable.avtr).centerCrop()
                .fit()
                .into(im);


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        static ImageView profile;
        static TextView namee;
        static TextView numberr;
        static TextView emaill;
        static TextView doob;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namee = (TextView) itemView.findViewById(R.id.name);
            numberr = (TextView) itemView.findViewById(R.id.number);
            emaill = (TextView) itemView.findViewById(R.id.mail);
            profile = (ImageView) itemView.findViewById(R.id.profile_image);
            doob=(TextView) itemView.findViewById(R.id.dobb);

        }
    }


    public String changeFormat(String d) throws ParseException {
        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;

        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            result = sdf1.format(sdf.parse(d));
        }
        catch(Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            sdf=null;
            sdf1=null;
        }
        return result;
    }


}
