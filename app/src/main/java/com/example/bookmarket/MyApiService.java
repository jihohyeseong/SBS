package com.example.bookmarket;

import com.example.bookmarket.dto.BookDto;
import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.CommentDto;
import com.example.bookmarket.dto.JoinDto;
import com.example.bookmarket.dto.User;
import com.example.bookmarket.model.Book;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface MyApiService {

    //카테고리
    @GET("/api/books/search/{bookname}")
    Call<List<Book>> searchBooks(@Path("bookname") String bookname);
    //랭킹
    @GET("/api/books/ranking")
    Call<List<Book>> getBookRankings();
    //신간
    @GET("/api/books/new")
    Call<List<Book>> getNewBooks();

    @GET("/api/books/{id}")
    Call<Book> getBookById(@Path("id") String bookId);


    //사용자 정보를 조회, 수정, 삭제
    @GET("/api/mypage")
    Call<User> getUserInfo();

    @PATCH("/api/mypage")
    Call<User> updateUserInfo(@Body User user);

    @DELETE("/api/mypage")
    Call<Void> deleteUserInfo();
    @POST("/api/logout")
    Call<Void> logoutUser();

    @DELETE("/api/user/{userId}")
    Call<Void> deleteUserAccount();



    // 도서 추가
    @POST("/api/books")
    Call<Void> addBook(@Body BookDto books);

    @GET("/api/books")
    Call<List<Book>> index();

    // 도서 정보 보기
    @GET("/api/books/{id}")
    Call<Book> getBookById(@Path("id") Long id);

    // 도서 정보 업데이트
    @PATCH("/api/books/{id}")
    Call<Book> updateBook(@Path("id") Long id, @Body BookDto dto);

    // 도서 삭제
    @DELETE("/api/books/{id}")
    Call<Void> deleteBook(@Path("id") Long id);



    //회원가입
    @POST("/api/joinProc")
    Call<JoinResponse> register(@Body JoinDto joinDto);

    // 로그인
    @FormUrlEncoded
    @POST("/loginProc")
    Call<LoginResponse> login(@Field("username") String username,
                              @Field("password") String password);

    //장바구니 담기
    @POST("/api/books/{bookId}/cart")
    Call<CartDto> addCart(@Body CartDto cartDto, @Path("bookId") Long bookId);

    // 댓글 생성 요청을 위한 POST 메서드
    @POST("/api/books/{bookId}/comments")
    Call<CommentDto> createComment(@Path("bookId") Long bookId, @Body CommentDto commentDto);

    @GET("/api/books/{bookId}/comments")
    Call<List<CommentDto>> getCommentsForBook(@Path("bookId") Long bookId);
}

