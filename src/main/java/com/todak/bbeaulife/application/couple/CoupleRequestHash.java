package com.todak.bbeaulife.application.couple;

import com.todak.bbeaulife.application.couple.exception.CoupleMissMatchException;
import com.todak.bbeaulife.type.CoupleRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.Objects;

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
            throw new CoupleMissMatchException(requesterId, requesteeId);
        }
        return new CoupleRequestHash(requesterId, requesteeId, role);
    }

    public CoupleRole getRole() {
        return CoupleRole.valueOf(this.role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoupleRequestHash that = (CoupleRequestHash) o;
        return Objects.equals(requesterId, that.requesterId) && Objects.equals(requesteeId, that.requesteeId) && Objects.equals(role, that.role) && Objects.equals(timeout, that.timeout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requesterId, requesteeId, role, timeout);
    }
}
