package com.natwest.Cashwave.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.Cashwave.AccountService.Controller.AccountController;
import com.natwest.Cashwave.AccountService.DTO.AccountRequest;
import com.natwest.Cashwave.AccountService.Entity.Account;
import com.natwest.Cashwave.AccountService.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class AccountControllerTests {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAccount() {
        // Create a sample AccountRequest
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNo("12345");
        accountRequest.setAccountBankName("Sample Bank");
        accountRequest.setAccountBalance("1000.0");

        // Mock the behavior of the accountService using doReturn
        doReturn(ResponseEntity.status(HttpStatus.CREATED).body(accountRequest))
                .when(accountService)
                .addAccount(anyString(), any(AccountRequest.class));

        // Call the controller method
        ResponseEntity<?> response = accountController.addAccount("user123", accountRequest);

        // Verify the response
        verify(accountService, times(1)).addAccount(anyString(), any(AccountRequest.class));
        assert response.getStatusCode().equals(HttpStatus.CREATED);
    }

    @Test
    public void testListAccounts() {
        // Create a sample list of accounts
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountNo("12345");
        account.setAccountBankName("Sample Bank");
        account.setAccountBalance(1000.0);
        accounts.add(account);

        // Mock the behavior of the accountService using doReturn
        doReturn(ResponseEntity.ok(accounts))
                .when(accountService)
                .listAccount(anyString());

        // Call the controller method
        ResponseEntity<?> response = accountController.listAccounts("user123");

        // Verify the response
        verify(accountService, times(1)).listAccount(anyString());
        assert response.getStatusCode().equals(HttpStatus.OK);
    }




    @Test
    public void testGetAccount() {
        // Create a sample account number
        String accountNo = "12345";

        // Create a sample account
        Account account = new Account();
        account.setAccountNo(accountNo);
        account.setAccountBankName("Sample Bank");
        account.setAccountBalance(1000.0);

        // Mock the behavior of the accountService using doReturn
        doReturn(ResponseEntity.ok(account))
                .when(accountService)
                .findByaccountNo(anyString());

        // Call the controller method
        ResponseEntity<?> response = accountController.getAccount(accountNo);

        // Verify the response
        verify(accountService, times(1)).findByaccountNo(accountNo);
        assert response.getStatusCode().equals(HttpStatus.OK);
    }


}