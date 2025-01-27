package com.professional.controller.exceptions;

import com.professional.model.dto.Error;

import com.professional.model.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.ConstraintViolationException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // Manejador Existente: NumberFormatException
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> numberFormatException(NumberFormatException ex) {
        logger.error("NumberFormatException: ", ex);
        return getStringObjectMap(ex.getMessage());
    }

    // Manejador Existente: NoHandlerFoundException
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noRutaException(NoHandlerFoundException ex) {
        logger.warn("NoHandlerFoundException: Ruta no encontrada", ex);
        return getStringObjectMap("Ruta No encontrada");
    }

    // Manejador Existente: NullPointerException
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> notFoundException(NullPointerException ex) {
        logger.error("NullPointerException: ", ex);
        return getStringObjectMap("No existen registros en la base de datos, " + ex.getMessage());
    }

    // Manejador Existente: UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> userNotFoundException(UserNotFoundException ex){
        logger.warn("UserNotFoundException: ", ex);
        return getStringObjectMap(ex.getMessage());
    }

    // Manejador Corregido: JwtException
    @ExceptionHandler({JwttException.class, JwtException.class}) // Asegúrate de que el nombre sea correcto
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> jwtException(JwtException ex){
        logger.error("JwtException: ", ex);
        return getStringObjectMap(ex.getMessage());
    }

    // Manejador Existente: Exception, IOException, ServletException
    @ExceptionHandler({IOException.class, ServletException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> datosFaltantes(Exception ex){
        logger.error("Exception: ", ex);
        return getStringObjectMap("Datos Faltantes o Inexistentes,  " + ex.getMessage());
    }

    // Manejador Existente: HttpClientErrorException
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handleHTTPClientError(HttpClientErrorException ex){
        logger.warn("HttpClientErrorException: ", ex);
        Map<String,Object> alg = new HashMap<>();
        if (ex.getStatusCode() == HttpStatus.FORBIDDEN){
            alg.put("message", "Acceso denegado: " + ex.getMessage());
        } else if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            alg.put("message", "No autorizado: " + ex.getMessage());
        } else {
            alg.put("message", "Error del cliente HTTP: " + ex.getMessage());
        }
        return alg;
    }

    // Nuevo Manejador: MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.warn("MethodArgumentNotValidException: ", ex);
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        return getStringObjectMap("Errores de validación: " + errors.toString());
    }

    /**
     * Maneja todas las demás excepciones no manejadas específicamente.
     *
     * @param ex La excepción lanzada.
     * @return Mapa con el mensaje de error.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleAllExceptions(Exception ex){
        logger.error("Exception: ", ex);
        return getStringObjectMap("Ocurrió un error inesperado: " + ex.getMessage());
    }
    // Nuevo Manejador: ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.warn("ConstraintViolationException: ", ex);
        StringBuilder errors = new StringBuilder();
        ex.getConstraintViolations().forEach(violation ->
                errors.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ")
        );
        return getStringObjectMap("Errores de restricción: " + errors.toString());
    }

    // Nuevo Manejador: AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException: ", ex);
        return getStringObjectMap("Acceso denegado: " + ex.getMessage());
    }

    // Nuevo Manejador: AuthenticationException
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(AuthenticationException ex) {
        logger.error("AuthenticationException: ", ex);
        return getStringObjectMap("Error de autenticación: " + ex.getMessage());
    }

    // Nuevo Manejador: DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("DataIntegrityViolationException: ", ex);
        return getStringObjectMap("Violación de integridad de datos: " + ex.getRootCause().getMessage());
    }

    // Nuevo Manejador: EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.warn("EntityNotFoundException: ", ex);
        return getStringObjectMap("Entidad no encontrada: " + ex.getMessage());
    }

    // Nuevo Manejador: HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("HttpMessageNotReadableException: ", ex);
        return getStringObjectMap("Mensaje HTTP no legible: " + ex.getMessage());
    }

    // Nuevo Manejador: MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.warn("MethodArgumentTypeMismatchException: ", ex);
        String message = String.format("El parámetro '%s' con valor '%s' no es válido.", ex.getName(), ex.getValue());
        return getStringObjectMap("Tipo de argumento inválido: " + message);
    }

    // Nuevo Manejador: MissingServletRequestParameterException
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        logger.warn("MissingServletRequestParameterException: ", ex);
        String message = String.format("Falta el parámetro '%s' de tipo '%s'.", ex.getParameterName(), ex.getParameterType());
        return getStringObjectMap("Parámetro de solicitud faltante: " + message);
    }

    // Nuevo Manejador: Excepciones Personalizadas (Ejemplo)
    // Asegúrate de crear tus propias excepciones personalizadas en el paquete adecuado.
    // Por ejemplo, OperationNotAllowedException y ResourceAlreadyExistsException
    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleOperationNotAllowedException(OperationNotAllowedException ex) {
        logger.error("OperationNotAllowedException: ", ex);
        return getStringObjectMap("Operación no permitida: " + ex.getMessage());
    }


    /**
     * Maneja excepciones de tipo ResourceNotFoundException.
     *
     * @param ex La excepción lanzada.
     * @return Mapa con el mensaje de error.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex){
        logger.warn("ResourceNotFoundException: ", ex);
        return getStringObjectMap(ex.getMessage());
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        logger.error("ResourceAlreadyExistsException: ", ex);
        return getStringObjectMap("Recurso ya existe: " + ex.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIllegalStateException(IllegalStateException ex) {
        logger.warn("IllegalStateException: ", ex);
        return getStringObjectMap(ex.getMessage());
    }


    // Método Existente: getStringObjectMap
    private Map<String, String> getStringObjectMap(String ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex);
        return error;
    }
}


