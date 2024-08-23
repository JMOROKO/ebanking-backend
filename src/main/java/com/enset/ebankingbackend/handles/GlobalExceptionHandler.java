package com.enset.ebankingbackend.handles;

import com.enset.ebankingbackend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionRepresentation> handleDisabledException() {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Votre compte utilisateur n'est pas activé")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRepresentation> handleBadCredentialsException() {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Le login ou le mot de passe est incorrect ")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);
    }

    @ExceptionHandler(BalanceNotSufficentException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(BalanceNotSufficentException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }
    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(BankAccountNotFoundException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(CustomerNotFoundException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }
    @ExceptionHandler(SameAccountException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(SameAccountException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(AppUserNotFoundException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(PasswordNotMatchException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(UserAlreadyExistException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler(RoleIsBlankException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(RoleIsBlankException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
    @ExceptionHandler(RoleIsNotExistException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(RoleIsNotExistException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }
}
