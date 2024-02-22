package com.example.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


import com.example.bookmarket.model.CartRepository;

public class BooksActivity extends AppCompatActivity {
    public static CartRepository cartRepositoryObj;

    ImageView colume1Obj;
    ImageView colume2Obj;

    LinearLayout layout1Obj;
    LinearLayout layout2Obj;

    ImageView book11Obj;
    ImageView book12Obj;
    ImageView book13Obj;
    ImageView book14Obj;
    ImageView book21Obj;
    ImageView book22Obj;
    ImageView book23Obj;
    ImageView book24Obj;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기

        colume1Obj = findViewById(R.id.books_iv02);
        colume2Obj = findViewById(R.id.books_iv03);
        layout1Obj = findViewById(R.id.list_layout01);
        layout2Obj = findViewById(R.id.list_layout02);

        book11Obj = findViewById(R.id.bookscol1_iv01); // 1열 도서 목록의 이미지 뷰 아이디를 저장할 변수
        book12Obj = findViewById(R.id.bookscol1_iv02);
        book13Obj = findViewById(R.id.bookscol1_iv03);
        book14Obj = findViewById(R.id.bookscol1_iv04);

        book21Obj = findViewById(R.id.bookscol2_iv01); // 2열 도서 목록의 이미지 뷰 아이디를 저장할 변수
        book22Obj = findViewById(R.id.bookscol2_iv02);
        book23Obj = findViewById(R.id.bookscol2_iv03);
        book24Obj = findViewById(R.id.bookscol2_iv04);

