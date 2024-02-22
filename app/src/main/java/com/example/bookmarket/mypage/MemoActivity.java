package com.example.bookmarket.mypage;

import static java.time.LocalDate.*;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class MemoActivity extends AppCompatActivity {
    DatabaseHelperMemo dbHelper;
    int  selectItemPos ;
    SimpleAdapter adapter;
    ListView lv ;
    ArrayList<HashMap<String, String>> memoList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기

        dbHelper = new DatabaseHelperMemo(this);
        memoList = new ArrayList<HashMap<String, String>>();

        lv = findViewById(R.id.memo_list);
        Button insert  = findViewById(R.id.btnInsert);
        Button update  = findViewById(R.id.btnUpdate);
        Button delete  = findViewById(R.id.btnDelete);

        ReadData(); // 데이터 조회 메서드 호출

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItemPos = i; //항목 선택 위치
            }
        });

        insert.setOnClickListener(new View.OnClickListener() { // ① 등록 버튼 클릭 이벤트 리스너
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this); // ② 등록 대화상자 보이기 시작
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_memo_dialog, null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.show();// ② 끝


                Button dlginsert = dialogView.findViewById(R.id.insert);
                Button dlgcancel = dialogView.findViewById(R.id.cancel);

                dlginsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalDate date = LocalDate.now();
                        EditText title = dialogView.findViewById(R.id.title);
                        EditText content = dialogView.findViewById(R.id.content);
                        dbHelper.insertData(date.toString(), title.getText().toString(), content.getText().toString());
                        dialog.dismiss();
                        ReadData();
                    }
                }); // ③ 끝
                dlgcancel.setOnClickListener(new View.OnClickListener() { // ④ 대화상자의 삭제 버튼 클릭 이벤트 리스너 시작
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });  // ④ 끝
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog  = new AlertDialog.Builder(MemoActivity.this); // ② 등록 대화상자 보이기 시작
                alertDialog.setTitle("메모장 삭제");
                alertDialog.setMessage("선택한 메모를 삭제하겠습니까?");
                alertDialog.setIcon(R.drawable.dialog_cat);

                alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        int count = adapter.getCount();
                        int checked;

                        if (count >= 0) {
                            checked = selectItemPos;

                            if (checked > -1 && checked < count) {

                                int id = Integer.parseInt(memoList.get(selectItemPos).get("id"));
                                dbHelper.deleteData(id);
                                //memoList.removeAt(checked);
                                memoList.clear();
                                ReadData();
                                //adapter.notifyDataSetChanged();
                            }
                        }
                    }

                });
                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                    }
                });

                if (selectItemPos> -1) alertDialog.show();
                else   Toast.makeText(getApplicationContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show();
              }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this); // ② 등록 대화상자 보이기 시작
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_memo_dialog, null);
                builder.setView(dialogView);

                EditText dlgtitle = dialogView.findViewById(R.id.title);
                EditText dlgcontent = dialogView.findViewById(R.id.content);

                Button dlginsert = dialogView.findViewById(R.id.insert);
                dlginsert.setText("수정");
                Button dlgcancel  = dialogView.findViewById(R.id.cancel);

                int count= adapter.getCount();
                int checked;

                if (count > 0) {
                    checked = selectItemPos;
                    if (checked > -1 && checked < count) {
                        //   val id :Int = memoList[selectItemPos].get("id").toString().toInt()
                        dlgtitle.setText(memoList.get(selectItemPos).get("title").toString());
                        dlgcontent.setText(memoList.get(selectItemPos).get("content").toString());

                        AlertDialog dialog = builder.show();// ② 끝

                        dlginsert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = Integer.parseInt(memoList.get(selectItemPos).get("id"));
                                LocalDate date = LocalDate.now();
                                EditText title  = dialogView.findViewById(R.id.title);
                                EditText content  = dialogView.findViewById(R.id.content);
                                dbHelper.updateData(date.toString(), title.getText().toString(), content.getText().toString(), id);
                                dialog.dismiss();
                                memoList.clear();


                                ReadData();
                            }
                        });

                        dlgcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    }
                    else Toast.makeText(getApplicationContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show();
                }


        }
        });
    }


    void ReadData(){
        Cursor cursor = dbHelper.readData();
        memoList.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "데이터를 찾을 수 없습니다", Toast.LENGTH_LONG).show();

        }
        StringBuffer buf = new StringBuffer();
        while(cursor.moveToNext()){

            HashMap<String, String> user = new HashMap<>();
            user.put("id", Integer.toString(cursor.getInt(0)));
            user.put("date", cursor.getString(1));
            user.put("title", cursor.getString(2));
            user.put("content",cursor.getString(3));

            memoList.add(user);

        }

        adapter= new SimpleAdapter(MemoActivity.this,
                memoList,
                R.layout.activity_memo_item,
                new String[]{"date", "title", "content"},
                new int[] {R.id.tvDate, R.id.tvTitle, R.id.tvContent}
        );

        lv.setAdapter(adapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search View Hint");
        //검색 액션 버튼의 검색어 이벤트 리스너
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { // 검색 액션 버튼 누를 때 호출

                memoList.clear();
                Cursor cursor = dbHelper.searchReadData(s.toString());

                StringBuffer buf = new StringBuffer();
                while(cursor.moveToNext()){

                    HashMap<String, String> memo = new HashMap<>();
                    memo.put("id", Integer.toString(cursor.getInt(0)));
                    memo.put("date", cursor.getString(1));
                    memo.put("title", cursor.getString(2));
                    memo.put("content",cursor.getString(3));

                    memoList.add(memo);

                }

                adapter= new SimpleAdapter(
                        MemoActivity.this,
                        memoList,
                        R.layout.activity_memo_item,
                        new String[]{"date", "title", "content"},
                        new int[] {R.id.tvDate, R.id.tvTitle, R.id.tvContent}
                );
                lv.setAdapter(adapter);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ReadData();
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
