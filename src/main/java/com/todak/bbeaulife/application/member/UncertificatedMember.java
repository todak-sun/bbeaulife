package com.todak.bbeaulife.application.member;

import com.todak.bbeaulife.type.FullName;
import com.todak.bbeaulife.type.Sex;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "uncertificatedMember")
public class UncertificatedMember {

    @Id
    private String email;
    private String password;
    private FullName name;
    private Sex sex;

    @TimeToLive
    @Getter
    private Long timeout;

    private UncertificatedMember(String email, String password, FullName name, Sex sex) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.timeout = Duration.ofSeconds(180L).getSeconds();
    }

    public static UncertificatedMember create(String email, String password, FullName name, Sex sex) {
        return new UncertificatedMember(email, password, name, sex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UncertificatedMember that = (UncertificatedMember) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, sex);
    }
}
