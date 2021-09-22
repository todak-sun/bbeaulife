package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.type.CoupleRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "coupleRequest")
public class CoupleRequestHash {

    @Id
    @Getter
    private Long requesterId;
    @Getter
    private Long requesteeId;
    private String role;

    @TimeToLive
    @Getter
    private final Long timeout = Duration.ofMinutes(30L).getSeconds();

    private CoupleRequestHash(Long requesterId, Long requesteeId, CoupleRole role) {
        this.requesterId = requesterId;
        this.requesteeId = requesteeId;
        this.role = role.name();
    }

    public static CoupleRequestHash create(Long requesterId, Long requesteeId, CoupleRole role) {
        if (requesteeId.equals(requesterId)) {
            throw new RuntimeException("요청자와 수락자는 동일할 수 없음.");
        }
        return new CoupleRequestHash(requesterId, requesteeId, role);
    }


    public CoupleRole getRole() {
        return CoupleRole.valueOf(this.role);
    }

}
