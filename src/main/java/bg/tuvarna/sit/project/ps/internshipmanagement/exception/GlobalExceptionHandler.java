package bg.tuvarna.sit.project.ps.internshipmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public record ErrorResponse(LocalDateTime timestamp,int status,String error,String message,String path) {}
    private ResponseEntity<ErrorResponse> error(HttpStatus s,String m,HttpServletRequest r){return ResponseEntity.status(s).body(new ErrorResponse(LocalDateTime.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI()));}
    @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException e,HttpServletRequest r){return error(HttpStatus.NOT_FOUND,e.getMessage(),r);}
    @ExceptionHandler({BadRequestException.class,DuplicateResourceException.class}) ResponseEntity<ErrorResponse> bad(RuntimeException e,HttpServletRequest r){return error(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
    @ExceptionHandler(ForbiddenActionException.class) ResponseEntity<ErrorResponse> forbidden(ForbiddenActionException e,HttpServletRequest r){return error(HttpStatus.FORBIDDEN,e.getMessage(),r);}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e,HttpServletRequest r){
        String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining("; ")); return error(HttpStatus.BAD_REQUEST,msg,r);
    }
    @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponse> generic(Exception e,HttpServletRequest r){return error(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()==null?"Unexpected error":e.getMessage(),r);}
}
