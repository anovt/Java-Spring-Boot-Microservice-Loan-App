package com.loan.microservice.app.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "CustomerDetails",
        description = "Customer details including name, email, mobile number, associated accounts,cards and loans"
)
@Data
public class CustomerDetailsDto {
    @Schema(
            description = "Name of the customer",
            example = "Test User"
    )
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
    private String mobileNumber;
    @Schema(
            description = "Accounts associated with the customer"
    )
    AccountsDto accountsDto;
    @Schema(description = "Cards associated with the customer")
    private CardsDto cardsDto;
    @Schema(description = "Loans associated with the customer")
    private LoansDto loansDto;

}
