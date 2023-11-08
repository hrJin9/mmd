package com.todos.mmd.login.model;

import com.todos.mmd.login.enums.UseStauts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="USER")
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String email;

    private String pwd;

    private String name;

    private String phone;

    private String addr;

    private String registerDate;

    private String lastLoginDate;

    @Enumerated(EnumType.STRING)
    private UseStauts useStauts;

}
