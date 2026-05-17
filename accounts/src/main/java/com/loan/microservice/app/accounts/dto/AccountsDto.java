package com.loan.microservice.app.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "Accounts",
        description = "Account details including account number, type, branch address and associated customer ID"
)
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Account number should be 10 digits")
    private Long accountNumber;
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
    private Long customerId;

}
