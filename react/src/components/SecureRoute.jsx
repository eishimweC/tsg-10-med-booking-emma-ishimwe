import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const SecureRoute = ({ component: Component }) => {
  const [authenticated, setAuthenticated] = useState(null);

  useEffect(() => {
    try {
      const sessionCookie = document.cookie.split("; ").find(row => row.startsWith("JSESSIONID="));
      const [value, expires] = sessionCookie.split("=")[1].split("|");
      const expirationTimestamp = parseInt(expires, 10);
    
      if (Date.now() > expirationTimestamp) {
        console.log("Session has expired");
      } else {
        console.log("Session is valid");
      }
      
      if (sessionCookie) {
        setAuthenticated(true);
      } else {
        setAuthenticated(false);
        useNavigate("/"); // Redirect to login
      }
    } catch (error) {
      console.error("Error checking authentication:", error);
      setAuthenticated(false);
      useNavigate("/"); // Redirect to login
    }
  }, []);

  return authenticated ? <Component /> : null;
};

export default SecureRoute;
