package com.example.bookstore.service;

import com.example.bookstore.dto.CalculateDto;
import com.example.bookstore.dto.PurchaseDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.Purchase;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.PurchaseRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<PurchaseDto> orderinfo(Long userId) {
        List<Purchase> purchaseList = purchaseRepository.findByUserId(userId);
        List<PurchaseDto> dtos = new ArrayList<>();
        for(int i = 0; i < purchaseList.size(); i++){
            Purchase purchase = purchaseList.get(i);
            PurchaseDto dto = PurchaseDto.createOrderDto(purchase);
            Book book = purchase.getBook();
            dto.setBookname(book.getBookname());
            dto.setPrice(book.getPrice());
            dto.setAuthor(book.getAuthor());
            dto.setPublisher(book.getPublisher());
            dto.setImageUrl(book.getImageurl());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public PurchaseDto addOrder(Long userId, Long bookId, PurchaseDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("구매 실패. 로그인 필요"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("구매 실패. 대상 책 없음"));
        Long remainingStock = book.getUnitsinstock() - dto.getQuantity();
        if(remainingStock < 0){
            throw new IllegalArgumentException("재고 부족. 현재 재고 수량: " + book.getUnitsinstock());
        }
        book.setBuyNum(book.getBuyNum() + dto.getQuantity());
        book.setUnitsinstock(remainingStock);
        bookRepository.save(book);
        Purchase purchase = Purchase.createOrder(user, book, dto);
        Purchase ordered = purchaseRepository.save(purchase);
        return PurchaseDto.createOrderDto(ordered);
    }

    @Transactional
    public PurchaseDto deleteOrder(Long purchaseId) {
        Purchase target = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("환불 실패. 구매내역 없음"));
        Long bookId = target.getBook().getId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("환불 실패. 대상 책 없음"));
        book.setBuyNum(book.getBuyNum() - target.getQuantity());
        book.setUnitsinstock(book.getUnitsinstock() + target.getQuantity());
        purchaseRepository.delete(target);
        return PurchaseDto.createOrderDto(target);
    }

    public List<CalculateDto> getCalculate() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        List<CalculateDto> calculateDtos = new ArrayList<>();
        for(int i = 0; i < purchaseList.size(); i++){
            Purchase purchase = purchaseList.get(i);
            CalculateDto calculateDto = CalculateDto.createCalculateDto(purchase);
            Long price = purchase.getBook().getPrice();
            calculateDto.setPrice(purchase.getQuantity()*price);
            calculateDtos.add(calculateDto);
        }
        return calculateDtos;
    }

    public Long calculateTotal() {
        return purchaseRepository.calculateTotalPurchaseAmount();
    }

    @Transactional
    public PurchaseDto addOrderInCart(Long userId, Long bookId, Long cartId, PurchaseDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("구매 실패. 로그인 필요"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("구매 실패. 대상 책 없음"));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("구매 실패. 대상 책 없음"));
        Long remainingStock = book.getUnitsinstock() - dto.getQuantity();
        if(remainingStock < 0){
            throw new IllegalArgumentException("재고 부족. 현재 재고 수량: " + book.getUnitsinstock());
        }
        book.setBuyNum(book.getBuyNum() + dto.getQuantity());
        book.setUnitsinstock(remainingStock);
        bookRepository.save(book);
        cartRepository.delete(cart);
        Purchase purchase = Purchase.createOrder(user, book, dto);
        Purchase ordered = purchaseRepository.save(purchase);
        return PurchaseDto.createOrderDto(ordered);
    }
}
