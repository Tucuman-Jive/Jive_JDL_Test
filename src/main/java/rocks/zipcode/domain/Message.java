package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "channel_id")
    private Long channelID;

    @Column(name = "text")
    private String text;

    @OneToMany(mappedBy = "message")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "members", "message", "userProfiles" }, allowSetters = true)
    private Set<Channel> channels = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "messages", "channels", "directMessages" }, allowSetters = true)
    private UserProfile userProfile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "userProfiles" }, allowSetters = true)
    private DirectMessage directMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Message userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChannelID() {
        return this.channelID;
    }

    public Message channelID(Long channelID) {
        this.setChannelID(channelID);
        return this;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public String getText() {
        return this.text;
    }

    public Message text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Channel> getChannels() {
        return this.channels;
    }

    public void setChannels(Set<Channel> channels) {
        if (this.channels != null) {
            this.channels.forEach(i -> i.setMessage(null));
        }
        if (channels != null) {
            channels.forEach(i -> i.setMessage(this));
        }
        this.channels = channels;
    }

    public Message channels(Set<Channel> channels) {
        this.setChannels(channels);
        return this;
    }

    public Message addChannel(Channel channel) {
        this.channels.add(channel);
        channel.setMessage(this);
        return this;
    }

    public Message removeChannel(Channel channel) {
        this.channels.remove(channel);
        channel.setMessage(null);
        return this;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Message userProfile(UserProfile userProfile) {
        this.setUserProfile(userProfile);
        return this;
    }

    public DirectMessage getDirectMessage() {
        return this.directMessage;
    }

    public void setDirectMessage(DirectMessage directMessage) {
        this.directMessage = directMessage;
    }

    public Message directMessage(DirectMessage directMessage) {
        this.setDirectMessage(directMessage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", channelID=" + getChannelID() +
            ", text='" + getText() + "'" +
            "}";
    }
}
