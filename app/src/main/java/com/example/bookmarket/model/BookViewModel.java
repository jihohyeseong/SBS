package com.example.bookmarket.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {
    private MutableLiveData<Book> selectedBook = new MutableLiveData<>();

    public void setBook(Book book) {
        selectedBook.setValue(book);
    }

    public LiveData<Book> getBook() {
        return selectedBook;
    }
}