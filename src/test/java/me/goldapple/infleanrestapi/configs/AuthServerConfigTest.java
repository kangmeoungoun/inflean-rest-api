package me.goldapple.infleanrestapi.configs;

import me.goldapple.infleanrestapi.accounts.Account;
import me.goldapple.infleanrestapi.accounts.AccountRole;
import me.goldapple.infleanrestapi.accounts.AccountService;
import me.goldapple.infleanrestapi.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControllerTest{

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    void getAuthToken() throws Exception{
        //given
        String username = "kes98201@naver.com";
        String password = "123456";
        Account goldapple = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN,AccountRole.USER ))
                .build();
        this.accountService.saveAccount(goldapple);

        String clientId = "myApp";
        String clientSecret = "pass";
        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId,clientSecret))
                    .param("username",username)
                    .param("password",password)
                    .param("grant_type","password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())

                ;
        //when

        //then
    }
}