package com.example.bookmarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bookmarket.dto.BookDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDialogFragment extends DialogFragment {

    private EditText editTextBookId;
    private EditText editTextBookAuthor;
    private EditText editTextBookPrice;
    private EditText editTextDescription;
    private EditText editTextBuyNum;
    private EditText editTextBookPublisher;
    private EditText editTextBookCategory;
    private EditText editTextBookName;
    private EditText editTextReleaseDate;
    private EditText editTextImageUrl;
    private EditText editTextUnitsInStock;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_book, null);

        //editTextBookId = view.findViewById(R.id.id);
        editTextBookAuthor = view.findViewById(R.id.author);
        editTextBookPublisher = view.findViewById(R.id.publisher);
        editTextBookPrice = view.findViewById(R.id.price);
        editTextBookCategory = view.findViewById(R.id.category);
        editTextBookName = view.findViewById(R.id.book_name);
        editTextDescription = view.findViewById(R.id.description);
        editTextBuyNum = view.findViewById(R.id.buy_num);
        editTextReleaseDate = view.findViewById(R.id.release_date);
        editTextImageUrl = view.findViewById(R.id.image_url);
        editTextUnitsInStock = view.findViewById(R.id.units_in_stock);


        builder.setView(view)
                .setTitle("도서 등록")
                .setPositiveButton("등록", (dialog, which) -> {
                    // 등록 버튼 클릭 시 도서 정보를 처리하는 코드 작성
                    //Long bookId = Long.valueOf(editTextBookId.getText().toString().trim());
                    String bookAuthor = editTextBookAuthor.getText().toString().trim();
                    String bookPublisher = editTextBookPublisher.getText().toString().trim();
                    String bookPriceStr = editTextBookPrice.getText().toString().trim();
                    String bookCategory = editTextBookCategory.getText().toString().trim();
                    String bookName = editTextBookName.getText().toString().trim();
                    String bookDescription = editTextDescription.getText().toString().trim();
                    String bookBuyNumStr = editTextBuyNum.getText().toString().trim();
                    String bookReleaseDate = editTextReleaseDate.getText().toString().trim();
                    String bookImageUrl = editTextImageUrl.getText().toString().trim();
                    String bookUnitsInStockStr = editTextUnitsInStock.getText().toString().trim();


                    // 입력값이 유효한지 확인하고 처리하는 코드 작성
                    if (!TextUtils.isEmpty(bookPriceStr) && !TextUtils.isEmpty(bookUnitsInStockStr) && !TextUtils.isEmpty(bookBuyNumStr)) {
                        // 유효성 검사: 숫자인지 확인
                        if (TextUtils.isDigitsOnly(bookPriceStr) && TextUtils.isDigitsOnly(bookUnitsInStockStr) && TextUtils.isDigitsOnly(bookBuyNumStr)) {
                            // 입력값이 유효할 때만 도서 등록 처리 메소드 호출
                            Long bookPrice = Long.valueOf(bookPriceStr);
                            Long bookUnitsInStock = Long.valueOf(bookUnitsInStockStr);
                            Long bookBuyNum = Long.valueOf(bookBuyNumStr);

                            addBook(bookAuthor,bookPublisher, bookPrice, bookCategory, bookName, bookDescription, bookBuyNum, bookReleaseDate, bookImageUrl, bookUnitsInStock);
                        } else {
                            // 사용자에게 알림: 올바른 숫자를 입력하도록 유도
                            Toast.makeText(getActivity(), "올바른 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 사용자에게 알림: 모든 필드를 입력하도록 유도
                        Toast.makeText(getActivity(), "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    // 취소 버튼 클릭 시 다이얼로그 종료
                    dismiss();
                });

        return builder.create();
    }

    // 도서 등록을 처리하는 메소드
    private void addBook(String author, String publisher, Long price, String category, String bookname, String description, Long buy_num, String releasedate, String imageurl, Long unitsinstock) {

        // 여기에 도서 등록 처리 코드 작성
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);

        BookDto books = new BookDto(
                null, // id는 서버에서 자동으로 생성되므로 null로 설정합니다.
                author,
                publisher,
                price,
                description,
                bookname,
                buy_num,
                category,
                releasedate,
                imageurl,
                unitsinstock
        );
        // Retrofit을 사용하여 서버에 도서 정보를 전송하는 요청 생성
        Call<Void> call = apiService.addBook(books);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 성공적으로 도서가 추가된 경우
                    Void message = response.body();
                    Log.e("POST", "성공");
                    // 처리할 작업 수행
                } else {
                    // 도서 추가 실패
                    Log.e("POST", "실패");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 네트워크 오류 등의 이유로 요청이 실패한 경우
                Log.e("retrofit 연동", "연동실패");
            }
        });
    }
}