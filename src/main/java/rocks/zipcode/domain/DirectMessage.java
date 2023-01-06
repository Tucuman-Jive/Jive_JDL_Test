package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DirectMessage.
 */
@Entity
@Table(name = "direct_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id_1")
    private Long userId1;

    @Column(name = "user_id_2")
    private Long userId2;

    @OneToMany(mappedBy = "directMessage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "channels", "userProfile", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_direct_message__user_profile",
        joinColumns = @JoinColumn(name = "direct_message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_profile_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "messages", "channels", "directMessages" }, allowSetters = true)
    private Set<UserProfile> userProfiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DirectMessage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId1() {
        return this.userId1;
    }

    public DirectMessage userId1(Long userId1) {
        this.setUserId1(userId1);
        return this;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return this.userId2;
    }

    public DirectMessage userId2(Long userId2) {
        this.setUserId2(userId2);
        return this;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setDirectMessage(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setDirectMessage(this));
        }
        this.messages = messages;
    }

    public DirectMessage messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public DirectMessage addMessage(Message message) {
        this.messages.add(message);
        message.setDirectMessage(this);
        return this;
    }

    public DirectMessage removeMessage(Message message) {
        this.messages.remove(message);
        message.setDirectMessage(null);
        return this;
    }

    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public DirectMessage userProfiles(Set<UserProfile> userProfiles) {
        this.setUserProfiles(userProfiles);
        return this;
    }

    public DirectMessage addUserProfile(UserProfile userProfile) {
        this.userProfiles.add(userProfile);
        userProfile.getDirectMessages().add(this);
        return this;
    }

    public DirectMessage removeUserProfile(UserProfile userProfile) {
        this.userProfiles.remove(userProfile);
        userProfile.getDirectMessages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectMessage)) {
            return false;
        }
        return id != null && id.equals(((DirectMessage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectMessage{" +
            "id=" + getId() +
            ", userId1=" + getUserId1() +
            ", userId2=" + getUserId2() +
            "}";
    }
}
