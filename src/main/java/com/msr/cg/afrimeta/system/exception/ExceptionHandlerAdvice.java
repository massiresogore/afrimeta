package com.msr.cg.afrimeta.system.exception;

import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private Throwable x;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        Map<String, String> errorMap = new HashMap<>();
        errors.forEach(error -> {
            String key =((FieldError) error).getField();
            String value =  error.getDefaultMessage();
            errorMap.put(key, value);
        });
        return new Result(false, StatusCode.INVALID_ARGUMENT,"Ivalid argument", errorMap);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleObjectNotFoundException(ObjectNotFoundException exception) {
        return new Result(false, StatusCode.NOT_FOUND, exception.getMessage());
    }

    //Récupérer des éreurs des  valeurs insérées doublement
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {

        String input = exception.getMessage();

        // Définir l'expression régulière pour correspondre à la première valeur entre simples guillemets
        String regex = "'([^']*)'";

        // Compiler l'expression régulière
        Pattern pattern = Pattern.compile(regex);

        // Créer un matcher pour la chaîne de caractères d'entrée
        Matcher matcher = pattern.matcher(input);

        // Trouver la première correspondance et l'imprimer
        if (matcher.find()) {
            // matcher.group(1) contient la valeur capturée par les parenthèses dans l'expression régulière
            return new Result(false, StatusCode.INTERNAL_SERVER_ERROR,"la valeur '"+matcher.group(1)+"' exist déjà ");
        }
            return null;
    }




}
