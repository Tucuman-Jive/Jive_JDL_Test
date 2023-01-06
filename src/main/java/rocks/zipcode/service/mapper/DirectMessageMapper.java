package rocks.zipcode.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link DirectMessage} and its DTO {@link DirectMessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface DirectMessageMapper extends EntityMapper<DirectMessageDTO, DirectMessage> {
    @Mapping(target = "userProfiles", source = "userProfiles", qualifiedByName = "userProfileIdSet")
    DirectMessageDTO toDto(DirectMessage s);

    @Mapping(target = "removeUserProfile", ignore = true)
    DirectMessage toEntity(DirectMessageDTO directMessageDTO);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);

    @Named("userProfileIdSet")
    default Set<UserProfileDTO> toDtoUserProfileIdSet(Set<UserProfile> userProfile) {
        return userProfile.stream().map(this::toDtoUserProfileId).collect(Collectors.toSet());
    }
}
