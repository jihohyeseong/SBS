package com.example.bookmarket.dto;

public class BookDto {
    public String ID;

    public Long id;
    public String author;
    public String publisher;
    public Long price;
    public String category;
    public String bookname;
    public String releasedate;
    public String imageurl;
    public Long unitsinstock;

    public String description;

    public Long buy_num;
    public int quantity; // 장바구니에 담은 도서 개수

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




    public BookDto(Long id, String author, String publisher, Long price, String description, String bookname, Long buy_num, String category, String releasedate, String imageurl, Long unitsinstock) {
        // 생성자 내용
        this.id = id;
        this.publisher = publisher;
        this.description = description;
        this.bookname = bookname;
        this.buy_num = buy_num;
        this.price = price;
        this.category = category;
        this.releasedate = releasedate;
        this.imageurl = imageurl;
        this.unitsinstock = unitsinstock;
        this.author = author;
    }



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

    public Long getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getBookname() {
        return bookname;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getDescription() {return description;}

    public Long getBuy_num() {return buy_num;}

    public String getImageurl() {
        return imageurl;
    }

    public Long getUnitsinstock() {
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

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBookName(String bookname) {
        this.bookname = bookname;
    }

    public void setReleaseDate(String releasedate) {
        this.releasedate = releasedate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBuy_num(Long buy_num) {
        this.buy_num = buy_num;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setUnitsInStock(Long unitsinstock) {
        this.unitsinstock = unitsinstock;
    }


}
