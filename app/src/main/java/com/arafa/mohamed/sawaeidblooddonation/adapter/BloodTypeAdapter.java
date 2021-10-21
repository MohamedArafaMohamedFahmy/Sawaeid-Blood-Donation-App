package com.arafa.mohamed.sawaeidblooddonation.adapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.arafa.mohamed.sawaeidblooddonation.R;
import com.arafa.mohamed.sawaeidblooddonation.models.BloodTypeModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class BloodTypeAdapter implements SpinnerAdapter {
    Context context;
    ArrayList <BloodTypeModel> listBloodType;

    public BloodTypeAdapter(Context context, ArrayList<BloodTypeModel> listBloodType) {
        this.context = context;
        this.listBloodType = listBloodType;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = new MyViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_blood_type,parent,false);

        myViewHolder.tvName = convertView.findViewById(R.id.text_blood_type);
        myViewHolder.imageBlood = convertView.findViewById(R.id.image_blood_type);

        myViewHolder.tvName.setText(listBloodType.get(position).name);
        Picasso.get().load(listBloodType.get(position).image).into(myViewHolder.imageBlood);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return listBloodType != null ? listBloodType.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = new MyViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       convertView =  layoutInflater.inflate(R.layout.item_blood_type,parent,false);

        myViewHolder.tvName = convertView.findViewById(R.id.text_blood_type);
        myViewHolder.imageBlood = convertView.findViewById(R.id.image_blood_type);

        myViewHolder.tvName.setText(listBloodType.get(position).name);
        Picasso.get().load(listBloodType.get(position).image).into(myViewHolder.imageBlood);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    public class MyViewHolder {
        AppCompatTextView tvName;
        AppCompatImageView imageBlood;

        public MyViewHolder() {
        }
    }
}
