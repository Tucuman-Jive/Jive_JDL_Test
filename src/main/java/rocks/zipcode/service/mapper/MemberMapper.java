package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Member;
import rocks.zipcode.service.dto.MemberDTO;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {}
