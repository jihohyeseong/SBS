package com.example.bookmarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<CommentDto> comments = new ArrayList<>();

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentDto comment = comments.get(position);
        holder.commentText.setText(comment.getContent()); // content를 출력
        holder.userName.setText(comment.getUsername());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void addComment(CommentDto newComment) {
        comments.add(newComment);
        notifyItemInserted(comments.size() - 1);
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentText;
        TextView userName;

        CommentViewHolder(View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.commentText);
            userName = itemView.findViewById(R.id.userName);
        }
    }
}
