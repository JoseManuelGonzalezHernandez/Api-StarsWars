package com.example.githubhunter.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubhunter.R;
import com.example.githubhunter.entities.PlanetEntity;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>{
    private static final String TAG = PlanetAdapter.class.getSimpleName();
    private final ListItemClickListener onClickListener;

    public PlanetEntity[] repoData;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemindex);
    }

    public PlanetAdapter(ListItemClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public int getItemCount() {
        if (repoData == null) {
            return 0;
        }
        return repoData.length;
    }

    public void setRepoData(PlanetEntity[] repositories) {
        repoData = repositories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentInmediately = false;

        View view = inflater.inflate(layoutIdForItem, parent,shouldAttachToParentInmediately);
        PlanetViewHolder viewHolder = new PlanetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        PlanetEntity repo = repoData[position];
        Log.i("Juan", "Binding position" + position);
        holder.bind(repo);
    }


    class PlanetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView gravity;
        TextView terrain;

        public PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            gravity = (TextView) itemView.findViewById(R.id.gravity);
            terrain = (TextView) itemView.findViewById(R.id.terrain);
            itemView.setOnClickListener(this);
        }

        void bind(PlanetEntity planet) {
            name.setText(planet.name);
            gravity.setText(planet.gravity);
            terrain.setText(planet.terrain);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAbsoluteAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }


}
