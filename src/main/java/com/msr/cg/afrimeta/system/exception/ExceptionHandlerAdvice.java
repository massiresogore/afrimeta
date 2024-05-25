package com.msr.cg.afrimeta.system.exception;

import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });

        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided Arguments are invalid, see data for details. ", map);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleObjectNotFoundException(ObjectNotFoundException exception) {
        return new Result(false, StatusCode.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            String key = constraintViolation.getPropertyPath().toString();
            String value = constraintViolation.getMessage();
            errorMap.put(key, value);
        }
        return new Result(false, StatusCode.NOT_FOUND,"", errorMap);
    }

    //Récupérer des éreurs des  valeurs insérées doublement
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        String input = exception.getMessage();
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR,"la valeur '"+ this.findValueInSimpleQuote(input)+"' exist déjà ");
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String input = exception.getMessage();
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR,"la valeur '"+ this.findValueInSimpleQuote(input)+"' exist déjà ");
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleExceptionunkwown(Exception exception) {
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR,"imposible",exception.getMessage());
    }

    private String findValueInSimpleQuote(String input)
    {
        // Définir l'expression régulière pour correspondre à la première valeur entre simples guillemets
        String regex = "'([^']*)'";

        // Compiler l'expression régulière
        Pattern pattern = Pattern.compile(regex);

        // Créer un matcher pour la chaîne de caractères d'entrée
        Matcher matcher = pattern.matcher(input);

        // Trouver la première correspondance et l'imprimer
        if(!matcher.find()){
            return null;
        }

        return matcher.group(1);
    }




}
