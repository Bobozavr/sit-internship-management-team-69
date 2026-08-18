package bg.tuvarna.sit.project.ps.internshipmanagement.mapper;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.StudentProfileDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.StudentProfile;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileMapper {
    public StudentProfileDto toDto(StudentProfile studentProfile){
        Long userId = null;
        String studentName = null;
        String studentEmail = null;

        if (studentProfile.getUser() != null){
            userId = studentProfile.getUser().getId();
            studentName = studentProfile.getUser().getFirstName() + " "  + studentProfile.getUser().getLastName();
            studentEmail = studentProfile.getUser().getEmail();
        }

        return StudentProfileDto.builder()
                .id(studentProfile.getId())
                .facultyNumber(studentProfile.getFacultyNumber())
                .specialty(studentProfile.getSpecialty())
                .course(studentProfile.getCourse())
                .skills(studentProfile.getSkills())
                .userId(userId)
                .studentName(studentName)
                .studentEmail(studentEmail)
                .build();
    }
}
