package com.example.bookmarket.order;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.MainActivity;
import com.example.bookmarket.R;


public class OrderActivity extends AppCompatActivity {
    TextView orderTotalObj;
    OrderAdapter  orderAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String phone = intent.getStringExtra("phone");
        String zipcode = intent.getStringExtra("zipcode");
        String address = intent.getStringExtra("address");
        String total = intent.getStringExtra("total");


        TextView tvDate = findViewById(R.id.textViewDate);
        TextView tvName = findViewById(R.id.textViewName);
        TextView tvZipcode = findViewById(R.id.textViewZipcode);
        TextView tvAddress = findViewById(R.id.textViewAddress);
        TextView tvTotal = findViewById(R.id.textViewTotal);

        tvDate.setText("배송일 : " + date);
        tvName.setText("성명 : " + name + "(" + phone + ")");
        tvZipcode.setText("우편번호 : " + zipcode);
        tvAddress.setText("주소 : " + address);
        tvTotal.setText(total);



        RecyclerView recyclerviewCart = findViewById(R.id.order_recyclerview);
        orderAdapter = new OrderAdapter(OrderActivity.this);
        recyclerviewCart.setAdapter(orderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(linearLayoutManager);

        Button orderSubmit = findViewById(R.id.buttonSubmit);
        Button orderCancel = findViewById(R.id.buttonCancel);

        //주문 완료 버튼 클릭 이벤트 처리
        orderSubmit.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this);
            alertDialog.setTitle("도서 상품 주문 완료");
            alertDialog.setMessage("주문해 주셔서 감사합니다.");
            alertDialog.setIcon(R.drawable.dialog_cat);


            alertDialog.setPositiveButton("예", (dialogInterface, i) -> {
                cartRepositoryObj.cartBooks.clear();
                Intent intent1 = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent1);
            });

            alertDialog.setNegativeButton("아니오", (dialogInterface, i) -> dialogInterface.cancel());
            alertDialog.show();


        });
        // 주문취소 버튼 클릭 이벤트 처리
        orderCancel.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this);
            alertDialog.setTitle("도서 상품 주문 취소");
            alertDialog.setMessage("주문을 취소합니다.");
            alertDialog.setIcon(R.drawable.dialog_cat);


            alertDialog.setPositiveButton("예", (dialogInterface, i) -> {
                cartRepositoryObj.cartBooks.clear();
                Intent intent12 = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent12);
               finish();
            });


        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
