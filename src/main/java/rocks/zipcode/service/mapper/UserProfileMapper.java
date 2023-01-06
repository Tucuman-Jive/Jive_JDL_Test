package rocks.zipcode.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.User;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.UserDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link UserProfile} and its DTO {@link UserProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "channels", source = "channels", qualifiedByName = "channelIdSet")
    UserProfileDTO toDto(UserProfile s);

    @Mapping(target = "removeChannel", ignore = true)
    UserProfile toEntity(UserProfileDTO userProfileDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("channelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChannelDTO toDtoChannelId(Channel channel);

    @Named("channelIdSet")
    default Set<ChannelDTO> toDtoChannelIdSet(Set<Channel> channel) {
        return channel.stream().map(this::toDtoChannelId).collect(Collectors.toSet());
    }
}
