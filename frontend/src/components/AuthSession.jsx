import { useEffect, useState } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { apiRequest } from "../api/authApi";
import { Session, useSession } from "./useSession";
export function AuthSession({ children }) {
  const [state,setState] = useState({ user:null, loading:true, error:"" });
  useEffect(() => {
    let active=true, version=0;
    async function load() {
      const current=++version;
      if (!sessionStorage.getItem("internship.auth.token")) { setState({user:null,loading:false,error:""}); return; }
      setState({user:null,loading:true,error:""});
      try { const user=await apiRequest("/auth/me"); if(active && current===version) setState({user,loading:false,error:""}); }
      catch(err) { if(active && current===version) setState({user:null,loading:false,error:err.message}); }
    }
    load(); window.addEventListener("authchange",load);
    return () => { active=false; window.removeEventListener("authchange",load); };
  },[]);
  return <Session.Provider value={state}>{children}</Session.Provider>;
}
export function SessionGate({ children }) {
  const {user,loading}=useSession(); const location=useLocation();
  if(loading) return <main><p role="status">Checking your account…</p></main>;
  if(user?.passwordChangeRequired && location.pathname!=="/account") return <Navigate to="/account" replace />;
  return children;
}
export function RequireRole({ role, children }) {
  const {user,loading}=useSession();
  if(loading) return <main><p role="status">Checking your account…</p></main>;
  if(!user) return <Navigate to={role==='STUDENT'?'/university-login':'/login?role=company'} replace />;
  if(user.role!==role) return <Navigate to="/account" replace />;
  return children;
}
