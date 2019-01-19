package com.example.sammrabatool.solutions5d.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammrabatool.solutions5d.list.CircleTransform;
import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.example.sammrabatool.solutions5d.utils.Tools;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterListInbox extends RecyclerView.Adapter<AdapterListInbox.ViewHolder> {

    private Context ctx;
    private List<Notification> items;
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView from, email, message, date, image_letter;
        public ImageView image, imaget1, imaget2;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            email = (TextView) view.findViewById(R.id.email);
            message = (TextView) view.findViewById(R.id.message);
            date = (TextView) view.findViewById(R.id.date);
            image_letter = (TextView) view.findViewById(R.id.image_letter);
            image = (ImageView) view.findViewById(R.id.list_image);
            lyt_checked = (RelativeLayout) view.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
            imaget1 = (ImageView) view.findViewById(R.id.image_listt1);
            imaget2 = (ImageView) view.findViewById(R.id.image_listt2);

        }


    }

    public AdapterListInbox(Context mContext, List<Notification> items) {
        this.ctx = mContext;
        this.items = items;
        selected_items = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Notification obj = items.get(position);

        // displaying text view data
        holder.from.setText(obj.touser);
        holder.email.setText(obj.message_name);
        holder.message.setText(obj.message_subject);
        holder.date.setText(obj.date);
        //holder.image.setImageURI(Uri.parse(inbox.imagelist));
        try {
            URL url = new URL(obj.imagelist);

         //   try {

                Picasso.get().load(obj.imagelist).transform(new CircleTransform()).into(holder.image);

            //      Toast.makeText(AdapterListInbox.this, "in try", Toast.LENGTH_SHORT).show();
            //    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
             //   holder.image.setImageBitmap(bmp);
              //  holder.imaget1.setVisibility(View.GONE);
                //  holder.imaget2.setVisibility(View.GONE);
         //   }
          //  catch (IOException error){

            //}
        }
        catch (MalformedURLException error) {
            //  Toast.makeText(ListMultiSelection.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();

        }



        //holder.image_letter.setText(inbox.from.substring(0, 1));
        // Toast.makeText(this, "in adapter", Toast.LENGTH_SHORT).show();
        holder.lyt_parent.setActivated(selected_items.get(position, false));

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, obj, position);
            }
        });

     /*   holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v, inbox, position);
                return true;
            }
        }); */

        //   toggleCheckedIcon(holder, position);
        // displayImage(holder, inbox);

    }

    private void displayImage(ViewHolder holder, Notification notification) {
        if (notification.image != null) {
            Tools.displayImageRound(ctx, holder.image, notification.image);
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(notification.color);
            holder.image_letter.setVisibility(View.VISIBLE);
        }

    }

    private void toggleCheckedIcon(ViewHolder holder, int position) {
        if (selected_items.get(position, false)) {
            holder.lyt_image.setVisibility(View.GONE);
            holder.lyt_checked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
            holder.lyt_checked.setVisibility(View.GONE);
            holder.lyt_image.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        }
    }

    public Notification getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void toggleSelection(int pos) {
        current_selected_idx = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        items.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface OnClickListener {
        void onItemClick(View view, Notification obj, int pos);

       // void onItemLongClick(View view, Inbox obj, int pos);
    }




}