package com.example.bookmarket.cart;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;
import static com.example.bookmarket.cart.CartActivity.cartUpdate;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.bookmarket.R;
import com.example.bookmarket.model.Book;


import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;


    public CartAdapter(Context context) {
        this.context = context;
    }

    private CartAdapter.OnSelectChangedListener  selectChangedListener;

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        Book book = cartRepositoryObj.cartBooks.get(position);

        holder.cartTitle.setText(book.name);
        holder.cartPrice.setText(Integer.toString(book.price));
        holder.cartCheckBox.setChecked(book.isCheck);
        holder.cartQuantity.setText(Integer.toString(book.quantity));
        holder.cartSum.setText(Integer.toString(book.price * book.quantity));

        switch (book.bookid) {
            case "BOOK1234":
                holder.cartPicture.setImageResource(R.drawable.book11);
                break;

            case "BOOK1235":
                holder.cartPicture.setImageResource(R.drawable.book21);
                break;

            case "BOOK1236":
                holder.cartPicture.setImageResource(R.drawable.book31);
                break;


            case "BOOK1237":
                holder.cartPicture.setImageResource(R.drawable.book41);
                break;
        }

        holder.cartCheckBox.setOnClickListener(view -> {
            book.isCheck = holder.cartCheckBox.isChecked();
            selectChangedListener.onSelectChanged(cartRepositoryObj.cartBooks);
        });
        holder.cartDelete.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("도서 상품 삭제");
            alertDialog.setMessage("선택한 도서 상품을 삭제하겠습니까?");
            alertDialog.setIcon(R.drawable.dialog_cat);

            alertDialog.setPositiveButton("예", (dialogInterface, i) -> {
                // Log.d("111111111" , "     "+position);
                Book book1 = cartRepositoryObj.cartBooks.get(position);
                cartRepositoryObj.cartBooks.remove(book1);

                cartUpdate();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartRepositoryObj.cartBooks.size());


                // selectChangedListener.onSelectChanged(cartRepositoryObj.cartBooks);

                dialogInterface.cancel();


            });
            alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return cartRepositoryObj.cartBooks.size();
    }

    interface OnSelectChangedListener {
        void onSelectChanged(ArrayList<Book> item );
    }


    void setOnSelectChangedListener( OnSelectChangedListener listener) {
        selectChangedListener = listener;
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartPicture;
        TextView cartTitle;
        TextView cartPrice;
        CardView cartParentLayout;
        CheckBox cartCheckBox;
        ImageButton cartDelete;
        TextView cartQuantity;
        TextView cartSum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartPicture = itemView.findViewById(R.id.cart_pic);
            cartTitle = itemView.findViewById(R.id.title);
            cartPrice = itemView.findViewById(R.id.price);
            cartParentLayout = itemView.findViewById(R.id.cart_parent_layout);
            cartDelete = itemView.findViewById(R.id.cart_delete);

            cartCheckBox= itemView.findViewById(R.id.cart_item_checkbox);
            cartQuantity = itemView.findViewById(R.id.quantity);
            cartSum = itemView.findViewById(R.id.sum);
        }


    }



}
