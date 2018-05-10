package com.sososeen09.sample.itembinder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sososeen09.sample.R;
import com.sososeen09.sample.bean.Address;
import com.sososeen09.sample.bean.Attribute;
import com.sososeen09.sample.bean.AttributeWithType;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created on 2018/5/10.
 *
 * @author sososeen09
 */

public class AddressBinder extends ItemViewBinder<AttributeWithType, AddressBinder.AddressHolder> {


    @NonNull
    @Override
    protected AddressHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new AddressHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull AddressHolder holder, @NonNull AttributeWithType item) {

        Attribute attributes = item.getAttributes();
        if (attributes instanceof Address) {
            Address address = (Address) attributes;
            holder.tvStreet.setText(address.getStreet());
            holder.tvCity.setText(address.getCity());
            holder.tvCountry.setText(address.getCountry());
        }
    }

    class AddressHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        TextView tvStreet;
        TextView tvCountry;

        public AddressHolder(View itemView) {
            super(itemView);
            tvStreet = itemView.findViewById(R.id.tv_street);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }
    }
}
