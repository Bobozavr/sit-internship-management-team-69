package bg.tuvarna.sit.project.ps.internshipmanagement.dto;


import lombok.Data;

@Data
public class CompanyRegistrationSubmitRequest {
    private String companyName;
    private String description;
    private String email; // Контактный email компании
}