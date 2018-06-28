package com.example.oleg.newspaper.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oleg.newspaper.Controller.DetailsActivity;
import com.example.oleg.newspaper.DataBase.BaseHelper;
import com.example.oleg.newspaper.Model.Item;
import com.example.oleg.newspaper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LOADED = 0;
    private static final int LOADING = 1;

    private List<Item> itemList;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case LOADED:
                view = inflater.inflate(R.layout.repository_row, parent, false);
                return new ViewHolder(view);
            case LOADING:
                view = inflater.inflate(R.layout.item_progress, parent, false);
                return new ViewHolder(view);

        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Item item = itemList.get(position);

        switch (getItemViewType(position)) {
            case LOADED:
                final ViewHolder movieVH = (ViewHolder) holder;
                movieVH.title.setText(item.getTitle());
                movieVH.author.setText(item.getAuthor());
                Picasso.with(movieVH.articleImage.getContext())
                        .load(item.getUrlToImage())
                        .into( movieVH.articleImage);

                movieVH.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDetailsActivity(item);
                    }
                });
                movieVH.favoriteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movieVH.favoriteIcon.setImageResource(R.drawable.ic_favorite);
                        BaseHelper baseHelper = new BaseHelper(context);
                        baseHelper.createArticle(item);

                    }
                });
                movieVH.shareIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, item.getUrl());
                        try
                        {
                            context.startActivity(Intent.createChooser(intent, "Article URL"));
                        }
                        catch (android.content.ActivityNotFoundException ex)
                        {
                            Toast.makeText(context, "Some error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case LOADING:
                break;
        }
    }

    private void openDetailsActivity(Item item) {
        startDetailsActivtiy(item);
    }




    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = itemList.get(position);

        if (position == itemList.size() - 1 && isLoadingAdded) {
            return LOADING;
        } else {
                return LOADED;

        }

    }

    public void add(Item r) {
        itemList.add(r);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addAll(List<Item> results) {
        for (Item result : results) {
            add(result);
        }
    }

    public void remove(Item r) {
        int position = itemList.indexOf(r);
        if (position > -1) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemList.size() - 1;
        Item result = getItem(position);

        if (result != null) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void startDetailsActivtiy(Item item) {
        Intent myIntent = new Intent(context, DetailsActivity.class);
        myIntent.putExtra("url", item.getUrl());
        context.startActivity(myIntent);
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, author;
        private ImageView articleImage, favoriteIcon, shareIcon;

        private CardView cardView;
        private ProgressBar mProgress;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_tv_tag);
            author = (TextView) itemView.findViewById(R.id.article_author_tv);
            articleImage = (ImageView) itemView.findViewById(R.id.article_image);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.article_favorite);
            shareIcon = (ImageView) itemView.findViewById(R.id.article_share);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
