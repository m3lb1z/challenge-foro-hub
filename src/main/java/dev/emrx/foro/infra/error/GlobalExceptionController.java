package dev.emrx.foro.infra.error;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerResourceNotFound404(EntityNotFoundException ex) {
        String mensaje = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(mensaje, false, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handlerBadRequest400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        String message = errores.toString();
        ExceptionResponse response = new ExceptionResponse(message, false, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity<ExceptionResponse> handlerValidacionDeIntegridad(Exception ex) {
        String mensaje = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(mensaje, false, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handlerValidacionDeNegocio(Exception ex) {
        String mensaje = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(mensaje, false, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private record DatosErrorValidacion(String campo, String error) {

        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }

        @Override
        public String toString() {
            return "{" +
                    "campo='" + campo + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }

}
