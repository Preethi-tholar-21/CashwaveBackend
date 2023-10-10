package com.Capstone.Insurance;
import com.Capstone.Insurance.Controller.PolicyController;
import com.Capstone.Insurance.Entity.Policy;
import com.Capstone.Insurance.Exceptions.PolicyAlreadyExistsException;
import com.Capstone.Insurance.Service.PolicyService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class PolicyControllerTest {
    @InjectMocks
    private PolicyController policyController;

    @Mock
    private PolicyService policyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePolicy() {
        Policy policy = new Policy();
        // Set policy properties as needed

        when(policyService.createPolicy(policy)).thenReturn(policy);

        Policy response = policyController.createPolicy(policy);

        verify(policyService, times(1)).createPolicy(policy);
        assert response.equals(policy);
    }

    @Test
    public void testGetPolicyByPolicyNumber() {
        String policyNumber = "ABC123";
        Policy policy = new Policy();
        // Set policy properties as needed

        when(policyService.getPolicyBypolicynumber(policyNumber)).thenReturn(policy);

        Policy response = policyController.getPolicyBypolicynumber(policyNumber);

        verify(policyService, times(1)).getPolicyBypolicynumber(policyNumber);
        assert response.equals(policy);
    }

    @Test
    public void testGetAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        // Add policies to the list as needed

        when(policyService.getAllPolicies()).thenReturn(policies);

        List<Policy> response = policyController.getAllPolicies();

        verify(policyService, times(1)).getAllPolicies();
        assert response.equals(policies);
    }

    @Test
    public void testLinkAccount() throws PolicyAlreadyExistsException {
        // Create a sample Policy object
        Policy policy = new Policy();
        // Set policy properties as needed

        // Mock the behavior of the policyService
        when(policyService.getPolicyBypolicynumber(anyString())).thenReturn(null); // Assuming no existing policy
        when(policyService.createPolicy(policy)).thenReturn(policy);

        // Call the controller method
        Policy response = policyController.linkAccount(policy);

        // Verify the interactions
        verify(policyService, times(1)).getPolicyBypolicynumber(policy.getpolicynumber());
        verify(policyService, times(1)).createPolicy(policy);

        // Assert that the response is not null
        Assertions.assertNotNull(response); // Use Assertions.assertNotNull here

        // Add any additional assertions you need to check the response object
    }
}