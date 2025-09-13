package unit.br.unitnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import unit.br.unitnetwork.dto.ResponseDTO;
import unit.br.unitnetwork.exception.DuplicateEmailException;
import unit.br.unitnetwork.exception.EmailNotRegisteredException;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleDuplicateEmailException(DuplicateEmailException ex) {
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailNotRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleEmailNotRegisteredException(EmailNotRegisteredException ex) {
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError, HttpStatus.BAD_REQUEST);

    }
}
