import React, { useEffect, useState } from "react";
import {
  Badge,
  Button,
  Card,
  Image,
  Layout,
  List,
  Menu,
  MenuProps,
  Segmented,
  Tag,
  theme,
  Tooltip,
} from "antd";
import { AimOutlined, CheckOutlined, CloseOutlined } from "@ant-design/icons";
import TagEpreuve from "../../components/tag/epreuve/TagEpreuve";
import { SUBAPP_URL } from "../../Utils/AppUtils";
import TargetPreview from "../../components/targetpreview/TargetPreview";
const { Header, Sider, Content } = Layout;

interface Cible {
  idCompetiteur: number;
  cheminImg: string;
  epreuve: string;
  nom: string;
}
type MenuItem = Required<MenuProps>["items"][number];

const SaisieTirs = () => {
  const { token } = theme.useToken();
  const [cibles, setCibles] = useState<Cible[]>([]);
  const [selectedCible, setSelectedCible] = useState<Cible | null>(null);
  const [selectedKeys, setSelectedKeys] = useState();

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

  const isKeySelected = (key: string) => {
    if (!selectedKeys) {
      return false;
    }
    // @ts-ignore
    return selectedKeys.keyPath.includes(key);
  };

  const generateMenuItems = (): MenuProps["items"] => {
    const epreuves = [...new Set(cibles.map((cible) => cible.epreuve))];
    let menuItems: MenuProps["items"] = [];
    epreuves.forEach((epreuve, indexEpreuve) => {
      if (menuItems) {
        const childrens = cibles
          .filter((cible) => cible.epreuve === epreuve)
          .map((cible, index) => {
            return {
              key: `${indexEpreuve}-${index}`,
              label: `${cible.idCompetiteur} - ${cible.nom}`,
              onClick: () => {
                setSelectedCible(cible);
              },
            } as MenuItem;
          });

        menuItems[indexEpreuve] = {
          key: indexEpreuve,
          label: (
            <Badge
              count={childrens.length}
              size="small"
              style={{
                transform: "translate(80%, -40%)",
              }}
            >
              {epreuve}
            </Badge>
          ),
          children: childrens,
        };
      }
    });
    return menuItems;
  };

  return (
    <div
      style={{
        height: "100%",
        display: "flex",
        flexDirection: "row",
        gap: 10,
      }}
    >
      <Menu
        style={{
          width: 300,
        }}
        onSelect={(keys) => {
          // @ts-ignore
          setSelectedKeys(keys);
        }}
        mode="inline"
        items={generateMenuItems()}
      ></Menu>

      <div
        style={{
          flex: 1,
        }}
      >
        <TargetPreview></TargetPreview>
        <Image src={selectedCible?.cheminImg}></Image>
        <button
          onClick={() => {
            cibles.push(cibles[0]);
            setCibles([...cibles]);
          }}
        >
          Valider
        </button>
      </div>
    </div>
  );
};

export default SaisieTirs;
