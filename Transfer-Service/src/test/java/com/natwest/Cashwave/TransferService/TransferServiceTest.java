package com.natwest.Cashwave.TransferService;

import static org.assertj.core.api.Assertions.assertThat;
import com.natwest.Cashwave.TransferService.DTO.TransactionRequest;
import com.natwest.Cashwave.TransferService.Entity.Account;
import com.natwest.Cashwave.TransferService.Entity.Transaction;
import com.natwest.Cashwave.TransferService.Repository.TransactionRepository;
import com.natwest.Cashwave.TransferService.Service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransactionService transactionService;

//    @Test
//    public void shouldAddTransactionSuccessfully() {
//        // Arrange
//        String userId = "1234567890";
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setAmount("100");
//        transactionRequest.setDescription("Test transaction");
//        transactionRequest.setReceiverName("John Doe");
//        transactionRequest.setReceiverNo("9876543210");
//        String fromAccount = "1234567890123456";
//
//        Account fromAccountMock = new Account();
//        fromAccountMock.setAccountNo(fromAccount);
//        fromAccountMock.setAccountBalance(1000);
//
//        when(restTemplate.getForEntity(
//                eq("http://localhost:8082/accounts/getAccount/" + fromAccount),
//                eq(Account.class))
//        ).thenReturn(new ResponseEntity<>(fromAccountMock, HttpStatus.OK));
//
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
//
//        // Act
//        ResponseEntity<?> responseEntity = transactionService.transferAmount(userId, transactionRequest, fromAccount);
//
//        // Assert
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }

    @Test
    public void shouldFailToAddTransactionIfUserNotFound() {
        // Arrange
        String userId = "1234567890";
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount("100");
        transactionRequest.setDescription("Test transaction");
        transactionRequest.setReceiverName("John Doe");
        transactionRequest.setReceiverNo("9876543210");
        String fromAccount = "1234567890123456";

        when(restTemplate.getForEntity(
                eq("http://localhost:8082/accounts/getAccount/" + fromAccount),
                eq(Account.class))
        ).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        // Act
        ResponseEntity<?> responseEntity = transactionService.transferAmount(userId, transactionRequest, fromAccount);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}