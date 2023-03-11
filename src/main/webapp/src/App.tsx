import React, { ReactNode, useEffect, useRef, useState } from "react";
import {
  AimOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  SettingOutlined,
  UserOutlined,
  OrderedListOutlined,
  TeamOutlined,
  FlagOutlined,
  PlusOutlined,
} from "@ant-design/icons";
import { Button, Layout, Menu, theme, Typography, ConfigProvider } from "antd";
const { Title, Text } = Typography;
import {
  blue,
  presetDarkPalettes,
  presetPalettes,
  presetPrimaryColors,
} from "@ant-design/colors";

import logo_l_white from "./assets/logo_l_white.png";
import logo_s_white from "./assets/logo_s_white.png";
import "./App.css";
import Competiteurs from "./pages/competiteurs/Competiteurs";
import SaisieTirs from "./pages/saisietirs/SaisieTirs";
import CardAdresse from "./components/card/adresse/CardAdresse";
import TargetPreview from "./components/targetpreview/TargetPreview";

const { Header, Sider, Content } = Layout;
function App() {
  const { token } = theme.useToken();
  console.log(presetPrimaryColors);
  const [collapsed, setCollapsed] = useState(false);

  const { defaultAlgorithm, darkAlgorithm } = theme;

  const [selectedKey, setSelectedKey] = useState("1");
  const switchContent = (): ReactNode => {
    switch (selectedKey) {
      case "1":
        return <CardAdresse></CardAdresse>;
      case "2":
        return <SaisieTirs />;
      case "3":
        return <Competiteurs />;
      case "4":
        return <div>Résultats</div>;
      case "5":
        return <div>Paramètres</div>;
      case "Calcul_tir":
    }
  };
  const getLabel = (): string => {
    return items.find((item) => item.key === selectedKey)?.label || "";
  };

  const items = [
    {
      key: "1",
      icon: <FlagOutlined />,
      label: "Arbitrage",
    },
    {
      key: "2",
      icon: <AimOutlined />,
      label: "Saisie des tirs",
    },
    {
      key: "3",
      icon: <TeamOutlined />,
      label: "Compétiteurs",
    },
    {
      key: "Calcul_tir",
      icon: <PlusOutlined />,
      label: "Calcul tir",
    },
    {
      key: "4",
      icon: <OrderedListOutlined />,
      label: "Résultats",
    },
    {
      key: "5",
      icon: <SettingOutlined />,
      label: "Paramètres",
    },
  ];
  // @ts-ignore
  return (
    <ConfigProvider>
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
            items={items}
          />
        </Sider>
        <Layout className="site-layout">
          <Header
            style={{
              padding: "0 16px",
              display: "flex",
              gap: "16px",
              alignItems: "center",
              backgroundColor: token.colorBgContainer,
              borderBottom: `1px solid ${token.colorBorder}`,
            }}
          >
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
            <Text strong>{getLabel()}</Text>
          </Header>

          <Content style={{}}>{switchContent()}</Content>
        </Layout>
      </Layout>
    </ConfigProvider>
  );
}

export default App;
