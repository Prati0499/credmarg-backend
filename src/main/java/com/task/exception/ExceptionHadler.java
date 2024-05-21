package com.task.exception;


import com.task.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHadler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        ResponseMessage resp = new ResponseMessage();
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            var problemDetail = ProblemDetail.forStatus(HttpStatus.PARTIAL_CONTENT);
            problemDetail.setTitle("Invalid_Field_Value");
            problemDetail.setDetail("Invalid_field_value message from controller advice");
            problemDetail.setStatus(12);
            errorMessage.append(defaultMessage).append(",");
            log.error(ex.getMessage());
        });
        resp.setErrorCode(12);
        resp.setMessage(errorMessage.toString());
        return new ResponseEntity<>(resp, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerException(NullPointerException e) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Null Pointer Exception !!");
        problemDetail.setDetail("NullPointer Exception message from controller advice ");
        problemDetail.setStatus(13);
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Unhandled Exception !! ");
        problemDetail.setDetail("Unhandled Exception message from controller advice  ");
        problemDetail.setStatus(14);
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

    }
}
