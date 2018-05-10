package com.sososeen09.sample.itembinder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sososeen09.sample.R;
import com.sososeen09.sample.bean.Attribute;
import com.sososeen09.sample.bean.AttributeWithType;
import com.sososeen09.sample.bean.Name;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created on 2018/5/10.
 *
 * @author sososeen09
 */

public class NameBinder extends ItemViewBinder<AttributeWithType, NameBinder.NameHolder> {


    @NonNull
    @Override
    protected NameHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_name, parent, false);
        return new NameHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull NameHolder holder, @NonNull AttributeWithType item) {
        Attribute attributes = item.getAttributes();
        if (attributes instanceof Name) {
            Name name = (Name) attributes;
            holder.tvName.setText(name.getFirstname()+" "+ name.getLastname());
        }
    }

    class NameHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        public NameHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
