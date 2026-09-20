import { Link } from "react-router-dom";
import useApiData from "../api/useApiData";
export default function CompanyWorkspace() {
 const offers=useApiData('/company/offers'); const applications=useApiData('/company/applications');
 return <main><header className="page-header"><span className="eyebrow">COMPANY WORKSPACE</span><h1>Our offers & applications</h1><p>Only your company’s internships and applications to those internships appear here.</p></header>
 <section><h2>Our internships</h2>{offers.loading?<p>Loading…</p>:offers.error?<p role="alert">{offers.error}</p>:offers.data.length?offers.data.map(offer=><article className="student-row" key={offer.id}><div><h3>{offer.title}</h3><p>{offer.status} · {offer.location}</p></div><Link className="text-link" to={`/student/offers/${offer.id}`}>View details →</Link></article>):<p>No internships have been published by your company yet.</p>}</section>
 <section className="company-applications"><h2>Applications to our internships</h2>{applications.loading?<p>Loading…</p>:applications.error?<p role="alert">{applications.error}</p>:applications.data.length?applications.data.map(app=><article className="application-card" key={app.id}><h3>{app.studentName}</h3><p>{app.internshipTitle} · {app.status}</p><p className="letter">{app.motivationLetter}</p></article>):<p>No students have applied to your internships yet.</p>}</section><p className="notice">Publishing offers and reviewing applications in this interface are being connected next.</p>
 </main>;
}
