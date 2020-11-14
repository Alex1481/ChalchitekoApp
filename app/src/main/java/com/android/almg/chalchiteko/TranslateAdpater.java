package com.android.almg.chalchiteko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TranslateAdpater extends RecyclerView.Adapter<TranslateAdpater.SearchViewHolder>{
    Context context;
    ArrayList<String> chalchitekoList;
    ArrayList<String> spanishList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView id_text, content;

        public SearchViewHolder(View itemView) {
            super(itemView);
            id_text = (TextView) itemView.findViewById(R.id.id_text);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public TranslateAdpater(Context context, ArrayList<String> chalchitekoList, ArrayList<String> spanishList) {
        this.context = context;
        this.chalchitekoList = chalchitekoList;
        this.spanishList = spanishList;
    }

    @Override
    public TranslateAdpater.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.palabra_list_content, parent, false);
        return new TranslateAdpater.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.id_text.setText(spanishList.get(position));
        holder.content.setText(chalchitekoList.get(position));
    }


    @Override
    public int getItemCount() {
        return spanishList.size();
    }
}
