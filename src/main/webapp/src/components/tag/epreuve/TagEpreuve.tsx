import React, { useEffect, useState } from "react";
import { Button, Card, Image, Tag } from "antd";
import { CheckOutlined } from "@ant-design/icons";

// @ts-ignore
const TagEpreuve = ({ libelle }) => {
  const tags = [
    {
      value: "BIATHLON",
      libelle: "Biathlon",
      color: "red",
    },
    {
      value: "SUPER_BIATHLON",
      libelle: "Super Biathlon",
      color: "blue",
    },
    {
      value: "PRECISION",
      libelle: "Précision",
      color: "green",
    },
    {
      value: "RELAIS",
      libelle: "Relais",
      color: "yellow",
    },
  ];

  return (
    <Tag color={tags.find((tag) => tag.value === libelle)?.color || ""}>
      {tags.find((tag) => tag.value === libelle)?.libelle || ""}
    </Tag>
  );
};

export default TagEpreuve;
