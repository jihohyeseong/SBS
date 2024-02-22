package com.example.bookmarket.order;


import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmarket.R;
import com.example.bookmarket.model.Book;

class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


        Context context;


        public OrderAdapter(Context context) {
                this.context = context;
        }

        @NonNull
        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_order_item, parent, false);

                return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Book book = cartRepositoryObj.cartBooks.get(position);
                String str;

                if (book.name.length() > 20) str = book.name.substring(20) + "...";
                else str = book.name;
                holder.orderTitle.setText(str);
                holder.rderPrice.setText(Integer.toString(book.price));
                holder.rderQuantity.setText(Integer.toString(book.quantity));
                holder.rderSum.setText(Integer.toString(book.price * book.quantity));
        }

        @Override
        public int getItemCount() {
                return cartRepositoryObj.cartBooks.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
                TextView orderTitle;
                TextView rderPrice;
                TextView rderQuantity;
                TextView rderSum;

                public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        orderTitle = itemView.findViewById(R.id.title);
                        rderPrice = itemView.findViewById(R.id.price);
                        rderQuantity = itemView.findViewById(R.id.quantity);
                        rderSum = itemView.findViewById(R.id.sum);
                }
        }
}
