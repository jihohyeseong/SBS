package com.example.bookmarket;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.view.MenuItemCompat;

import com.example.bookmarket.cart.CartActivity;
import com.example.bookmarket.model.Book;
import com.example.bookmarket.model.CartRepository;

public class BookActivity extends AppCompatActivity {
    ImageView imgObj;
    TextView bookidObj;
    TextView nameObj;
    TextView priceObj;
    TextView dateObj;
    TextView writerObj;
    TextView pageObj;
    TextView descriptionObj;
    TextView categoryObj;

    Button cartPlusObj;
    TextView cartCount;


    //CartRepository cartRepositoryObj;
    public static CartRepository cartRepositoryObj;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표 (<--) 표시

        cartRepositoryObj = MainActivity.cartRepositoryObj;

        imgObj = findViewById(R.id.book_iv01);
        bookidObj = findViewById(R.id.book_tv01);
        nameObj = findViewById(R.id.book_tv02);
        priceObj = findViewById(R.id.book_tv03);
        dateObj = findViewById(R.id.book_tv04);
        writerObj = findViewById(R.id.book_tv05);
        pageObj = findViewById(R.id.book_tv06);
        descriptionObj = findViewById(R.id.book_tv07);
        categoryObj = findViewById(R.id.book_tv08);

        Intent intent = getIntent(); // 전송된 인텐트 받기
        bookidObj.setText(intent.getStringExtra("bookid"));
        nameObj.setText(intent.getStringExtra("name"));
        priceObj.setText(intent.getStringExtra("price"));
        dateObj.setText(intent.getStringExtra("date"));
        writerObj.setText(intent.getStringExtra("writer"));
        pageObj.setText(intent.getStringExtra("page"));
        descriptionObj.setText(intent.getStringExtra("description"));
        categoryObj.setText(intent.getStringExtra("category"));

        switch(bookidObj.getText().toString()){ // 전송된 도서 ID에 대한 도서 이미지 출력
            case "BOOK1234" :
                imgObj.setImageResource(R.drawable.book11);
                break;
            case "BOOK1235" :
                imgObj.setImageResource(R.drawable.book21);
                break;

            case "BOOK1236" :
                imgObj.setImageResource(R.drawable.book31);
                break;

            case "BOOK1237" :
                imgObj.setImageResource(R.drawable.book41);
                break;
        }

        Book mBook = new Book();

        mBook.bookid = bookidObj.getText().toString();
        mBook.name = nameObj.getText().toString();
        mBook.price = Integer.parseInt(priceObj.getText().toString());
        mBook.date = dateObj.getText().toString();
        mBook.writer = writerObj.getText().toString();
        mBook.page =  pageObj.getText().toString();
        mBook.description = descriptionObj.getText().toString();
        mBook.category = categoryObj.getText().toString();
        mBook.quantity = 0;


        cartPlusObj = findViewById(R.id.book_bt01); // 도서주문 버튼 아이디 가져오기
        cartPlusObj.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

            alertDialog.setTitle("도서주문");
            alertDialog.setMessage("상품을 장바구니에 추가하겠습니까?");
            alertDialog.setIcon(R.drawable.dialog_cat);
            DialogInterface.OnClickListener listener  = (dialogInterface, i) -> {
                switch(i){
                    case DialogInterface.BUTTON_POSITIVE  :
                        cartRepositoryObj.addCartItems(mBook);

                        int count = Integer.parseInt(cartCount.getText().toString()) + 1;
                        cartCount.setText(Integer.toString(count));
                        dialogInterface.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE   :
                        dialogInterface.cancel();
                        break;
                }
            };

            alertDialog.setPositiveButton("예", listener); // '예' 버튼
            alertDialog.setNegativeButton("아니오", listener); // '아니요' 버튼
            alertDialog.show();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book, menu);

        MenuItem item = menu.findItem(R.id.menu_cart);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifyCount = (RelativeLayout)item.getActionView();
        cartCount = (TextView) notifyCount.findViewById(R.id.hotlist_hot);
        ImageView image = (ImageView)notifyCount.findViewById(R.id.hotlist_bell);


        cartCount.setText(Integer.toString(cartRepositoryObj.countCartItems()));

        //cartCount.setText(Integer.toString(count));
        image.setOnClickListener(view -> {
            Intent myIntent = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(myIntent);
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                break;
            case R.id.menu_home  :
                Toast.makeText(getApplicationContext(),"홈으로 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;
            case R.id.menu_list    :
                Toast.makeText(getApplicationContext(),"도서목록 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                finish();
                Intent intent2 = new Intent(this, BooksActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_cart  :
                Toast.makeText(getApplicationContext(),"장바구니 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() { // 액티비티 새로고침
        super.onResume();
        if (cartCount!=null) cartCount.setText(Integer.toString(cartRepositoryObj.countCartItems()));
    }


}

