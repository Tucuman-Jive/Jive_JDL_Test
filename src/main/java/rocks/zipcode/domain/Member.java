package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "channel_id")
    private Long channelID;

    @ManyToMany(mappedBy = "members")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "members", "message", "userProfiles" }, allowSetters = true)
    private Set<Channel> channels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Member id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Member userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelID() {
        return this.channelID;
    }

    public Member channelID(Long channelID) {
        this.setChannelID(channelID);
        return this;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public Set<Channel> getChannels() {
        return this.channels;
    }

    public void setChannels(Set<Channel> channels) {
        if (this.channels != null) {
            this.channels.forEach(i -> i.removeMember(this));
        }
        if (channels != null) {
            channels.forEach(i -> i.addMember(this));
        }
        this.channels = channels;
    }

    public Member channels(Set<Channel> channels) {
        this.setChannels(channels);
        return this;
    }

    public Member addChannel(Channel channel) {
        this.channels.add(channel);
        channel.getMembers().add(this);
        return this;
    }

    public Member removeChannel(Channel channel) {
        this.channels.remove(channel);
        channel.getMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", channelID=" + getChannelID() +
            "}";
    }
}
