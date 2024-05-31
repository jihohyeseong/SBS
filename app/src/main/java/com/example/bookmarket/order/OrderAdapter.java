package com.example.bookmarket.order;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmarket.R;
import com.example.bookmarket.dto.BookDto;
import com.example.bookmarket.dto.CartDto;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
        private Context context;
        private List<CartDto> cartDtoList; // 변경: CartDto 리스트로 수정

        public OrderAdapter(Context context, List<CartDto> cartDtoList) {
                this.context = context;
                this.cartDtoList = cartDtoList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_order_item, parent, false);
                return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                CartDto cartItem = cartDtoList.get(position);
                holder.bind(cartItem);
        }

        @Override
        public int getItemCount() {
                return cartDtoList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                private TextView bookName;
                private TextView price;
                private TextView quantity;
                private TextView sum;

                public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        bookName = itemView.findViewById(R.id.title);
                        price = itemView.findViewById(R.id.price);
                        quantity = itemView.findViewById(R.id.quantity);
                        sum = itemView.findViewById(R.id.sum);
                }

                public void bind(CartDto cartItem) {
                        bookName.setText(cartItem.getBookname());
                        price.setText(String.format("%,d", cartItem.getPrice()));
                        quantity.setText(String.valueOf(cartItem.getQuantity()));

                        // 금액 계산을 long 타입으로 처리하여 안전성 보장
                        long totalPrice = cartItem.getPrice() * cartItem.getQuantity();
                        sum.setText(String.format("%,d", totalPrice));
                }
        }
}