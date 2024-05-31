package com.example.bookmarket.cart;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.MyApiService;
import com.example.bookmarket.R;
import com.example.bookmarket.dto.BookDto;
import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.CartRepository;
import com.example.bookmarket.model.Book;
import com.example.bookmarket.model.BookViewModel;
import com.example.bookmarket.order.ShippingActivity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<BookDto> cartBooks;
    private List<CartDto> cartItems;
    static CheckBox allChooseCheckBox;
    static TextView cartTotalPriceObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setupActionBar();
        setupRecyclerView();
        setupCartSummaryAndActions();

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 장바구니 데이터 로드
        cartItems = CartRepository.getInstance().getCartBooks();
        cartAdapter = new CartAdapter(this, cartItems);
        cartAdapter.setOnSelectChangedListener(items -> cartUpdate());
        recyclerView.setAdapter(cartAdapter);

        allChooseCheckBox = findViewById(R.id.cart_selectall_checkbox);
        cartTotalPriceObj = findViewById(R.id.cart_total_price);

        Button orderButton = findViewById(R.id.cart_order_button);
        orderButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, ShippingActivity.class);
            startActivity(intent);
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

                alertDialog.setPositiveButton("예", (dialogInterface, i) -> {
                    // 수정된 부분: getCartBooks() 메소드를 사용하여 cartBooks에 접근
                    cartRepositoryObj.getCartBooks().clear();
                    setSelectAllCheckBoxState();
                    cartUpdate();
                });
                alertDialog.setNegativeButton("아니오", (dialogInterface, i) -> dialogInterface.cancel());

                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshRecyclerView() {
        cartAdapter.notifyDataSetChanged();
        cartTotalPriceObj.setText(Integer.toString(cartRepositoryObj.grandTotalCartItems()));
        setSelectAllCheckBoxState();
    }


    private void setSelectAllCheckBoxState() {
        if (cartRepositoryObj.getCartBooks() == null) { // 수정된 부분
            allChooseCheckBox.setChecked(false);
            return;
        }
        for (int i = 0; i < cartRepositoryObj.getCartBooks().size(); i++) { // 수정된 부분
            if (!cartRepositoryObj.getCartBooks().get(i).isCheck()) { // 수정된 부분
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

    public void updateCartSummary() {
        int total = 0;
        boolean allChecked = true;

        for (CartDto item : cartItems) {
            if (item.isCheck()) {
                total += item.getPrice() * item.getQuantity();
            } else {
                allChecked = false;
            }
        }

        cartTotalPriceObj.setText(String.valueOf(total));
        allChooseCheckBox.setChecked(allChecked);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateCartItems();
    }

    private void setupCartSummaryAndActions() {
        cartTotalPriceObj = findViewById(R.id.cart_total_price);
        CheckBox allChooseCheckBox = findViewById(R.id.cart_selectall_checkbox);
        Button orderButton = findViewById(R.id.cart_order_button);
        orderButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, ShippingActivity.class);
            startActivity(intent);
        });
    }

    private void updateCartItems() {
        List<CartDto> cartItems = CartRepository.getInstance().getCartBooks();
        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(this, cartItems);
            recyclerView.setAdapter(cartAdapter);
        } else {
            cartAdapter.setCartItems(cartItems);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCartItems(); // 화면이 다시 포커스를 받을 때 마다 장바구니 항목 업데이트
        refreshCartSummary(); // 장바구니 요약 정보 업데이트
    }

    private void refreshCartSummary() {
        int totalPrice = CartRepository.getInstance().grandTotalCartItems();
        cartTotalPriceObj.setText(String.format("%,d원", totalPrice));
    }
}