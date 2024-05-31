package com.example.bookmarket.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Book implements Parcelable {
    public Long id;
    public String author;
    public String bookname;
    public Long buy_num;
    public String category;
    public String description;
    public Long price;
    public String releasedate;
    public String imageurl;
    public String publisher;
    public Long unitsinstock;
    public int quantity =0 ; // 장바구니에 담은 도서 개수
    public boolean isCheck = false; // 장바구니에서 체크박스 선택 여부


    // Getter methods
    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Long getPrice() { return price;}
    public Long getBuy_num() { return buy_num;}

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getBookName() {
        return bookname;
    }

    public String getRelease_date() {
        return releasedate;
    }

    public String getImage_url() {
        return imageurl;
    }

    public Long getUnits_in_stock() {
        return unitsinstock;
    }

    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(Long price) {this.price = price;}

    public void setBuy_num(Long buy_num) {this.buy_num = buy_num;}
    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookName(String bookname) {
        this.bookname = bookname;
    }

    public void setReleaseDate(String releasedate) {
        this.releasedate = releasedate;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setUnitsInStock(Long unitsinstock) {
        this.unitsinstock = unitsinstock;
    }


    // Parcelable 인터페이스 메서드 구현
    protected Book(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        author = in.readString();
        bookname = in.readString();
        if (in.readByte() == 0) {
            buy_num = null;
        } else {
            buy_num = in.readLong();
        }
        category = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        releasedate = in.readString();
        imageurl = in.readString();
        publisher = in.readString();
        if (in.readByte() == 0) {
            unitsinstock = null;
        } else {
            unitsinstock = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(author);
        dest.writeString(bookname);
        if (buy_num == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(buy_num);
        }
        dest.writeString(category);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(price);
        }
        dest.writeString(releasedate);
        dest.writeString(imageurl);
        dest.writeString(publisher);
        if (unitsinstock == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(unitsinstock);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}