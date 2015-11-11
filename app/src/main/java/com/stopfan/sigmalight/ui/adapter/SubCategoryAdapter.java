package com.stopfan.sigmalight.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stopfan.sigmalight.R;
import com.stopfan.sigmalight.core.models.SubCategory;

import java.util.List;

/**
 * Created by Denys on 11/11/2015.
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubViewHolder> {

    private Context context;

    private List<SubCategory> subCategories;

    public SubCategoryAdapter(Context context, List<SubCategory> subCategories) {
        this.context = context;
        this.subCategories = subCategories;
    }

    @Override
    public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item, null);
        return new SubViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(SubViewHolder holder, int position) {
        SubCategory category = subCategories.get(position);
        Picasso.with(context)
                .load(category.getImage())
                .into(holder.image);
        holder.title.setText(category.getTitle());
        holder.address.setText(category.getAddress());
        holder.description.setText(category.getDescription());
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView address;
        public TextView description;

        public SubViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.sub_image);
            title = (TextView) itemView.findViewById(R.id.sub_title);
            address = (TextView) itemView.findViewById(R.id.sub_address);
            description = (TextView) itemView.findViewById(R.id.sub_description);
        }
    }

}
