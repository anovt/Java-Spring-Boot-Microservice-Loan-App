package com.loan.microservice.app.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("AuditAwareImpl")

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // In a real application, you would retrieve the current user from the security context
        // For simplicity, we return a fixed value here
        return Optional.of("ACCOUNTS_MS");
    }

}
