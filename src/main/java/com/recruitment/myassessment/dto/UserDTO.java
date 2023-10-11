package com.recruitment.myassessment.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class UserDTO {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "ex : sesat@gmail.com")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,20}$", message = "Minimal 1 simbol & alfanumeric , ex : Paul@123... Kombinasi Huruf dan Angka !!(Password Minimal 10 Maksimal 20 Karakter)")
    private String password;

    private Boolean isAdmin;

    private Boolean isUser;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 10, max = 60)
    private String nama;

    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }

}