        // 1열 이미지 뷰 클릭 이벤트 추리
        book11Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"자바 코딩의 기술",Toast.LENGTH_LONG).show();
            BookItem1();
        });

        book12Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"머신 러닝을 다루는 기술",Toast.LENGTH_LONG).show();
            BookItem2();
        });
        book13Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"모던 리눅스 관리",Toast.LENGTH_LONG).show();
            BookItem3();
        });
        book14Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"유니티 교과서",Toast.LENGTH_LONG).show();
            BookItem4();
        });
        // 2열 이미지 뷰 클릭 이벤트 추리
        book21Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"자바 코딩의 기술",Toast.LENGTH_LONG).show();
            BookItem1();
        });
        book22Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"머신 러닝을 다루는 기술",Toast.LENGTH_LONG).show();
            BookItem2();
        });
        book23Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"모던 리눅스 관리",Toast.LENGTH_LONG).show();
            BookItem3();
        });
        book24Obj.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"유니티 교과서",Toast.LENGTH_LONG).show();
            BookItem4();
        });
    }

    public void onBookViewClick(View view){
        switch(view.getId()){
            case R.id.books_iv02: // 1열 도서 목록 보기 버튼 (1X4)
                colume1Obj.setImageResource(R.drawable.list_type1);
                colume2Obj.setImageResource(R.drawable.list_type22);
                layout1Obj.setVisibility(View.VISIBLE); // 1열 도서 목록 레이아웃 보이기
                layout2Obj.setVisibility(View.INVISIBLE); // 2열 도서 목록 레이아웃 감추기
                break;

            case R.id.books_iv03: // 2열 도서 목록 보기 버튼 (2X2)
                colume1Obj.setImageResource(R.drawable.list_type12);
                colume2Obj.setImageResource(R.drawable.list_type2);
                layout1Obj.setVisibility(View.INVISIBLE); // 1열 도서 목록 레이아웃 보이기
                layout2Obj.setVisibility(View.VISIBLE); // 2열 도서 목록 레이아웃 감추기
                break;
        }
    }

    public void BookItem1(){
        Intent intent  = new Intent(this, BookActivity.class);
        intent.putExtra("bookid", "BOOK1234");
        intent.putExtra("name", "자바 코딩의 기술");
        intent.putExtra("price", "22000");
        intent.putExtra("date", "2020-07-30");
        intent.putExtra("writer", "사이먼 하러,리누스 디에츠,요르그 레너드/심지현");
        intent.putExtra("page", "264쪽");
        intent.putExtra("description", "코딩 스킬을 개선하는 가장 좋은 방법은 전문가의 코드를 읽는 것이다. 오픈 소스 코드를 읽으면서 이해하면 좋지만, 너무 방대하고 스스로 맥락을 찾는 게 어려울 수 있다. 그럴 땐 이 책처럼 현장에서 자주 발견되는 문제 유형 70가지와 해법을 비교하면서 자신의 코드에서 개선할 점을 찾는 것이 좋다.");
        intent.putExtra("category", "프로그래밍/오픈소스");
        startActivity(intent);
    }

    public void BookItem2(){
        Intent intent  = new Intent(this, BookActivity.class);
        intent.putExtra("bookid", "BOOK1235");
        intent.putExtra("name", "머신 러닝을 다루는 기술 with 파이썬, 사이킷런");
        intent.putExtra("price", "34000");
        intent.putExtra("date", "2020-06-3");
        intent.putExtra("writer", "마크 페너/황준식");
        intent.putExtra("page", "624쪽");
        intent.putExtra("description", " 저자는 오랫동안 다양한 사람들에게 머신 러닝을 가르치면서 효과적인 학습 방법을 고안했고, 그대로 책에 담았다. 이 책은 그림과 스토리로 개념을 설명하고 바로 파이썬 코드로 구현하는 것에서 시작한다. 수학적 증명을 깊게 파고들거나 개념을 설명하기 위해 수식에 의존하지 않으며, 필요한 수학은 고등학교 수준으로 그때마다 첨가하여 설명한다. 또한, 바닥부터 모델을 구현하지 않고, 넘파이, 판다스, 사이킷런처럼 잘 구현된 강력한 파이썬 라이브러리를 사용해 실용적으로 접근한다. 개념과 기술을 잘 보여주는 양질의 예제를 직접 실행하며 머신 러닝 개념을 이해할 수 있다. ");
        intent.putExtra("category", "데이터베이스/데이터분석");
        startActivity(intent);
    }

    public void BookItem3(){
        Intent intent  = new Intent(this, BookActivity.class);
        intent.putExtra("bookid", "BOOK1236");
        intent.putExtra("name", "모던 리눅스 관리");
        intent.putExtra("price", "30000");
        intent.putExtra("date", "2019-10-10");
        intent.putExtra("writer", "데이비드 클린턴(David Cliton)/강석주");
        intent.putExtra("page", "472쪽");
        intent.putExtra("description", "이 책은 최신 기술을 활용한 리눅스 관리 방법을 가상화, 연결, 암호화, 네트워킹, 이미지관리, 시스템 모니터링의 6가지 주제로 나눠 설명한다. 가상 머신에 리눅스를 설치하고 서버를 구축하는 방법뿐만 아니라 구축 이후에 리눅스를 관리하고 운영하며 겪을수 있는 다양한 문제를 해결하는 방법까지 다룬다. VM과 컨테이너를 이용한 가상화, AWS S3를 이용한 데이터 백업, Nextcloud를 이용한 파일공유 서버 구축, 앤서블을 이용한 데브옵스 환경 구축 등 최신 기술을 활용한 실용적인 12가지 프로젝트로 실무에 필요한 리눅스 관리 방법을 배울 수 있다.");
        intent.putExtra("category", "임베디드/시스템/네트워크");
        startActivity(intent);
    }

    public void BookItem4(){
        Intent intent  = new Intent(this, BookActivity.class);
        intent.putExtra("bookid", "BOOK1237");
        intent.putExtra("name", "유니티 교과서");
        intent.putExtra("price", "28000");
        intent.putExtra("date", "2019-10-30");
        intent.putExtra("writer", "기타무라 마나미/김은철,유세라");
        intent.putExtra("page", "456쪽");
        intent.putExtra("description", "[유니티 교과서, 개정 3판]은 유니티를 사용해 2D/3D 게임과 애니메이션을 만들면서 유니티 기초 지식과 함께 게임 제작 흐름을 익히는 것을 목적으로 한다. 유니티를 설치한 후 C# 핵심 문법을 학습하고, 이어서 여섯 가지 2D/3D 게임을 ‘게임 설계하기 → 프로젝트와 씬 만들기 → 씬에 오브젝트 배치하기 → 스크립트 작성하기 → 스크립트 적용하기’ 단계로 만들어 보면서 게임 제작 흐름을 익힌다. ");
        intent.putExtra("category", "게임");
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_books, menu); // 메뉴파일 등록
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        // 옵션 메뉴 파일에서 검색하기 메뉴 가져오기


        //SearchView searchView = (SearchView) searchItem.getActionView();
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("검색어(도서명)을 입력해주세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 검색하기 액션 버튼 이벤트 처리
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home : // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
            case R.id.menu_home  :
                Toast.makeText(getApplicationContext(),"홈으로 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                break;
            case R.id.main_video   :
                Toast.makeText(getApplicationContext(),"동영상강좌 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_customer  :
                Toast.makeText(getApplicationContext(),"고객센터 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_mypage :
                Toast.makeText(getApplicationContext(),"마이페이지 메뉴가 클릭되었습니다",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

