package com.themintiest.core.mappers;

import com.themintiest.users.domain.RegisterRequest;
import com.themintiest.users.domain.UpdateProfileRequest;
import com.themintiest.users.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.themintiest.core.domain.UserProfile;
import com.themintiest.core.entity.Profile;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    UserProfile mapFromUserToUserProfile(Profile profile);

    @Mapping(target = "username", source = "email")
    Profile mapFromRegisterRequestToUser(RegisterRequest registerRequest);

    UserDto mapFromUserToUserDto(Profile profile);

    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Profile profile, UpdateProfileRequest updateProfileRequest);
}
