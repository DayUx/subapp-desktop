import React, { useState } from 'react'
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UploadOutlined,
  UserOutlined,
  VideoCameraOutlined,
} from '@ant-design/icons';
import {Button, Layout, Menu, theme} from 'antd';

import logo_l_white from './assets/logo_l_white.png'
import logo_s_white from './assets/logo_s_white.png'

const { Header, Sider, Content } = Layout;
import './App.css'
import Icon from "antd/es/icon";

function App() {
  const [count, setCount] = useState(0)
    const [collapsed, setCollapsed] = useState(false);
    const {
        token: { colorBgContainer },
    } = theme.useToken();
  return (
      <Layout style={{height:"100vh"}}>
        <Sider trigger={null} collapsible collapsed={collapsed}>
          <div className="logo " >
              <img src={collapsed ? logo_s_white:logo_l_white}/>
          </div>
          <Menu
              theme="dark"
              mode="inline"
              defaultSelectedKeys={['1']}
              items={[
                {
                  key: '1',
                  icon: <UserOutlined />,
                  label: 'nav 1',
                },
                {
                  key: '2',
                  icon: <VideoCameraOutlined />,
                  label: 'nav 2',
                },
                {
                  key: '3',
                  icon: <UploadOutlined />,
                  label: 'nav 3',
                },
              ]}
          />
        </Sider>
        <Layout className="site-layout" >
          <Header style={{ padding: '0 16px', background: colorBgContainer }}>
              <Button onClick={
                  () => setCollapsed(!collapsed)
              } icon={collapsed ?<MenuUnfoldOutlined></MenuUnfoldOutlined>  : <MenuFoldOutlined></MenuFoldOutlined>}  type={'primary'}></Button>

          </Header>
          <Content
              style={{
                margin: '24px 16px',
                padding: 24,
                background: colorBgContainer,
              }}
          >
            Content
          </Content>
        </Layout>
      </Layout>
  )
}

export default App
