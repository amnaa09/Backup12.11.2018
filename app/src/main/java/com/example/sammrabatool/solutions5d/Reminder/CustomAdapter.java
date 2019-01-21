package com.example.sammrabatool.solutions5d.Reminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sammrabatool.solutions5d.R;

import java.util.List;

/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context ctx;
    private List<Model> list;

    public CustomAdapter(Context ctx, List<Model> list) {

        inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.fyr.setText(list.get(position).getFyr());
        holder.id.setText(String.valueOf(list.get(position).getNotifcation_id()));
        holder.name.setText(list.get(position).getName());
        holder.date.setText(String.valueOf(list.get(position).getDate()));
        holder.status.setText(list.get(position).getStatus());
        holder.msg.setText(String.valueOf(list.get(position).getMessage()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected Button btn_plus, btn_minus;
        private TextView fyr, id,name,date,status,msg;
//        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
//imageView=(ImageView)itemView.findViewById(R.id.image);
            id=(TextView)itemView.findViewById(R.id.a);
            fyr = (TextView) itemView.findViewById(R.id.g);
            name = (TextView) itemView.findViewById(R.id.h);
            date = (TextView) itemView.findViewById(R.id.e);
            status = (TextView) itemView.findViewById(R.id.d);
            msg = (TextView) itemView.findViewById(R.id.b);

            btn_plus = (Button) itemView.findViewById(R.id.plus);
            btn_minus = (Button) itemView.findViewById(R.id.minus);

            btn_plus.setTag(R.integer.btn_plus_view, itemView);
            btn_minus.setTag(R.integer.btn_minus_view, itemView);
            btn_plus.setOnClickListener(this);
            btn_minus.setOnClickListener(this);

        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == btn_plus.getId()){

//                View tempview = (View) btn_plus.getTag(R.integer.btn_plus_view);
//                TextView tv = (TextView) tempview.findViewById(R.id.number);
//                int number = Integer.parseInt(tv.getText().toString()) + 1;
//                tv.setText(String.valueOf(number));
//                Recyclerview.modelArrayList.get(getAdapterPosition()).setName(name);

            } else if(v.getId() == btn_minus.getId()) {

//                View tempview = (View) btn_minus.getTag(R.integer.btn_minus_view);
//                TextView tv = (TextView) tempview.findViewById(R.id.number);
//                int number = Integer.parseInt(tv.getText().toString()) - 1;
//                tv.setText(String.valueOf(number));
//                Recyclerview.modelArrayList.get(getAdapterPosition()).setNumber(number);
            }
        }

    }
}


