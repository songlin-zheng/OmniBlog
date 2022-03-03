package com.songlinzheng.authentication.entity;

import com.songlinzheng.authentication.util.SnowflakeGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GenericGenerator(name = "snowflake" , strategy = "com.songlinzheng.authentication.util.SnowflakeGenerator")
    @GeneratedValue(generator = "snowflake")
    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    protected User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
