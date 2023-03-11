import React, { useEffect, useState } from "react";
import { Table, Tag, Tooltip } from "antd";
import { ColumnsType } from "antd/es/table";

import { ManOutlined, WomanOutlined } from "@ant-design/icons";
import { getSubAppUrl } from "../../Utils/AppUtils";
interface DataType {
  nom: string;
  prenom: string;
  sexe: string;
  dateNaissance: Date;
  categorie: string;
  club: string;
  licence: number;
}
const Competiteurs = () => {
  const [dataSource, setDataSource] = useState<DataType[] | undefined>();

  useEffect(() => {
    fetch(`${getSubAppUrl()}/competiteur/getAll`, {
      method: "GET",
    }).then((res) => {
      const ok = res.ok;
      res.json().then((json) => {
        if (ok) {
          setDataSource(json);
        } else {
          console.log("error");
        }
      });
    });
  }, []);

  const columns: ColumnsType<DataType> = [
    {
      title: "N°",
      dataIndex: "licence",
      showSorterTooltip: false,
      key: "licence",
      width: 130,
      render: (text) => <b>{text}</b>,
      sorter: (a, b) => a.licence - b.licence,
    },

    {
      title: "Nom Prénom",
      dataIndex: "nom",
      showSorterTooltip: false,

      sorter: (a, b) => a.nom.localeCompare(b.nom),
      render: (text, record) => {
        return `${record.nom} ${record.prenom}`;
      },
      width: "100%",
    },

    {
      title: "Sexe",
      dataIndex: "sexe",
      width: 50,
      showSorterTooltip: false,

      sorter: (a, b) => a.sexe.localeCompare(b.sexe),
      render: (sexe, record) => {
        return sexe === "MASCULIN" ? (
          <Tooltip placement="bottom" title={"Masculin"}>
            <ManOutlined
              style={{
                color: "blue",
              }}
            />
          </Tooltip>
        ) : (
          <Tooltip placement="bottom" title={"Féminin"}>
            <WomanOutlined
              style={{
                color: "red",
              }}
            />
          </Tooltip>
        );
      },
    },
    {
      title: "Catégorie",
      dataIndex: "categorie",
      width: 100,
      showSorterTooltip: false,

      sorter: (a, b) => a.categorie.localeCompare(b.categorie),
      render: (text, record) => {
        switch (text) {
          case "MINIME":
            return <Tag color={"blue"}>Minime</Tag>;
          case "CADET":
            return <Tag color={"green"}>Cadet</Tag>;
          case "JUNIOR":
            return <Tag color={"gold"}>Junior</Tag>;
          case "SENIOR":
            return <Tag color={"volcano"}>Senior</Tag>;
          case "MASTER":
            return <Tag color={"magenta"}>Master</Tag>;
          default:
            return "Inconnu";
        }
      },
    },
    {
      width: 100,
      title: "Date de naissance",
      dataIndex: "dateNaissance",
      showSorterTooltip: false,

      sorter: (a, b) =>
        new Date(a.dateNaissance).getTime() -
        new Date(b.dateNaissance).getTime(),
      render: (dateNaissance, record) => {
        return new Date(dateNaissance).toLocaleDateString();
      },
    },
  ];

  return <Table dataSource={dataSource} columns={columns} />;
};

export default Competiteurs;
