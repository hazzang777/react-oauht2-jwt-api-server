import React from "react";
import {Navigate} from "react-router-dom";

const SocialLoginProc = (props: any) => {
    const getUrlParameter = (name: string) => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        return params.get(name);
    }

    console.log(props.location)

    const token = getUrlParameter("token");

    if (token) {
        localStorage.setItem("ACCESS_TOKEN", token);
        return (
            <Navigate state={{from: props.location}} to={{pathname: "/"}}/>
        );
    } else {
        return (
            <Navigate to={{pathname: "/sociallogin"}} state={{from: props.location}} />
        )
    }
};

export default SocialLoginProc;