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
import com.example.githubhunter.entities.RepoEntity;

import org.w3c.dom.Text;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private static final String TAG = RepoAdapter.class.getSimpleName();
    private final ListItemClickListener onClickListener;

    public RepoEntity[] repoData;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemindex);
    }

    public RepoAdapter(ListItemClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public int getItemCount() {
        if (repoData == null) {
            return 0;
        }
        return repoData.length;
    }

    public void setRepoData(RepoEntity[] repositories) {
        repoData = repositories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentInmediately = false;

        View view = inflater.inflate(layoutIdForItem, parent,shouldAttachToParentInmediately);
        RepoViewHolder viewHolder = new RepoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        RepoEntity repo = repoData[position];

        holder.bind(repo);
    }


    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView repoName;
        TextView repoComment;
        TextView repoStars;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = (TextView) itemView.findViewById(R.id.repo_name);
            repoComment = (TextView) itemView.findViewById(R.id.repo_comment);
            repoStars = (TextView) itemView.findViewById(R.id.repo_stars);
            itemView.setOnClickListener(this);
        }

        void bind(RepoEntity repository) {
            repoName.setText(repository.fullName);
            repoComment.setText(repository.description);
            repoStars.setText(repository.stars);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }


}
