package com.example.bookmarket.dto;

public class KakaoPayReadyResponse {
    private String tid; // 결제 고유 번호
    private String next_redirect_app_url; // 모바일 앱일 경우 받는 결제 페이지
    private String next_redirect_mobile_url; // 모바일 웹일 경우 받는 결제 페이지
    private String next_redirect_pc_url; // PC 웹일 경우 받는 결제 페이지
    private String created_at; // 결제 요청 시간

    // Getter 메서드
    public String getTid() {
        return tid;
    }

    public String getNext_redirect_app_url() {
        return next_redirect_app_url;
    }

    public String getNext_redirect_mobile_url() {
        return next_redirect_mobile_url;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    // Setter 메서드
    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setNext_redirect_app_url(String next_redirect_app_url) {
        this.next_redirect_app_url = next_redirect_app_url;
    }

    public void setNext_redirect_mobile_url(String next_redirect_mobile_url) {
        this.next_redirect_mobile_url = next_redirect_mobile_url;
    }

    public void setNext_redirect_pc_url(String next_redirect_pc_url) {
        this.next_redirect_pc_url = next_redirect_pc_url;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
