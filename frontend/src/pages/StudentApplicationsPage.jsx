import ApplicationCard from "../components/ApplicationCard";
import { getMyApplications } from "../api/applicationApi";

function StudentApplicationsPage() {
  let applications;
  try { applications = getMyApplications(); }
  catch (error) { return <main><h1>My applications</h1><p role="alert">{error.message}</p></main>; }
  return (
    <main>
      <header className="page-header"><span className="eyebrow">YOUR JOURNEY / APPLICATIONS</span><h1>Every step counts.</h1><p>Keep your opportunities in sight. Follow your applications and hear from your next team.</p></header>
      <div className="section-heading"><h2>My applications</h2><span>{applications.length} submitted</span></div>
      <div className="application-list">
        {applications.length === 0 && <div className="empty-panel"><h2>Your journey starts here.</h2><p>You have not submitted any applications yet.</p></div>}
        {applications.map(application => <ApplicationCard key={application.id} application={application} />)}
      </div>
      <p className="notice">Demo workspace · One test student · Saved in this browser.</p>
    </main>
  );
}
export default StudentApplicationsPage;
