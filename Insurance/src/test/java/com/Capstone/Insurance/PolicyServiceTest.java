package com.Capstone.Insurance;
import com.Capstone.Insurance.Entity.Policy;
import com.Capstone.Insurance.Exceptions.PolicyAlreadyExistsException;
import com.Capstone.Insurance.Repository.PolicyRepository;
import com.Capstone.Insurance.Service.PolicyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {
    @InjectMocks
    private PolicyService policyService;

    @Mock
    private PolicyRepository policyRepository;

    @Test
    public void testCreatePolicy() {
        Policy policy = new Policy();
        // Set policy properties as needed

        when(policyRepository.save(policy)).thenReturn(policy);

        Policy createdPolicy = policyService.createPolicy(policy);

        verify(policyRepository, times(1)).save(policy);
        assertNotNull(createdPolicy); // Ensure the created policy is not null

        // Add any additional assertions to check the createdPolicy object
    }

    @Test
    public void testGetPolicyByPolicyNumber() {
        String policyNumber = "ABC123";
        Policy policy = new Policy();
        // Set policy properties as needed

        when(policyRepository.findBypolicynumber(policyNumber)).thenReturn(policy);

        Policy retrievedPolicy = policyService.getPolicyBypolicynumber(policyNumber);

        verify(policyRepository, times(1)).findBypolicynumber(policyNumber);
        assertNotNull(retrievedPolicy); // Ensure the retrieved policy is not null

        // Add any additional assertions to check the retrievedPolicy object
    }

    @Test
    public void testLinkAccount() {
        Policy policy = new Policy();
        // Set policy properties as needed

        when(policyRepository.findBypolicynumber(policy.getpolicynumber())).thenReturn(null); // Assuming no existing policy
        when(policyRepository.save(policy)).thenReturn(policy);

        try {
            Policy linkedPolicy = policyService.linkAccount(policy);
            verify(policyRepository, times(1)).findBypolicynumber(policy.getpolicynumber());
            verify(policyRepository, times(1)).save(policy);
            assertNotNull(linkedPolicy); // Ensure the linked policy is not null

            // Add any additional assertions to check the linkedPolicy object
        } catch (PolicyAlreadyExistsException e) {
            // Handle the exception if it occurs in your production code
            fail("PolicyAlreadyExistsException should not have been thrown");
        }
    }
}
