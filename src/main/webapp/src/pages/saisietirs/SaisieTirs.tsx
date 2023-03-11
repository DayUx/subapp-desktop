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
  Modal,
  Segmented,
  Tag,
  theme,
  Tooltip,
  Upload,
  UploadFile,
  UploadProps,
} from "antd";

import {
  AimOutlined,
  CheckOutlined,
  CloseOutlined,
  ClockCircleOutlined,
  FieldTimeOutlined,
  TeamOutlined,
  PlusOutlined,
} from "@ant-design/icons";
import TagEpreuve from "../../components/tag/epreuve/TagEpreuve";
import { getSubAppUrl } from "../../Utils/AppUtils";
import TargetPreview from "../../components/targetpreview/TargetPreview";
import { impactData, TargetData } from "../../Utils/target/TargetUtils";
import { RcFile, UploadChangeParam } from "antd/es/upload";
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
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");
  const [previewTitle, setPreviewTitle] = useState("");
  const [file, setFile] = useState<UploadFile | null>(null);
  const [loading, setLoading] = useState(false);
  const [resultImageUrl, setResultImageUrl] = useState("");

  useEffect(() => {
    fetch(`${getSubAppUrl()}/cible/getAll`, {
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

  const getBase64 = (file: RcFile): Promise<string> =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });

  const handleCancel = () => setPreviewOpen(false);

  const handlePreview = async (file: UploadFile) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj as RcFile);
    }

    setPreviewImage(file.url || (file.preview as string));
    setPreviewOpen(true);
    setPreviewTitle(
      file.name || file.url!.substring(file.url!.lastIndexOf("/") + 1)
    );
  };

  const handleChange: UploadProps["onChange"] = (
    info: UploadChangeParam<UploadFile>
  ) => {
    if (info.file.status === "uploading") {
      setLoading(true);
      return;
    }
    if (info.file.status === "done") {
      // Get this url from response in real world.
      getBase64(info.file.originFileObj as RcFile).then((imageUrl) => {
        setLoading(false);
        setPreviewImage(imageUrl);
        setPreviewOpen(true);
        setPreviewTitle(
          info.file.name ||
            info.file.url!.substring(info.file.url!.lastIndexOf("/") + 1)
        );
      });
    }
  };

  const uploadButton = (
    <div>
      <PlusOutlined />
      <div style={{ marginTop: 8 }}>Upload</div>
    </div>
  );

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
        <Image src={resultImageUrl}></Image>
        <button
          onClick={() => {
            cibles.push(cibles[0]);
            setCibles([...cibles]);
          }}
        >
          Valider
        </button>

        <Upload
          multiple={false}
          action={`${getSubAppUrl()}/cible/uploadCible`}
          listType="picture-card"
          fileList={[file as UploadFile]}
          onPreview={handlePreview}
          onChange={handleChange}
          beforeUpload={(file) => {
            setFile(file);
            return false;
          }}
        >
          {uploadButton}
        </Upload>
        <Modal
          visible={previewOpen}
          title={previewTitle}
          footer={null}
          onCancel={handleCancel}
        >
          <img alt="example" style={{ width: "100%" }} src={previewImage} />
        </Modal>
        <Button
          onClick={() => {
            if (file) {
              const formData = new FormData();
              formData.append("file", file as RcFile);
              setLoading(true);
              fetch(`${getSubAppUrl()}/cible/uploadCible`, {
                method: "POST",
                body: formData,
              }).then((res) => {
                const ok = res.ok;
                res.text().then(async (json) => {
                  if (ok) {
                    setLoading(false);
                    setResultImageUrl("data:image/jpeg;base64," + json);
                    console.log(json);
                  } else {
                    console.log("error");
                  }
                });
              });
            }
          }}
        >
          Upload
        </Button>
      </div>
    </div>
  );
};

export default SaisieTirs;
