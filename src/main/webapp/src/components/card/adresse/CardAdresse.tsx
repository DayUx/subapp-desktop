import { Card, Layout, QRCode, Skeleton } from "antd";
import React, { useEffect, useState } from "react";
import { getSubAppUrl } from "../../../Utils/AppUtils";
const { Content } = Layout;
interface Adresse {
  port: number;
  ip: string;
}

const CardAdresse = () => {
  const [adresse, setAdresse] = useState<Adresse>({} as Adresse);

  useEffect(() => {
    fetch(`${getSubAppUrl()}/app/getIpAndPort`, {
      method: "GET",
    }).then((res) => {
      const ok = res.ok;
      res.json().then((json) => {
        if (ok) {
          setAdresse(json);
        } else {
          console.log("error");
        }
      });
    });
  }, []);

  const isLoaded = () => {
    return adresse.ip && adresse.port;
  };

  return (
    <Card
      title="Connecter un smartphone"
      style={{
        width: 300,
      }}
    >
      <Content
        style={{
          display: "flex",
          flexDirection: "column",
          gap: 20,
          alignItems: "center",
        }}
      >
        <QRCode
          value={`https://${adresse.ip}:${adresse.port}/connect`}
          status={adresse.ip && adresse.port ? undefined : "loading"}
        />

        {isLoaded() ? (
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              border: "1px solid #1677ff",
              color: "#1677ff",
              fontSize: 16,
              borderRadius: 10,
              overflow: "hidden",
            }}
          >
            <span
              style={{
                flex: 1,
                padding: "10px 20px",
              }}
            >
              {adresse.ip}
            </span>
            <span
              style={{
                width: "fit-content",
                backgroundColor: "#1677ff",
                color: "white",
                height: "100%",
                padding: "10px 20px",
              }}
              className={"port"}
            >
              {adresse.port}
            </span>
          </div>
        ) : (
          <Skeleton.Input block={true} active></Skeleton.Input>
        )}
      </Content>
    </Card>
  );
};

export default CardAdresse;
