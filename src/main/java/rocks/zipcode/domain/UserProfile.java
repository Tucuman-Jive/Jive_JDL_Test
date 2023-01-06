package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name = "profile_image_content_type")
    private String profileImageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "channels", "userProfile", "directMessage" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_user_profile__channel",
        joinColumns = @JoinColumn(name = "user_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "members", "message", "userProfiles" }, allowSetters = true)
    private Set<Channel> channels = new HashSet<>();

    @ManyToMany(mappedBy = "userProfiles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "messages", "userProfiles" }, allowSetters = true)
    private Set<DirectMessage> directMessages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public UserProfile displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public UserProfile profileImage(byte[] profileImage) {
        this.setProfileImage(profileImage);
        return this;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageContentType() {
        return this.profileImageContentType;
    }

    public UserProfile profileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
        return this;
    }

    public void setProfileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setUserProfile(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setUserProfile(this));
        }
        this.messages = messages;
    }

    public UserProfile messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public UserProfile addMessage(Message message) {
        this.messages.add(message);
        message.setUserProfile(this);
        return this;
    }

    public UserProfile removeMessage(Message message) {
        this.messages.remove(message);
        message.setUserProfile(null);
        return this;
    }

    public Set<Channel> getChannels() {
        return this.channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public UserProfile channels(Set<Channel> channels) {
        this.setChannels(channels);
        return this;
    }

    public UserProfile addChannel(Channel channel) {
        this.channels.add(channel);
        channel.getUserProfiles().add(this);
        return this;
    }

    public UserProfile removeChannel(Channel channel) {
        this.channels.remove(channel);
        channel.getUserProfiles().remove(this);
        return this;
    }

    public Set<DirectMessage> getDirectMessages() {
        return this.directMessages;
    }

    public void setDirectMessages(Set<DirectMessage> directMessages) {
        if (this.directMessages != null) {
            this.directMessages.forEach(i -> i.removeUserProfile(this));
        }
        if (directMessages != null) {
            directMessages.forEach(i -> i.addUserProfile(this));
        }
        this.directMessages = directMessages;
    }

    public UserProfile directMessages(Set<DirectMessage> directMessages) {
        this.setDirectMessages(directMessages);
        return this;
    }

    public UserProfile addDirectMessage(DirectMessage directMessage) {
        this.directMessages.add(directMessage);
        directMessage.getUserProfiles().add(this);
        return this;
    }

    public UserProfile removeDirectMessage(DirectMessage directMessage) {
        this.directMessages.remove(directMessage);
        directMessage.getUserProfiles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", profileImage='" + getProfileImage() + "'" +
            ", profileImageContentType='" + getProfileImageContentType() + "'" +
            "}";
    }
}
