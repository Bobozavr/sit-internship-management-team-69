import { useEffect, useState } from "react";
import { apiRequest } from "./authApi";
export default function useApiData(path) {
  const [result,setResult]=useState({data:null,error:"",loading:true});
  useEffect(()=>{let active=true; apiRequest(path).then(data=>{if(active)setResult({path,data,error:"",loading:false});}).catch(err=>{if(active)setResult({path,data:null,error:err.message,loading:false});}); return ()=>{active=false;};},[path]);
  return result.path === path ? result : {data:null,error:"",loading:true};
}
