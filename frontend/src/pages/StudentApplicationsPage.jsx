import { Link } from "react-router-dom";
import ApplicationCard from "../components/ApplicationCard";
import useApiData from "../api/useApiData";
import { useSession } from "../components/useSession";
export default function StudentApplicationsPage() {
 const {user}=useSession(); const {data,loading,error}=useApiData('/student/applications');
 return <main><header className="page-header"><span className="eyebrow">STUDENT WORKSPACE</span><h1>My applications</h1><p>Applications submitted by {user.firstName} {user.lastName} · {user.email}</p><p>Only your applications appear here. The company reviews each application and updates its status.</p></header>
 {loading?<p role="status">Loading your applications…</p>:error?<p role="alert">{error}</p>:<><div className="section-heading"><h2>Your applications</h2><span>{data.length} submitted</span></div>{data.length===0?<section className="empty-panel"><h2>No applications yet</h2><p>Choose an internship and send a motivation letter to apply.</p><Link className="button" to="/student/offers">Find internships →</Link></section>:<div className="application-list">{data.map(app=><ApplicationCard key={app.id} application={app}/>)}</div>}</>}
 </main>;
}
