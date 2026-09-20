import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { apiRequest } from "../api/authApi";
export default function CompanyRegistrationStatusPage() {
 const {hash}=useLocation(); return <RegistrationStatus key={hash} token={hash.slice(1)} />;
}
function RegistrationStatus({token}) {
 const valid=/^[A-Za-z0-9_-]{43}$/.test(token);
 const [data,setData]=useState(null),[error,setError]=useState(''),[busy,setBusy]=useState(true),[refresh,setRefresh]=useState(0);
 useEffect(()=>{let active=true;if(!valid) return;
 apiRequest('/company-registration-requests/status',{headers:{'X-Registration-Token':token}}).then(result=>{if(active){setData(result);setError('');setBusy(false);}}).catch(err=>{if(active){setError(err.message);setBusy(false);}});return()=>{active=false;};},[token,refresh,valid]);
 const date=value=>new Intl.DateTimeFormat('en-GB',{dateStyle:'medium',timeStyle:'short'}).format(new Date(value));
 return <main><header className="page-header"><span className="eyebrow">COMPANY REGISTRATION</span><h1>Registration status</h1><p>Keep this private link. Anyone with it can view the decision and administrator’s feedback.</p></header><section className="empty-panel">{!valid?<p role="alert">Open the complete status link you received after submitting your registration.</p>:busy?<p role="status">Checking status…</p>:error?<p role="alert">{error}</p>:data&&<><h2>{data.companyName}</h2><p className={`registration-state state-${data.status}`} role="status">{data.status==='PENDING'?'Under review':data.status==='APPROVED'?'Approved':'Not approved'}</p>{data.status==='PENDING'&&<p>The administrator is checking your company details. Your account is not available yet.</p>}{data.adminComment&&<div className="feedback"><strong>Administrator’s feedback</strong><p>{data.adminComment}</p></div>}{data.status==='APPROVED'&&<><p>Your company account is ready. Use the sign-in email and password from your registration.</p><Link className="button" to="/login?role=company">Company sign in →</Link></>}{data.status==='REJECTED'&&<><p>You can submit corrected details from {data.resubmitAt?date(data.resubmitAt):'the time confirmed by the administrator'} (24 hours after rejection).</p>{data.resubmitAt&&new Date(data.resubmitAt)<=new Date()&&<Link className="button" to="/company-registration">Submit a new request →</Link>}</>}<p>Submitted {date(data.requestedAt)}</p></>}<button className="reset-button" disabled={busy || !valid} onClick={()=>{setBusy(true);setRefresh(value=>value+1);}}>Refresh status</button></section></main>;
}
