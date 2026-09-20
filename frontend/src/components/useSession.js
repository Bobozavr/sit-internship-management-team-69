import { createContext, useContext } from "react";
export const Session = createContext(null);
export function useSession() { return useContext(Session); }
