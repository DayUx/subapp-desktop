import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";
import { BrowserRouter, HashRouter, Route, Routes } from "react-router-dom";
import { Button } from "antd";
import { Content } from "antd/es/layout/layout";
import logo from "./assets/logo_l_mix.png";
ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <HashRouter>
    <Routes>
      <Route
        path={"/connect"}
        element={
          <Content
            style={{
              display: "flex",
              flexDirection: "column",
              gap: 20,
              alignItems: "center",
              justifyContent: "center",
              width: "100%",
              height: "100vh",
            }}
          >
            <img
              src={logo}
              style={{
                width: "100%",
                height: "auto",
                maxWidth: 300,
                maxHeight: 300,
              }}
            />
            <Button href={"http://subapp.tirsub"}>Se connecter</Button>
          </Content>
        }
      />
      <Route path={"/*"} element={<App></App>} />
    </Routes>
  </HashRouter>
);
