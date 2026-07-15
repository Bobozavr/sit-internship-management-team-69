package bg.tuvarna.sit.project.ps.internshipmanagement.mapper;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.ApplicationDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationDto toDto(Application application) {
        Long studentProfileId = null;
        String studentName = null;

        Long internshipOfferId = null;
        String internshipTitle = null;
        String companyName = null;

        if (application.getStudentProfile() != null) {
            studentProfileId = application.getStudentProfile().getId();

            if (application.getStudentProfile().getUser() != null) {
                studentName = application.getStudentProfile().getUser().getFirstName()
                        + " "
                        + application.getStudentProfile().getUser().getLastName();
            }
        }

        if (application.getInternshipOffer() != null) {
            internshipOfferId = application.getInternshipOffer().getId();
            internshipTitle = application.getInternshipOffer().getTitle();

            if (application.getInternshipOffer().getCompany() != null) {
                companyName = application.getInternshipOffer().getCompany().getName();
            }
        }

        return ApplicationDto.builder()
                .id(application.getId())
                .applicationDate(application.getApplicationDate())
                .motivationLetter(application.getMotivationLetter())
                .status(application.getStatus())
                .comment(application.getComment())
                .studentProfileId(studentProfileId)
                .studentName(studentName)
                .internshipOfferId(internshipOfferId)
                .internshipTitle(internshipTitle)
                .companyName(companyName)
                .build();
    }
}