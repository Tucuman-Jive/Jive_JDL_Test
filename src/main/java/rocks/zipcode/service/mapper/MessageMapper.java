package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.DirectMessage;
import rocks.zipcode.domain.Message;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.DirectMessageDTO;
import rocks.zipcode.service.dto.MessageDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    @Mapping(target = "directMessage", source = "directMessage", qualifiedByName = "directMessageId")
    MessageDTO toDto(Message s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);

    @Named("directMessageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DirectMessageDTO toDtoDirectMessageId(DirectMessage directMessage);
}
