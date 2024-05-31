package com.example.bookstore.dto;

import com.example.bookstore.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JoinDto {
    @NotEmpty(message = "id는 최소 3자 이상 최대 20자 이하여야 합니다.")
    @Size(min = 3 , max = 20, message = "id는 최소 3자 이상 최대 20자 이하여야 합니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 최소 5자 이상이어야 하며, 특수문자 하나 이상을 포함해야 합니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()-+=])(?=\\S+$).{5,}$", message = "비밀번호는 최소 5자 이상이어야 하며, 특수문자 하나 이상을 포함해야 합니다.")
    private String password;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotEmpty(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "휴대폰 번호는 010-xxxx-xxxx 형식이어야 합니다.")
    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$", message = "휴대폰 번호는 010-xxxx-xxxx 형식이어야 합니다.")
    private String phoneNum;

    @NotEmpty(message = "배송을 위해 주소를 정확히 입력해주세요.")
    private String address;
}
