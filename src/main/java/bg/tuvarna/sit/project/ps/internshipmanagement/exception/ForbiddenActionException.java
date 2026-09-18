package bg.tuvarna.sit.project.ps.internshipmanagement.exception;

public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String message) {
        super(message);
    }
}