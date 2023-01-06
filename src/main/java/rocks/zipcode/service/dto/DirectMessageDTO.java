package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link rocks.zipcode.domain.DirectMessage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectMessageDTO implements Serializable {

    private Long id;

    private Long userId1;

    private Long userId2;

    private Set<UserProfileDTO> userProfiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Set<UserProfileDTO> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfileDTO> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectMessageDTO)) {
            return false;
        }

        DirectMessageDTO directMessageDTO = (DirectMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, directMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectMessageDTO{" +
            "id=" + getId() +
            ", userId1=" + getUserId1() +
            ", userId2=" + getUserId2() +
            ", userProfiles=" + getUserProfiles() +
            "}";
    }
}
