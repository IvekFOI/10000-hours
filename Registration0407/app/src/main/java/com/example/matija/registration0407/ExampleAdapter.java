package com.example.matija.registration0407;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Exampleitem> mExampleList;
    public ImageView mDeleteImage;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<Exampleitem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_items, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Exampleitem currentItem = mExampleList.get(position);

        String catexada = currentItem.getCatexa();
        String subcatexada = currentItem.getSubcatexa();
        String vrijemeexada = currentItem.getTimeexa();

        holder.mTextViewCat.setText("Category : " + catexada);
        holder.mTextViewSub.setText("Subcategory : " + subcatexada);
        holder.mTextViewVrijeme.setText("Time : " + vrijemeexada);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewCat;
        public TextView mTextViewSub;
        public TextView mTextViewVrijeme;


        public ExampleViewHolder(View itemView) {
            super(itemView);

            mTextViewCat = itemView.findViewById(R.id.tvcatrecy);
            mTextViewSub= itemView.findViewById(R.id.tvsubcatrecy);
            mTextViewVrijeme= itemView.findViewById(R.id.tvvrijemerec);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!= null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!= null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }

                }
            });

            }
        }
}