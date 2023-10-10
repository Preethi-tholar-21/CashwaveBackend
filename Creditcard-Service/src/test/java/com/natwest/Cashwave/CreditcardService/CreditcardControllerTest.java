package com.natwest.Cashwave.CreditcardService;
import com.natwest.Cashwave.CreditcardService.Controller.CreditcardController;
import com.natwest.Cashwave.CreditcardService.DTO.CreditCardRequest;
import com.natwest.Cashwave.CreditcardService.Entity.CreditCard;
import com.natwest.Cashwave.CreditcardService.Service.CreditcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditcardControllerTest {
    @InjectMocks
    private CreditcardController creditcardController;

    @Mock
    private CreditcardService creditcardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCreditCard() {
        // Create a mock CreditCardRequest
        CreditCardRequest creditCardRequest = new CreditCardRequest();
        creditCardRequest.setCreditCardNo("1234567890123456");
        creditCardRequest.setBankName("Bank");
        creditCardRequest.setCardHolderName("Card Holder");
        creditCardRequest.setExpiryDate("12/25");

        // Create a mock CreditCard response
        CreditCard mockCreditCard = new CreditCard();
        mockCreditCard.setCreditCardNo(creditCardRequest.getCreditCardNo());
        mockCreditCard.setBankName(creditCardRequest.getBankName());
        mockCreditCard.setCardHolderName(creditCardRequest.getCardHolderName());
        mockCreditCard.setExpiryDate(creditCardRequest.getExpiryDate());

        // Mock the behavior of the creditcardService.addCreditCard method
        when(creditcardService.addCreditCard(eq("user123"), any(CreditCardRequest.class)))
                .thenAnswer(invocation -> {
                    CreditCardRequest request = invocation.getArgument(1);
                    return ResponseEntity.ok(mockCreditCard);
                });

        // Perform the test
        ResponseEntity<?> responseEntity = creditcardController.addCreditCard("user123", creditCardRequest);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCreditCard, responseEntity.getBody());
    }

//    @Test
//    public void testListCreditCard() {
//        // Create a mock CreditCard list
//        List<CreditCard> mockCreditCardList = new ArrayList<>();
//
//        // Add mock credit cards to the list
//        CreditCard mockCreditCard1 = new CreditCard();
//        mockCreditCard1.setCreditCardNo("1234567890123456");
//        mockCreditCard1.setBankName("Bank 1");
//        mockCreditCard1.setCardHolderName("Card Holder 1");
//        mockCreditCard1.setExpiryDate("12/25");
//
//        CreditCard mockCreditCard2 = new CreditCard();
//        mockCreditCard2.setCreditCardNo("9876543210987654");
//        mockCreditCard2.setBankName("Bank 2");
//        mockCreditCard2.setCardHolderName("Card Holder 2");
//        mockCreditCard2.setExpiryDate("06/24");
//
//        mockCreditCardList.add(mockCreditCard1);
//        mockCreditCardList.add(mockCreditCard2);
//
//        // Mock the behavior of the creditcardService.listCreditCards method
//        when(creditcardService.listCreditCards(eq("user123")))
//                .thenReturn(ResponseEntity.ok().body(mockCreditCardList)); // Use ResponseEntity.ok().body() here
//
//        // Perform the test
//        ResponseEntity<?> responseEntity = creditcardController.listCreditCard("user123");
//
//        // Assert the response
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(mockCreditCardList, responseEntity.getBody());
//    }
}