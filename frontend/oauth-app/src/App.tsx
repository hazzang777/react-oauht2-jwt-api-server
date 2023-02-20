import React, {useState, useEffect} from 'react';
import './App.css';
import {call} from "./service/ApiService";
import {AppBar, Button, Grid, Typography, Toolbar, Paper, Container} from "@mui/material";
import {signout} from "./service/ApiService";

function App() {

  const [user, setUser] = useState<UserInfo | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    call("/user-info", "GET", null)
        .then((response) => {
          console.log("response = ", response);
          let data = response.data;
          setUser(new UserInfo(data.userId, data.provider, data.oauthId));
          setLoading(false);
        });
  }, []);

    let navigationBar = (
        <AppBar position="static">
            <Toolbar>
                <Grid container justifyContent="space-between">
                    <Grid item>
                        <Typography variant='h6'>OAuth2 + JWT 로그인 및 회원가입 테스트</Typography>
                    </Grid>
                    <Grid item>
                        <Button color={"inherit"} onClick={signout}>
                            로그아웃
                        </Button>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>
    );

  let userInfo = user != null && (
      <div>
          {navigationBar}
          <Container maxWidth={"md"}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography component={"h5"} variant={"h5"}>
                    회원 번호 : {user.userId}
                </Typography>
                <Typography component={"h5"} variant={"h5"}>
                    OAuth 2.0 제공자 : {user.provider}
                </Typography>
                <Typography component={"h5"} variant={"h5"}>
                    회원 OAuthId : {user.oauthId}
                </Typography>
              </Grid>
            </Grid>
          </Container>
      </div>
  );


  let loadingPage = <h1>로딩중..</h1>;
  let content: any = loadingPage;

  if (!loading) {
    content = userInfo;
  }

  return <div className={"App"}>{content}</div>;

}

class UserInfo {
    public userId: number
    public provider: string
    public oauthId: string

    constructor(
        userId: number,
        provider: string,
        oauthId: string
    ) {
        this.userId = userId;
        this.provider = provider;
        this.oauthId = oauthId;
    }
}

export default App;