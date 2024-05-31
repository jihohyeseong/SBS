package com.example.bookstore.api;

import com.example.bookstore.dto.CalculateDto;
import com.example.bookstore.dto.CustomUserDetails;
import com.example.bookstore.dto.PurchaseDto;
import com.example.bookstore.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseApiController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/api/purchase")
    public ResponseEntity<List<PurchaseDto>> orderList(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        List<PurchaseDto> purchaseDtos = purchaseService.orderinfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseDtos);
    }

    @PostMapping("/api/books/{bookId}/purchase")
    public ResponseEntity<PurchaseDto> addOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody PurchaseDto dto, @PathVariable Long bookId){
        Long userId = userDetails.getUserId();
        PurchaseDto item = purchaseService.addOrder(userId, bookId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PostMapping("/api/books/{bookId}/{cartId}/purchase")
    public ResponseEntity<PurchaseDto> addOrderInCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @RequestBody PurchaseDto dto,
            @PathVariable Long bookId ,@PathVariable Long cartId){
        Long userId = userDetails.getUserId();
        PurchaseDto item = purchaseService.addOrderInCart(userId, bookId, cartId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/api/books/{purchaseId}/purchase") // 구매 취소
    public ResponseEntity<PurchaseDto> deleteOrder(@PathVariable Long purchaseId){
        PurchaseDto deleted = purchaseService.deleteOrder(purchaseId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @GetMapping("/api/admin/calculate") // 관리자페이지 정산
    public ResponseEntity<List<CalculateDto>> calculateAdmin(){
        List<CalculateDto> dtos = purchaseService.getCalculate();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/api/admin/total") // 관리자페이지 정산
    public ResponseEntity<Long> calculateTotal(){
        Long total = purchaseService.calculateTotal();
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }
}
