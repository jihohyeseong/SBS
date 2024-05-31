package com.example.bookmarket.order;

import static com.example.bookmarket.MainActivity.cartRepositoryObj;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.KakaoPayApiService;
import com.example.bookmarket.R;
import com.example.bookmarket.RetrofitClient;
import com.example.bookmarket.cart.CartActivity;
import com.example.bookmarket.dto.KakaoPayReadyResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDate;
    private EditText etPhone;
    private EditText etZipcode;
    private EditText etAddress;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setDefaultValues();
        setupButtons();
    }

//        EditText etName = findViewById(R.id.editTextName);
//        EditText etDate = findViewById(R.id.editTextDate);
//        EditText etPhone = findViewById(R.id.editTextPhone);
//        EditText etZipcode = findViewById(R.id.editTextZipcode);
//        EditText etAddress = findViewById(R.id.editTextAddress);

        private void initializeViews() {
            etName = findViewById(R.id.editTextName);
            etDate = findViewById(R.id.editTextDate);
            etPhone = findViewById(R.id.editTextPhone);
            etZipcode = findViewById(R.id.editTextZipcode);
            etAddress = findViewById(R.id.editTextAddress);
        }

        private void setDefaultValues() {
            // 예시 디폴트 값 설정
            etName.setText("홍길동");
            etDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            etPhone.setText("010-1234-1234");
            etZipcode.setText("01234");
            etAddress.setText("서울시 성북구 상상대로 123");
        }

        private void setupButtons() {
            Button btCancel = findViewById(R.id.buttonCancel);
            Button btSubmit = findViewById(R.id.buttonSubmit);

            // 등록 버튼 클릭 이벤트 처리
            btSubmit.setOnClickListener(view -> {
                Intent intent = new Intent(ShippingActivity.this, OrderActivity.class);
                intent.putExtra("name", etName.getText().toString().trim());
                intent.putExtra("date", etDate.getText().toString().trim());
                intent.putExtra("phone", etPhone.getText().toString().trim());
                intent.putExtra("zipcode", etZipcode.getText().toString().trim());
                intent.putExtra("address", etAddress.getText().toString().trim());
                intent.putExtra("total", String.valueOf(cartRepositoryObj.grandTotalCartItems()));
                startActivity(intent);
            });

            // 주문취소 버튼 클릭 이벤트 처리
            btCancel.setOnClickListener(view -> {
                Intent intent = new Intent(ShippingActivity.this, CartActivity.class);
                startActivity(intent);
            });
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
