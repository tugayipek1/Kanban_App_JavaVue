package com.vbt.kanban.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.vbt.kanban.dto.response.ErrorResponse;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
    		AccountNotFoundException.class,
			InviteNotFoundException.class,
            UserNotFoundException.class,
			ResetPasswordRequestNotFoundException.class,
            ProjectNotFoundException.class
    })
    public ResponseEntity<?> NotFoundExceptions(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserIsAlreadyMemberOfThisProjectException.class)
    public ResponseEntity<?> UserIsAlreadyMemberOfThisProjectException(UserIsAlreadyMemberOfThisProjectException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), new Date(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> NotFoundExceptionHandler(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResetPasswordTokenIsNotValidException.class)
    public ResponseEntity<?> NotValidExceptions(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
