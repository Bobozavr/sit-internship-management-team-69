import ApplicationCard from "../components/ApplicationCard";
import { fakeApplications } from "../data/fakeApplications";

function StudentApplicationsPage() {
  return (
    <div>
      <h1>My Applications</h1>
      <p>Here students can see their submitted internship applications.</p>

      <div>
        {fakeApplications.map((application) => (
          <ApplicationCard
            key={application.id}
            application={application}
          />
        ))}
      </div>
    </div>
  );
}

export default StudentApplicationsPage;