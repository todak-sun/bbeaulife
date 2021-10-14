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
    private String requesterRole;

    @TimeToLive
    @Getter
    private Long timeout;

    private CoupleRequestHash(Long requesterId, Long requesteeId, CoupleRole requesterRole) {
        this.requesterId = requesterId;
        this.requesteeId = requesteeId;
        this.requesterRole = requesterRole.name();
        this.timeout = Duration.ofMinutes(30L).getSeconds();
    }

    public static CoupleRequestHash create(Long requesterId, Long requesteeId, CoupleRole role) {
        if (requesteeId.equals(requesterId)) {
            throw new CoupleMissMatchException(requesterId, requesteeId);
        }
        return new CoupleRequestHash(requesterId, requesteeId, role);
    }

    public CoupleRole getRequesterRole() {
        return CoupleRole.valueOf(this.requesterRole);
    }

    public CoupleRole getRequesteeRole() {
        return CoupleRole.valueOf(this.requesterRole).getOpposite();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoupleRequestHash that = (CoupleRequestHash) o;
        return Objects.equals(requesterId, that.requesterId) && Objects.equals(requesteeId, that.requesteeId) && Objects.equals(requesterRole, that.requesterRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requesterId, requesteeId, requesterRole);
    }
}
