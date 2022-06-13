package com.example.contentprovidertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public ContactsAdapter(Context context, int textViewResourceId, List<String> contacts){
        super(context,textViewResourceId,contacts);
        this.resourceId = textViewResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String contact = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contacts = view.findViewById(R.id.contacts);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.contacts.setText(String.valueOf(contact));
        return view;
    }
    class ViewHolder {
        public TextView contacts;
    }
}