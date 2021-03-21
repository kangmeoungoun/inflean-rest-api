package me.goldapple.infleanrestapi.accounts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("UserDetailsService 사용하여 username 으로 사용자 찾기")
    void findByUsername() throws Exception{
        //given
        String password = "goldapple";
        String username = "kes98202@naver.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN,AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);
        //when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //then
        Assertions.assertThat(passwordEncoder.matches(password,userDetails.getPassword())).isTrue();
    }

    @Test
    @DisplayName("없는 username 으로 사용자 찾기 실패")
    void findByUsernameFail() throws Exception{
        //given
        String username = "random@gmail.com";
        //when & then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class , () -> accountService.loadUserByUsername(username));
        Assertions.assertThat(exception.getMessage()).contains(username);
    }

}