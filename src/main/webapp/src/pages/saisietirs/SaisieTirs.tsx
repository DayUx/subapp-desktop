import React, { useEffect, useState } from "react";
import { Button, Card, Image, Layout, Segmented, Tooltip } from "antd";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons";
import TagEpreuve from "../../components/tag/epreuve/TagEpreuve";
import { SUBAPP_URL } from "../../Utils/AppUtils";
const { Header, Sider, Content } = Layout;

interface Cible {
  idCompetiteur: number;
  cheminImg: string;
  epreuve: string;
  nom: string;
}

const SaisieTirs = () => {
  const [cibles, setCibles] = useState<Cible[]>([]);

  useEffect(() => {
    fetch(`${SUBAPP_URL}/cible/getAll`, {
      method: "GET",
    }).then((res) => {
      const ok = res.ok;
      res.json().then((json) => {
        if (ok) {
          setCibles(json);
        } else {
          console.log("error");
        }
      });
    });
  }, []);

  return (
    <Content
      style={{
        height: "100%",
        display: "flex",
        flexWrap: "wrap",
        overflowY: "scroll",
        gap: 10,
      }}
    >
      {cibles.map((cible, key) => (
        <Card
          key={key}
          style={{
            width: 350,
            height: 350,
          }}
          title={`${cible.idCompetiteur} - ${cible.nom}`}
          extra={
            <>
              <TagEpreuve libelle={cible.epreuve} />
              <Tooltip
                placement="bottom"
                title={"Valider la saisie automatique"}
              >
                <Button
                  shape={"circle"}
                  type={"primary"}
                  icon={<CheckOutlined />}
                ></Button>
              </Tooltip>
              <Tooltip
                placement="bottom"
                title={"Refuser la saisie automatique"}
              >
                <Button
                  style={{ marginLeft: 10 }}
                  shape={"circle"}
                  type={"primary"}
                  icon={<CloseOutlined />}
                  danger
                ></Button>
              </Tooltip>
            </>
          }
        >
          <Image src={cible.cheminImg} alt={cible.epreuve} />
        </Card>
      ))}
    </Content>
  );
};

export default SaisieTirs;
