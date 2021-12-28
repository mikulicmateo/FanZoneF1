package hr.riteh.fanzonef1.exception;

import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<?> handleMissingHeaderError(Exception exception, WebRequest request){
        //400
        return new ResponseEntity<>(new ResponseMessageDto(false, "Missing authorization details."), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<?> handleAccessDeniedException(Exception exception, WebRequest request){
        //401
        return new ResponseEntity<>(new ResponseMessageDto(false, "Access has been denied."), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleDefaultException(Exception exception, WebRequest request){
        //500
        return  new ResponseEntity<>(new ResponseMessageDto(false, "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
