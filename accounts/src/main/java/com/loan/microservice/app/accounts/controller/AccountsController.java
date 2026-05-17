package com.loan.microservice.app.accounts.controller;

import com.loan.microservice.app.accounts.constants.AccountConstants;
import com.loan.microservice.app.accounts.dto.AccountsContactInfoDto;
import com.loan.microservice.app.accounts.dto.CustomerDto;
import com.loan.microservice.app.accounts.dto.ErrorResponseDto;
import com.loan.microservice.app.accounts.dto.ResponseDto;
import com.loan.microservice.app.accounts.service.IAccountsService;
import com.loan.microservice.app.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@Tag(
        name = "CRUD REST APIs for Accounts in Loans Application",
        description = "CRUD REST APIs in Loans App to CREATE, UPDATE, FETCH AND DELETE Account details"
)
@RestController
@RequestMapping(path = "/api/v1")
@Validated
@RequiredArgsConstructor
public class AccountsController {


    private final IAccountsService accountsService;
    private final ICustomerService customerService;




    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(summary = "Create a new customer account",
            description = "Creates a new customer account with the provided details")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createAccounts(@Valid @RequestBody CustomerDto accountDetails) {

        accountsService.createAccount(accountDetails);

        ResponseDto responseDto = new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>getAccountDetails(@RequestParam
                                                            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
                                                            String mobileNumber) {

        CustomerDto customerDto = accountsService.getAccountDetailsByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccounts(@Valid @RequestBody CustomerDto accountDetails) {
        Boolean isUpdated = accountsService.updateAccount(accountDetails);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }


    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>deleteAccounts(@RequestParam String mobileNumber) {
        Boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountConstants.STATUS_200, "Account deleted successfully"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
