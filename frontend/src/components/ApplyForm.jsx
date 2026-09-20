import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useSession } from "./useSession";
import { getMyApplications, submitApplication } from "../api/applicationApi";
export default function ApplyForm({offer}) {
  const {user}=useSession();
  if(!user) return <section><h2>Interested in this internship?</h2><p>Sign in with your student account to submit an application.</p><Link className="button" to="/university-login">Student sign in</Link></section>;
  if(user.role!=='STUDENT') return <p>Only students can apply for internships. You are signed in as {user.role.toLowerCase()}.</p>;
  return <StudentApply key={`${user.id}-${offer.id}`} offer={offer} />;
}
function StudentApply({offer}) {
  const [letter,setLetter]=useState(''); const [error,setError]=useState('');
  const [applied,setApplied]=useState(false); const [loading,setLoading]=useState(true); const [busy,setBusy]=useState(false);
  useEffect(()=>{let active=true; getMyApplications().then(apps=>{if(active){setApplied(apps.some(a=>a.internshipOfferId===offer.id));setLoading(false);}}).catch(err=>{if(active){setError(err.message);setLoading(false);}});return ()=>{active=false;};},[offer.id]);
  async function submit(e) {e.preventDefault();setBusy(true);setError('');try{await submitApplication(offer.id,letter);setApplied(true);}catch(err){setError(err.message);}finally{setBusy(false);}}
  if(loading)return <p role="status">Checking your applications…</p>;
  if(applied)return <section><p role="status">You have applied for this internship.</p><Link className="text-link" to="/student/applications">View my applications →</Link></section>;
  const now=new Date(); const today=`${now.getFullYear()}-${String(now.getMonth()+1).padStart(2,'0')}-${String(now.getDate()).padStart(2,'0')}`;
  if(offer.status!=='ACTIVE' || offer.deadline<today)return <p>This internship is no longer accepting applications.</p>;
  return <section><h2>Apply for this internship</h2><p>Your application will be sent to {offer.companyName} and saved to your student account.</p><form onSubmit={submit}><label htmlFor="motivationLetter">Motivation letter</label><textarea id="motivationLetter" required maxLength={3000} value={letter} onChange={e=>setLetter(e.target.value)} disabled={busy}/>{error&&<p role="alert">{error}</p>}<button type="submit" disabled={busy||!letter.trim()}>{busy?'Sending…':'Submit my application'}</button></form></section>;
}
