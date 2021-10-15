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
import java.util.Random;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "uncertificatedMember")
public class UncertificatedMember {

    @Id
    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private FullName name;

    @Getter
    private String cirtificateCode;
    private Sex sex;

    @TimeToLive
    @Getter
    private Long timeout;

    private UncertificatedMember(String email, String password, FullName name, Sex sex) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.cirtificateCode = new Random().ints(8)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        this.timeout = Duration.ofSeconds(180L).getSeconds();
    }

    public static UncertificatedMember create(String email, String password, FullName name, Sex sex) {
        return new UncertificatedMember(email, password, name, sex);
    }

    public boolean isValidCode(String code) {
        return this.cirtificateCode.equals(code);
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
