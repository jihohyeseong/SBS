package com.example.bookmarket.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookmarket.MainActivity;
import com.example.bookmarket.R;
import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.CartRepository;
import com.example.bookmarket.model.Book;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartDto> cartItems;
    private List<Book> books;
    private Context context;
    private OnSelectChangedListener selectChangedListener;

    public CartAdapter(Context context, List<CartDto> cartItems) {
        this.context = context;
        this.cartItems = new ArrayList<>(cartItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDto cartItem = cartItems.get(position);

        holder.cartTitle.setText(cartItem.getBookname());
        holder.cartPrice.setText(String.format("%,d원", cartItem.getPrice()));
        holder.cartQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.cartSum.setText(String.format("%,d원", cartItem.getPrice() * cartItem.getQuantity()));
        holder.cartCheckBox.setChecked(cartItem.isCheck());

        // Image Loading with Glide
        if (cartItem.getImageUrl() != null && !cartItem.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(cartItem.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)  // Set your placeholder image
                    .error(R.drawable.baseline_error_24)  // Set your error image
                    .centerInside()
                    .into(holder.cartPicture);
        } else {
            Glide.with(context).load(R.drawable.placeholder_image).into(holder.cartPicture);  // Load a default image if the URL is not valid
        }



        // 체크박스 변경 리스너 설정
        holder.cartCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cartItem.setCheck(isChecked);
            selectChangedListener.onSelectChanged(cartItems);
        });

        // 삭제 버튼 리스너 설정
        holder.cartDelete.setOnClickListener(view -> showDeleteDialog(cartItem));
    }

    private void showDeleteDialog(CartDto cartItem) {
        new AlertDialog.Builder(context)
                .setTitle("도서 상품 삭제")
                .setMessage("선택한 도서 상품을 삭제하시겠습니까?")
                .setIcon(R.drawable.dialog_cat)
                .setPositiveButton("예", (dialog, which) -> {
                    CartRepository.getInstance().removeCartItem(cartItem);
                    cartItems.remove(cartItem);
                    notifyDataSetChanged();
                    selectChangedListener.onSelectChanged(cartItems);
                })
                .setNegativeButton("아니오", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public interface OnSelectChangedListener {
        void onSelectChanged(List<CartDto> items);
    }

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.selectChangedListener = listener;
    }

    public void setCartItems(List<CartDto> cartItems) {
        this.cartItems = new ArrayList<>(cartItems);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartPicture;
        TextView cartTitle, cartPrice, cartQuantity, cartSum;
        CheckBox cartCheckBox;
        ImageButton cartDelete;
        CardView cartParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartPicture = itemView.findViewById(R.id.cart_pic);
            cartTitle = itemView.findViewById(R.id.title);
            cartPrice = itemView.findViewById(R.id.price);
            cartCheckBox = itemView.findViewById(R.id.cart_item_checkbox);
            cartQuantity = itemView.findViewById(R.id.quantity);
            cartSum = itemView.findViewById(R.id.sum);
            cartDelete = itemView.findViewById(R.id.cart_delete);
            cartParentLayout = itemView.findViewById(R.id.cart_parent_layout);
        }
    }
}
