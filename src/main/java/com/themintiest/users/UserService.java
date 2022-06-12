package com.themintiest.users;

import com.themintiest.users.domain.UpdateProfileRequest;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.RequiredArgsConstructor;
import com.themintiest.core.constants.Roles;
import com.themintiest.core.entity.Account;
import com.themintiest.core.entity.AccountRole;
import com.themintiest.core.entity.Role;
import com.themintiest.core.entity.Profile;
import com.themintiest.core.mappers.AccountMapper;
import com.themintiest.core.mappers.ProfileMapper;
import com.themintiest.core.query.Pageable;
import com.themintiest.core.query.PagingQuery;
import com.themintiest.core.repository.AccountRepository;
import com.themintiest.core.repository.AccountRoleRepository;
import com.themintiest.core.repository.RoleRepository;
import com.themintiest.core.repository.UserRepository;
import com.themintiest.exception.RoleException;
import com.themintiest.exception.UserException;
import com.themintiest.security.SecurityIdentityHolder;
import com.themintiest.users.domain.GrantRoleRQ;
import com.themintiest.users.domain.RegisterRequest;
import com.themintiest.users.domain.UserDto;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;

    public Pageable<UserDto> search(PagingQuery pagingQuery) {
        return userRepository.search(pagingQuery, ProfileMapper.INSTANCE::mapFromUserToUserDto);
    }

    public Profile getById(Long id) {
        return userRepository.findByIdOptional(id).orElseThrow(UserException::userNotFound);
    }

    public Profile getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserException::userNotFound);
    }

    @Transactional
    public UserDto createUser(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);
        Role role = roleRepository.findByName(Roles.USER).orElseThrow(RoleException::roleNotFound);
        createAccount(registerRequest, role);
        Profile profile = ProfileMapper.INSTANCE.mapFromRegisterRequestToUser(registerRequest);
        return ProfileMapper.INSTANCE.mapFromUserToUserDto(userRepository.save(profile));
    }

    public UserDto getCurrentLoginUser() {
        SecurityIdentity identity = SecurityIdentityHolder.getIdentity();
        Profile profile = userRepository.findByEmail(identity.getPrincipal().getName())
                .orElseThrow(UserException::userNotFound);
        return ProfileMapper.INSTANCE.mapFromUserToUserDto(profile);
    }

    @Transactional
    public UserDto updateMyProfile(UpdateProfileRequest updateProfileRequest) {
        SecurityIdentity identity = SecurityIdentityHolder.getIdentity();
        Profile profile = userRepository.findByEmail(identity.getPrincipal().getName())
                .orElseThrow(UserException::userNotFound);
        ProfileMapper.INSTANCE.merge(profile, updateProfileRequest);
        return ProfileMapper.INSTANCE.mapFromUserToUserDto(userRepository.save(profile));
    }

    @Transactional
    public UserDto grantRoleForUser(Long id, GrantRoleRQ grantRoleRQ) {
        Profile profile = userRepository.findByIdOptional(id).orElseThrow(UserException::userNotFound);
        Account account = accountRepository.findByEmail(profile.getEmail()).orElseThrow(UserException::userNotFound);
        Set<Role> roles = roleRepository.findByNameIn(grantRoleRQ.getRoles());
        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoleRepository.deleteByAccountId(account.getId());
        for (Role role : roles) {
            AccountRole accountRole = new AccountRole();
            accountRole.setRole(role);
            accountRole.setAccount(account);
            accountRoles.add(accountRole);
        }
        accountRoleRepository.persist(accountRoles);
        return ProfileMapper.INSTANCE.mapFromUserToUserDto(profile);
    }

    public UserDto getUserRSById(Long id) {
        Profile profile = userRepository.findByIdOptional(id).orElseThrow(UserException::userNotFound);
        return ProfileMapper.INSTANCE.mapFromUserToUserDto(profile);
    }

    private void createAccount(RegisterRequest registerRequest, Role role) {
        Account account = AccountMapper.INSTANCE.mapFromRegisterRequestToAccount(registerRequest);
        account.setPassword(BcryptUtil.bcryptHash(registerRequest.getPassword()));
        accountRepository.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        accountRoleRepository.save(accountRole);
        accountRoleRepository.findAll();
    }

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw UserException.emailHasBeenInUsed();
        }
    }
}
