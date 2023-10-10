package com.example.Natwest.cashwavebackend;
import com.example.Natwest.cashwavebackend.Model.Loan;
import com.example.Natwest.cashwavebackend.Repo.Repo;
import com.example.Natwest.cashwavebackend.Service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class LoanServiceTest {
    @Mock
    private Repo loanRepo;

    private LoanService loanService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService(loanRepo);
    }

    @Test
    public void testSaveLoan() {
        Loan loan = new Loan("1", "12345", "Lender1", 1000.0);

        when(loanRepo.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.saveLoan(loan);

        assertNotNull(savedLoan);
        assertEquals("12345", savedLoan.getLoanNumber());
        assertEquals("Lender1", savedLoan.getLender());

        verify(loanRepo, times(1)).save(any(Loan.class));
        verifyNoMoreInteractions(loanRepo);
    }

    @Test
    public void testGetAllLoans() {
        List<Loan> loans = Arrays.asList(
                new Loan("1", "12345", "Lender1", 1000.0),
                new Loan("2", "67890", "Lender2", 2000.0)
        );

        when(loanRepo.findAll()).thenReturn(loans);

        List<Loan> allLoans = loanService.getAllLoans();

        assertNotNull(allLoans);
        assertEquals(2, allLoans.size());

        verify(loanRepo, times(1)).findAll();
        verifyNoMoreInteractions(loanRepo);
    }

    @Test
    public void testFindLoan() {
        String lender = "Lender1";
        String loanNumber = "12345";
        Loan loan = new Loan("1", loanNumber, lender, 1000.0);

        when(loanRepo.findAll()).thenReturn(Arrays.asList(loan));

        Loan foundLoan = loanService.findLoan(lender, loanNumber);

        assertNotNull(foundLoan);
        assertEquals(loanNumber, foundLoan.getLoanNumber());
        assertEquals(lender, foundLoan.getLender());

        verify(loanRepo, times(1)).findAll();
        verifyNoMoreInteractions(loanRepo);
    }

    @Test
    public void testFindLoanNotFound() {
        String lender = "Lender1";
        String loanNumber = "12345";

        when(loanRepo.findAll()).thenReturn(Arrays.asList(new Loan("1", "67890", "Lender2", 2000.0)));

        Loan foundLoan = loanService.findLoan(lender, loanNumber);

        assertNull(foundLoan);

        verify(loanRepo, times(1)).findAll();
        verifyNoMoreInteractions(loanRepo);
    }

}
