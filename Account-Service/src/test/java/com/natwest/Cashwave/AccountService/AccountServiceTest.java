package com.natwest.Cashwave.AccountService;

import com.natwest.Cashwave.AccountService.DTO.AccountRequest;
import com.natwest.Cashwave.AccountService.Entity.Account;
import com.natwest.Cashwave.AccountService.Repository.AccountRepository;
import com.natwest.Cashwave.AccountService.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Autowired // Inject the mongoTemplate bean
    private MongoTemplate mongoTemplate;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RestTemplate restTemplate; // Inject the mocked RestTemplate

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

        // Mock the behavior of the accountRepository
        when(accountRepository.existsByAccountNo("12345")).thenReturn(false);

        // Mock the behavior of the accountRepository save method
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        // Mock the behavior of the restTemplate
        when(restTemplate.getForEntity(anyString(), any(Class.class), any(Object[].class)))
                .thenReturn(ResponseEntity.ok().build());

        // Call the service method
        ResponseEntity<?> response = accountService.addAccount("user123", accountRequest);

        // Verify the response and repository interactions
        verify(accountRepository, times(1)).existsByAccountNo("12345");
        verify(accountRepository, times(1)).save(any(Account.class));
        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    public void testListAccount() {
        // Create a sample list of accounts
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountNo("12345");
        account.setAccountBankName("Sample Bank");
        account.setAccountBalance(1000.0);
        accounts.add(account);

        // Mock the behavior of the accountRepository
        when(accountRepository.findByUser_Id(anyString())).thenReturn(accounts);

        // Call the service method
        ResponseEntity<?> response = accountService.listAccount("user123");

        // Verify the response
        verify(accountRepository, times(1)).findByUser_Id(anyString());
        assert response.getStatusCode().is2xxSuccessful();
    }

//    @Test
//    public void testUpdateBalance() {
//        // Mock the behavior of the mongoTemplate update method
//        when(mongoTemplate.updateFirst(any(Query.class), any(Update.class), eq(Account.class)))
//                .thenReturn(null); // You can return an UpdateResult if needed
//
//        // Call the service method
//        ResponseEntity<String> response = accountService.updateAccountBalance("12345", 2000.0);
//
//        // Verify the response
//        verify(mongoTemplate, times(1)).updateFirst(any(Query.class), any(Update.class), eq(Account.class));
//        assert response.getStatusCode().equals(HttpStatus.OK);
//    }

    @Test
    public void testGetAccount() {
        // Create a sample account
        Account account = new Account();
        account.setAccountNo("12345");
        account.setAccountBankName("Sample Bank");
        account.setAccountBalance(1000.0);

        // Mock the behavior of the accountRepository
        when(accountRepository.findByAccountNo(anyString())).thenReturn(account);

        // Call the service method
        ResponseEntity<?> response = accountService.findByaccountNo("12345");

        // Verify the response
        verify(accountRepository, times(1)).findByAccountNo(anyString());
        assert response.getStatusCode().is2xxSuccessful();
    }
}