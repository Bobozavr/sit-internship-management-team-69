package bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin;
import jakarta.validation.constraints.*;
public record CreateStudentRequest(
    @NotBlank @Size(max=100) String firstName,
    @NotBlank @Size(max=100) String lastName,
    @NotBlank @Pattern(regexp="[0-9]{4,12}") String facultyNumber,
    @NotBlank @Size(max=200) String specialty,
    @NotNull @Min(1) @Max(4) Integer course) { }
