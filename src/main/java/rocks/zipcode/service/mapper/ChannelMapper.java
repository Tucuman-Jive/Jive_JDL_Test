package rocks.zipcode.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.Member;
import rocks.zipcode.domain.Message;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.MemberDTO;
import rocks.zipcode.service.dto.MessageDTO;

/**
 * Mapper for the entity {@link Channel} and its DTO {@link ChannelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {
    @Mapping(target = "members", source = "members", qualifiedByName = "memberIdSet")
    @Mapping(target = "message", source = "message", qualifiedByName = "messageId")
    ChannelDTO toDto(Channel s);

    @Mapping(target = "removeMember", ignore = true)
    Channel toEntity(ChannelDTO channelDTO);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);

    @Named("memberIdSet")
    default Set<MemberDTO> toDtoMemberIdSet(Set<Member> member) {
        return member.stream().map(this::toDtoMemberId).collect(Collectors.toSet());
    }

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);
}
