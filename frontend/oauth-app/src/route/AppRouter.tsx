import React from "react";
import "../index.css";
import App from "../App";
import SocialLogin from "../component/SocialLogin";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import SocialLoginProc from "../service/SocialLoginProc";

function AppRouter(){
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"} element={<App />}/>
                    <Route path={"/sociallogin"} element={<SocialLogin />}/>
                    <Route path={"/social-login-proc"} element={<SocialLoginProc />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default AppRouter;