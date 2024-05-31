package com.example.bookmarket.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CartDto implements Parcelable {
    private Long id;
    private Long userId;
    private Long bookId;
    Long quantity;
    private String bookname;
    private Long price;
    private String author;
    private String publisher;
    private String imageUrl;
    private boolean checked;


    // 기본 생성자 및 인자를 받는 생성자는 여기에 정의됩니다.
    public CartDto(Long id, Long userId, Long bookId, Long quantity, String bookname, Long price, String author, String publisher, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookname = bookname;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
    }
    // BookDto를 받아들이는 생성자 추가
    public CartDto(Long bookId, String bookName, Long price, Long quantity, String imageUrl) {
        this.bookId = bookId;
        this.bookname = bookName;
        this.price = price;
        this.quantity = quantity;
        // Set other fields as needed, perhaps to default or null values
        this.id = null;
        this.userId = null;
        this.author = "";
        this.publisher = "";
        this.imageUrl = imageUrl;
        this.checked = true; // Default value if needed
    }

    protected CartDto(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        userId = (Long) in.readValue(Long.class.getClassLoader());
        bookId = (Long) in.readValue(Long.class.getClassLoader());
        quantity = (Long) in.readValue(Long.class.getClassLoader());
        bookname = in.readString();
        price = (Long) in.readValue(Long.class.getClassLoader());
        author = in.readString();
        publisher = in.readString();
        imageUrl = in.readString();
        checked = in.readByte() != 0;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCheck(boolean checked) {
        this.checked = checked;
    }

    public boolean isCheck() {
        return this.checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(bookId);
        dest.writeValue(quantity);
        dest.writeString(bookname);
        dest.writeValue(price);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    public static final Parcelable.Creator<CartDto> CREATOR = new Parcelable.Creator<CartDto>() {
        @Override
        public CartDto createFromParcel(Parcel in) {
            return new CartDto(in);
        }

        @Override
        public CartDto[] newArray(int size) {
            return new CartDto[size];
        }
    };
}