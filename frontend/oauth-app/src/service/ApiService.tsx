import {API_BASE_URL} from "../config/api-config";

export function call(api: String, method: String, request?: any) {

    let headers = new Headers({
        "Content-Type": "application/json"
    });

    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if (accessToken && accessToken !== null) {
        headers.append("Authorization", "Bearer " + accessToken);
    }

    let options: any = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method
    };

    if (request){
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options)
        .then((response) => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 403) {
                window.location.href = "/sociallogin";
            } else {
                Promise.reject(response);
                // @ts-ignore
                throw Error(response);
            }
        }).catch((error) => {
            console.log("error = ", error);
        });
}

export function socialLogin(provider: String) {
    const frontendUrl = window.location.protocol + "//" + window.location.host;
    console.log("어떻게 생김? " + frontendUrl)
    window.location.href = API_BASE_URL + "/auth/authorize/" + provider + "?redirect_uri=" + frontendUrl;
}

export function signout() {
    localStorage.removeItem("ACCESS_TOKEN");
    window.location.href = "/sociallogin";
}