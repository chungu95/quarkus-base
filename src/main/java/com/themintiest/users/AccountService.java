package com.themintiest.users;

import com.themintiest.core.domain.Account;
import com.themintiest.core.domain.UserStatus;
import com.themintiest.core.mappers.AccountMapper;
import com.themintiest.core.repository.AccountRepository;
import com.themintiest.exception.UserException;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getAccountByEmailAndStatus(String email, UserStatus status) {
        com.themintiest.core.entity.Account account = accountRepository.findByEmailAndStatus(email, status)
                .orElseThrow(UserException::userNotFound);
        return AccountMapper.INSTANCE.map(account);
    }
}
