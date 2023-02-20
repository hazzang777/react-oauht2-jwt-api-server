import React from "react";
import {
    Container,
    Grid,
    Typography,
    Button
} from "@mui/material";
import {socialLogin} from "../service/ApiService";

const SocialLogin = () => {

    const handleSocialLogin = (provider: String) => {
        socialLogin(provider);
    }

    return (
        <Container component="main" maxWidth="xs" style={{marginTop: "8%"}}>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography component={"h1"} variant={"h5"}>
                        OAuth2.0 / JWT 테스트
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    <Button onClick={() => handleSocialLogin("google")}
                            fullWidth variant={"contained"}
                            style={{backgroundColor: '#000'}}
                    >
                        구글 로그인 / 회원가입
                    </Button>
                    <Button onClick={() => handleSocialLogin("kakao")}
                            fullWidth variant={"contained"}
                            style={{backgroundColor: '#FFFF00'}}
                    >
                        <Typography color={'#111'}>
                        카카오 로그인 / 회원가입
                        </Typography>
                    </Button>
                </Grid>
            </Grid>
        </Container>
    );
}

export default SocialLogin;