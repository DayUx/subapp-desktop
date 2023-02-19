import React, { ReactNode, useState } from "react";
import {
  AimOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  SettingOutlined,
  UserOutlined,
  OrderedListOutlined,
} from "@ant-design/icons";
import { Button, Layout, Menu, theme } from "antd";

import logo_l_white from "./assets/logo_l_white.png";
import logo_s_white from "./assets/logo_s_white.png";
import "./App.css";
import Competiteurs from "./pages/Competiteurs/Competiteurs";

const { Header, Sider, Content } = Layout;

function App() {
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const [selectedKey, setSelectedKey] = useState("1");
  const switchContent = (): ReactNode => {
    switch (selectedKey) {
      case "1":
        return <div>Saisie des tirs</div>;
      case "2":
        return <div>Résultats</div>;
      case "3":
        return <Competiteurs />;
      case "4":
        return <div>Paramètres</div>;
    }
  };
  return (
    <Layout style={{ height: "100vh" }}>
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="logo ">
          <img src={collapsed ? logo_s_white : logo_l_white} />
        </div>
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={["1"]}
          selectedKeys={[selectedKey]}
          onSelect={(e) => setSelectedKey(e.key)}
          items={[
            {
              key: "1",
              icon: <AimOutlined />,
              label: "Saisie des tirs",
            },
            {
              key: "2",
              icon: <OrderedListOutlined />,
              label: "Résultats",
            },
            {
              key: "3",
              icon: <UserOutlined />,
              label: "Compétiteurs",
            },
            {
              key: "4",
              icon: <SettingOutlined />,
              label: "Paramètres",
            },
          ]}
        />
      </Sider>
      <Layout className="site-layout">
        <Header style={{ padding: "0 16px", background: colorBgContainer }}>
          <Button
            onClick={() => setCollapsed(!collapsed)}
            icon={
              collapsed ? (
                <MenuUnfoldOutlined></MenuUnfoldOutlined>
              ) : (
                <MenuFoldOutlined></MenuFoldOutlined>
              )
            }
          ></Button>
        </Header>

        <Content
          style={{
            margin: "24px 16px",
            padding: 24,
            background: colorBgContainer,
          }}
        >
          {switchContent()}
        </Content>
      </Layout>
    </Layout>
  );
}

export default App;
