package com.example.slf.getgps;

/**
 * Created by slf on 2016/10/5.
 */



import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by draft on 2015/7/28.
 */
public class MacAdapter extends ArrayAdapter<Mac> {

    private Context context;
    private int resourceId;
    private Handler hanGetImage;
    private ListView listView;
    private ArrayList<Bitmap> bitmapArrayList=new ArrayList<Bitmap>();


    public MacAdapter(Context context, int textViewResourceId, List<Mac> objects, ListView listView){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        this.listView=listView;



    }

    @Override
    public View getView( final int position,View convertView,ViewGroup parent){
        final Mac mac=getItem(position);
        View view;
        final ViewHolder viewHolder;
//        if(convertView==null){
        Log.d("test",  "convertView"+convertView);
        view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        viewHolder=new ViewHolder();
        viewHolder.linearLayout= (LinearLayout) view.findViewById(R.id.list_item);
        viewHolder.textName=(TextView)view.findViewById(R.id.name);
        viewHolder.textAddress=(TextView)view.findViewById(R.id.address);
        view.setTag(viewHolder);
        viewHolder.textName.setText(mac.getName());
        viewHolder.textAddress.setText(mac.getAddress());
        viewHolder.linearLayout.setBackgroundColor(mac.getLevel()*3241);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "wifi信号level: "+mac.getLevel(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    class ViewHolder{
        TextView textName,textAddress;
        LinearLayout linearLayout;
    }

}
