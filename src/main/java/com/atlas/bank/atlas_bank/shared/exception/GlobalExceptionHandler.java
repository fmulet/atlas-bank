package com.atlas.bank.atlas_bank.shared.exception;

import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.domain.exception.FraudCheckException;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccountNotFoundException.class)
  public ProblemDetail handleAccountNotFound(AccountNotFoundException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(AccountNotActiveException.class)
  public ProblemDetail handleaccountNotActive(AccountNotActiveException exception) {
    return ProblemDetail.forStatusAndDetail(
      HttpStatus.UNPROCESSABLE_CONTENT,
      exception.getMessage()
    );
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ProblemDetail handleInsufficientFunds(InsufficientFundsException exception) {
    return ProblemDetail.forStatusAndDetail(
      HttpStatus.UNPROCESSABLE_CONTENT,
      exception.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception exception) {
    return ProblemDetail.forStatusAndDetail(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "Error interno del servidor."
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Error de validación");

    List<String> errors = new ArrayList<>();
    exception
      .getBindingResult()
      .getFieldErrors()
      .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
    exception
      .getBindingResult()
      .getGlobalErrors()
      .forEach(error -> errors.add(error.getDefaultMessage()));
    problem.setProperty("errors", errors);

    return problem;
  }

  @ExceptionHandler(FraudCheckException.class)
  public ProblemDetail handleFraudCheck(FraudCheckException exception) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
    problem.setTitle("Operación bloqueada por fraude");

    return problem;
  }
}
