package com.natwest.Cashwave.TransferService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.Cashwave.TransferService.DTO.TransactionRequest;
import com.natwest.Cashwave.TransferService.DTO.TransferRequest;
import com.natwest.Cashwave.TransferService.Entity.Transaction;
import com.natwest.Cashwave.TransferService.Service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito; // Add this import for 'when' method
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

//    @Test
//    public void testAddTransaction() throws Exception {
//        // Define your test data
//        TransactionRequest transactionRequest = new TransactionRequest();
//        // Set transactionRequest properties
//
//        TransferRequest transferRequest = new TransferRequest();
//        transferRequest.setTransactionRequest(transactionRequest);
//        transferRequest.setFromAccountNo("12345");
//
//        when(transactionService.addTransaction(anyString(), any(TransactionRequest.class), anyString()))
//                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new Transaction()));
//
//        mockMvc.perform(post("/transfer/{user_id}/addTrans", "user123")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(transferRequest)))
//
//                .andExpect(status().isCreated())
//                .andExpect(content().string(is("Transaction created successfully")));
//    }

    @Test
    public void testListTransactions() throws Exception {
        // Define your test data

        when(transactionService.listTransactions(anyString()))
                .thenReturn(Arrays.asList(new Transaction(), new Transaction()));

        mockMvc.perform(get("/transfer/{user_id}/listTrans", "user123"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    public void testListTransactionsWithDifferentData() throws Exception {
        // Define your test data with different transactions
        Transaction transaction1 = new Transaction("1", "Receiver1", "1234567890", 100.0, "Description1", "Account1", "2023-10-10", null);
        Transaction transaction2 = new Transaction("2", "Receiver2", "9876543210", 50.0, "Description2", "Account2", "2023-10-11", null);
        Transaction transaction3 = new Transaction("3", "Receiver3", "5555555555", 75.0, "Description3", "Account3", "2023-10-12", null);

        when(transactionService.listTransactions(anyString()))
                .thenReturn(Arrays.asList(transaction1, transaction2, transaction3));

        mockMvc.perform(get("/transfer/{user_id}/listTrans", "user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].receiverName", is("Receiver1")))
                .andExpect(jsonPath("$[0].receiverNo", is("1234567890")))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].description", is("Description1")))
                .andExpect(jsonPath("$[0].fromAccount", is("Account1")))
                .andExpect(jsonPath("$[0].transactionDate", is("2023-10-10")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].receiverName", is("Receiver2")))
                .andExpect(jsonPath("$[1].receiverNo", is("9876543210")))
                .andExpect(jsonPath("$[1].amount", is(50.0)))
                .andExpect(jsonPath("$[1].description", is("Description2")))
                .andExpect(jsonPath("$[1].fromAccount", is("Account2")))
                .andExpect(jsonPath("$[1].transactionDate", is("2023-10-11")))
                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].receiverName", is("Receiver3")))
                .andExpect(jsonPath("$[2].receiverNo", is("5555555555")))
                .andExpect(jsonPath("$[2].amount", is(75.0)))
                .andExpect(jsonPath("$[2].description", is("Description3")))
                .andExpect(jsonPath("$[2].fromAccount", is("Account3")))
                .andExpect(jsonPath("$[2].transactionDate", is("2023-10-12")));
    }



    //    @Test
//    public void testMakeTransfer() throws Exception {
//        // Define your test data
//        TransactionRequest transactionRequest = new TransactionRequest();
//        // Set transactionRequest properties
//
//        TransferRequest transferRequest = new TransferRequest();
//        transferRequest.setTransactionRequest(transactionRequest);
//        transferRequest.setFromAccountNo("12345");
//
//        when(transactionService.transferAmount(anyString(), any(TransactionRequest.class), anyString()))
//                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Transaction completed successfully"));
//
//        mockMvc.perform(put("/transfer/{user_id}/makeTransfer", "user123")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(transferRequest)))
//
//                .andExpect(status().isOk())
//                .andExpect(content().string(is("Transaction completed successfully")));
//    }
    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}