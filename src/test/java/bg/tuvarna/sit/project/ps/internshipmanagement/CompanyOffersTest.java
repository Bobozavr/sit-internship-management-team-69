package bg.tuvarna.sit.project.ps.internshipmanagement;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.offer.InternshipOfferRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.mapper.InternshipOfferMapper;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CompanyOffersTest {
    InternshipOfferRepository offers=mock(InternshipOfferRepository.class);
    CompanyRepository companies=mock(CompanyRepository.class);
    CurrentUserService current=mock(CurrentUserService.class);
    User owner=User.builder().id(12L).build();
    Company company=Company.builder().id(3L).user(owner).name("Test").build();
    InternshipOffer offer=InternshipOffer.builder().id(8L).company(company).deadline(LocalDate.now().plusDays(10)).status(OfferStatus.CLOSED).build();
    InternshipOfferService service=new InternshipOfferService(offers,companies,current,new InternshipOfferMapper());
    @BeforeEach void setup(){when(current.get()).thenReturn(owner);when(offers.findById(8L)).thenReturn(Optional.of(offer));when(offers.save(any())).thenAnswer(i->i.getArgument(0));}
    @Test void editingClosedOfferDoesNotReopenIt(){
        var request=InternshipOfferRequest.builder().title("Updated").description("Description").requiredSkills("Java").location("Varna").type(WorkType.REMOTE).deadline(LocalDate.now().plusDays(20)).build();
        assertEquals(OfferStatus.CLOSED,service.update(8L,request).getStatus());
        assertEquals("Updated",offer.getTitle());
    }
    @Test void closeAndReopenPreserveOffer(){
        assertEquals(OfferStatus.ACTIVE,service.reopen(8L).getStatus());
        assertEquals(OfferStatus.CLOSED,service.close(8L).getStatus());
        verify(offers,never()).delete(any());
    }
    @Test void disabledOfferCannotBeReactivatedThroughCloseOrReopen(){
        offer.setStatus(OfferStatus.INACTIVE);
        assertThrows(ForbiddenActionException.class,()->service.close(8L));
        assertThrows(ForbiddenActionException.class,()->service.reopen(8L));
        assertEquals(OfferStatus.INACTIVE,offer.getStatus());
    }
    @Test void expiredOfferCannotBeReopened(){
        offer.setDeadline(LocalDate.now().minusDays(1));
        assertThrows(BadRequestException.class,()->service.reopen(8L));
    }
    @Test void differentCompanyCannotManageOffer(){
        when(current.get()).thenReturn(User.builder().id(99L).build());
        assertThrows(ForbiddenActionException.class,()->service.close(8L));
        assertThrows(ForbiddenActionException.class,()->service.reopen(8L));
        assertThrows(ForbiddenActionException.class,()->service.update(8L,new InternshipOfferRequest()));
        verify(offers,never()).save(any());
    }
}
