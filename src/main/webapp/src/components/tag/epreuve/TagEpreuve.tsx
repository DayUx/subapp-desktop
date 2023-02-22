import React, { useEffect, useState } from "react";
import { Button, Card, Image, Tag } from "antd";
import { CheckOutlined } from "@ant-design/icons";

// @ts-ignore
const TagEpreuve = ({ libelle }) => {
  const tags = [
    {
      libelle: "BIATHLON",
      color: "red",
    },
    {
      libelle: "SUPER_BIATHLON",
      color: "blue",
    },
    {
      libelle: "PRECISION",
      color: "green",
    },
    {
      libelle: "RELAIS",
      color: "yellow",
    },
  ];

  return (
    <Tag color={tags.find((tag) => tag.libelle === libelle)?.color || ""}>
      {tags.find((tag) => tag.libelle === libelle)?.libelle || ""}
    </Tag>
  );
};

export default TagEpreuve;
