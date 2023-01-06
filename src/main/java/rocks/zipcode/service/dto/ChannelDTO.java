package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link rocks.zipcode.domain.Channel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Set<MemberDTO> members = new HashSet<>();

    private MessageDTO message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberDTO> members) {
        this.members = members;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelDTO)) {
            return false;
        }

        ChannelDTO channelDTO = (ChannelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, channelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", members=" + getMembers() +
            ", message=" + getMessage() +
            "}";
    }
}
