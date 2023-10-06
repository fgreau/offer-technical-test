package com.fgreau.offertechtest.mappers;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.models.User;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Mapper Entity -> DTO.
     *
     * @param user entity
     * @return DTO
     */
    UserDto map(final User user);

    /**
     * Mapper DTO -> Entity.
     *
     * @param userDto DTO
     * @return Entity
     */
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "countryOfResidence", source = "countryOfResidence", qualifiedByName = "toLowerCase")
    User map(final UserDto userDto);

    @Named("toLowerCase")
    static String toLowerCase(final String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return value.toLowerCase();
    }
}
