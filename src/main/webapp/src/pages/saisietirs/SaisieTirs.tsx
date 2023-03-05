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
import {
  AimOutlined,
  CheckOutlined,
  CloseOutlined,
  ClockCircleOutlined,
  FieldTimeOutlined,
  TeamOutlined,
} from "@ant-design/icons";
import TagEpreuve from "../../components/tag/epreuve/TagEpreuve";
import { SUBAPP_URL } from "../../Utils/AppUtils";
import TargetPreview from "../../components/targetpreview/TargetPreview";
import { impactData, TargetData } from "../../Utils/target/TargetUtils";
const { Header, Sider, Content } = Layout;

interface CibleData {
  idCompetiteur: number;
  cheminImg: string;
  epreuve: string;
  nom: string;
  impacts: impactData[];
}

type epreuveInfosOptions = {
  [key: string]: {
    label: string;
    icon: React.ReactNode;
  };
};

const epreuvesInfos: epreuveInfosOptions = {
  SUPER_BIATHLON: {
    label: "Super Biathlon",
    icon: <FieldTimeOutlined />,
  },
  BIATHLON: {
    label: "Biathlon",
    icon: <ClockCircleOutlined />,
  },
  PRECISION: {
    label: "Précision",
    icon: <AimOutlined />,
  },
  RELAIS: {
    label: "Relais",
    icon: <TeamOutlined />,
  },
};

type MenuItem = Required<MenuProps>["items"][number];

const SaisieTirs = () => {
  const { token } = theme.useToken();
  const [cibles, setCibles] = useState<CibleData[]>([]);
  const [selectedCible, setSelectedCible] = useState<CibleData | null>(null);
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
    epreuves.forEach((epreuve: string, indexEpreuve) => {
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
          icon: epreuvesInfos[epreuve] ? epreuvesInfos[epreuve].icon : null,
          label: (
            <>
              {epreuvesInfos[epreuve] ? epreuvesInfos[epreuve].label : null}
              <Badge
                count={childrens.length}
                size="small"
                style={{
                  transform: "translate(-20%, -50%)",
                }}
              ></Badge>
            </>
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
          visibility: selectedCible ? "visible" : "hidden",
        }}
      >
        <TargetPreview
          impacts={
            selectedCible && selectedCible.impacts ? selectedCible.impacts : []
          }
        ></TargetPreview>
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
