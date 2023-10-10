package com.natwest.Cashwave.CreditcardService;

import com.natwest.Cashwave.CreditcardService.DTO.CreditCardRequest;
import com.natwest.Cashwave.CreditcardService.Entity.CreditCard;
import com.natwest.Cashwave.CreditcardService.Entity.User;
import com.natwest.Cashwave.CreditcardService.Repository.CreditCardRepository;
import com.natwest.Cashwave.CreditcardService.Service.CreditcardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreditcardServiceTest {
    @InjectMocks
    private CreditcardService creditcardService;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testAddCreditCardUserNotFound() {
        // Mock a user response from the external service
        when(restTemplate.getForEntity(eq("http://localhost:8080/users/getUser/user123"), eq(User.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        // Mock credit card data
        CreditCardRequest creditCardRequest = new CreditCardRequest();
        creditCardRequest.setCreditCardNo("1234567890123456");
        creditCardRequest.setBankName("Bank");
        creditCardRequest.setCardHolderName("Card Holder");
        creditCardRequest.setExpiryDate("12/25");

        // Test adding a credit card for a user that does not exist
        ResponseEntity<?> response = creditcardService.addCreditCard("user123", creditCardRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Add any additional assertions if needed
    }
}
