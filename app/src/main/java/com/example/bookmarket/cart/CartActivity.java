package com.example.bookmarket.cart;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.R;
import com.example.bookmarket.model.Book;
import com.example.bookmarket.order.ShippingActivity;

import java.util.ArrayList;



public class CartActivity extends AppCompatActivity {

    CartAdapter cartAdapter;

    static CheckBox allChooseCheckBox;
    static TextView cartTotalPriceObj;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기


        //cartRepositoryObj = new CartRepository();
        cartTotalPriceObj = findViewById(R.id.cart_total_price);

        cartTotalPriceObj.setText(Integer.toString(cartRepositoryObj.grandTotalCartItems()));

        RecyclerView recyclerviewCart = findViewById(R.id.cart_recyclerview);
        cartAdapter = new CartAdapter(CartActivity.this);
        recyclerviewCart.setAdapter(cartAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(linearLayoutManager);
        //recyclerviewCart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        cartAdapter.setOnSelectChangedListener(item -> refreshRecyclerView());




        allChooseCheckBox = findViewById(R.id.cart_selectall_checkbox);
        allChooseCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            Log.d("222222" , "     "+b);

           if (b)
              doSelectAll();
            else
                doSelectNone();



        });


        Button orderButton  = findViewById(R.id.cart_order_button);

        orderButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, ShippingActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
            case R.id.order_button  :
                Intent intent = new Intent(CartActivity.this, ShippingActivity.class);
                startActivity(intent);
                break;


            case R.id.delete_button  :
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                alertDialog.setTitle("도서 상품 삭제");
                alertDialog.setMessage("장바구니에 담은 상품을 모두 삭제하겠습니까?");
                alertDialog.setIcon(R.drawable.dialog_cat);

                alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartRepositoryObj.cartBooks.clear();
                        setSelectAllCheckBoxState();
                        cartUpdate();

                    }
                });

                alertDialog.setNegativeButton("아니오", (dialogInterface, i) -> dialogInterface.cancel());

                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void  doSelectAll()  {

        int num = cartAdapter.getItemCount();
        Log.d("222222" , "     "+num);
        if (num > 0) {
            boolean isChanged = false;
            for (int i=0 ; i<=num-1;i++) {
                Book item = cartRepositoryObj.cartBooks.get(i);

                if (!item.isCheck) {
                    item.isCheck = true;
                    isChanged = true;
                }
            }
            if (isChanged) {
                refreshRecyclerView();
            }
        }
    }


    void doSelectNone() {
        int num = cartAdapter.getItemCount();
         Log.d("111111111" , "     "+num);
        if ( num > 0) {
            boolean isChanged = false;
            for (int i = 0; i<=num-1 ;i++) {
                Book item  = cartRepositoryObj.cartBooks.get(i);
                if (item.isCheck) {
                    item.isCheck = false;
                    isChanged = true;
                }
            }
            if (isChanged) {
                refreshRecyclerView();
            }
        }
    }

    private void refreshRecyclerView() {

        cartAdapter.notifyItemRangeRemoved(0, cartRepositoryObj.cartBooks.size());
        cartTotalPriceObj.setText(Integer.toString(cartRepositoryObj.grandTotalCartItems()));
        setSelectAllCheckBoxState();
    }
    private void setSelectAllCheckBoxState() {

        if (cartRepositoryObj.cartBooks == null) {
            allChooseCheckBox.setChecked(false);
            return;
        }
        for (int i=0 ; i < cartRepositoryObj.cartBooks.size(); i++) {
            if (!cartRepositoryObj.cartBooks.get(i).isCheck) {
                allChooseCheckBox.setChecked(false);
                return;
            }
        }
        allChooseCheckBox.setChecked(true);

    }

    public static void cartUpdate(){
        cartRepositoryObj.countCartItems();
        cartTotalPriceObj.setText(Integer.toString(cartRepositoryObj.grandTotalCartItems()));

    }

}
