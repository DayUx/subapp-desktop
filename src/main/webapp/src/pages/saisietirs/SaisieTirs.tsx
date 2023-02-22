import React, { useEffect, useState } from "react";
import { Button, Card, Image } from "antd";
import { CheckOutlined, CloseOutlined } from "@ant-design/icons";
import TagEpreuve from "../../components/tag/epreuve/TagEpreuve";

interface Cible {
  idCompetiteur: number;
  cheminImg: string;
  epreuve: string;
  nom: string;
}

const SaisieTirs = () => {
  const [cibles, setCibles] = useState<Cible[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/cible/getAll", {
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
    <div
      style={{
        width: 300,
      }}
    >
      {cibles.map((cible) => (
        <div>
          <Card
            title={`${cible.nom} - ${cible.epreuve}`}
            extra={
              <>
                <TagEpreuve libelle={cible.epreuve} />
                <Button type={"primary"} icon={<CheckOutlined />}></Button>
                <Button
                  style={{ marginLeft: 10 }}
                  type={"primary"}
                  icon={<CloseOutlined />}
                  danger
                ></Button>
              </>
            }
          >
            <Image
              src={cible.cheminImg}
              width={200}
              height={200}
              alt={cible.epreuve}
            />
          </Card>
        </div>
      ))}
    </div>
  );
};

export default SaisieTirs;
