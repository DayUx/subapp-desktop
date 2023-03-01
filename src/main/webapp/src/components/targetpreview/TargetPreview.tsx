import targetBlueprint from "../../assets/target_blueprint.svg";
import { Tooltip } from "antd";

interface TargetData {
  topLeft: impactData[];
  topRight: impactData[];
  bottomLeft: impactData[];
  bottomRight: impactData[];
  center: impactData[];
}

enum TargetZone {
  topLeft = "topLeft",
  topRight = "topRight",
  bottomLeft = "bottomLeft",
  bottomRight = "bottomRight",
  center = "center",
}
interface impactData {
  point: number;
  angle: number;
}

const TargetPreview = () => {
  const caseSize = 9;
  const cibleSize = 2565;
  const visuelSize = 810;
  const globalPadding = 180;

  let TargetData = {
    topLeft: [
      {
        point: 570,
        angle: 0,
      },
    ],
    topRight: [
      {
        point: 570,
        angle: 0,
      },
    ],
    bottomLeft: [
      {
        point: 570,
        angle: 0,
      },
    ],
    bottomRight: [
      {
        point: 570,
        angle: 0,
      },
    ],
    center: [
      {
        point: 570,
        angle: 0,
      },
    ],
  };

  const placeImpact = (impact: impactData, area: TargetZone) => {
    let center = {
      x: "0%",
      y: "0%",
    };
    switch (area) {
      case TargetZone.topLeft:
        center.x = sizeToPercent(globalPadding + visuelSize / 2);
        center.y = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        break;
      case TargetZone.topRight:
        center.x = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        center.y = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        break;
      case TargetZone.bottomLeft:
        center.x = sizeToPercent(globalPadding + visuelSize / 2);
        center.y = sizeToPercent(globalPadding + visuelSize / 2);
        break;
      case TargetZone.bottomRight:
        center.x = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        center.y = sizeToPercent(globalPadding + visuelSize / 2);
        break;
      case TargetZone.center:
        center.x = "50%";
        center.y = "50%";
        break;
      default:
        break;
    }

    return (
      <Tooltip title={`Point: ${impact.point}`}>
        <div
          style={{
            position: "absolute",
            height: 10,
            width: 10,
            transform: `translate(-50%, 50%)`,
            borderRadius: "50%",
            backgroundColor: "red",
            bottom: center.y,
            left: center.x,
          }}
        ></div>
      </Tooltip>
    );
  };

  const sizeToPercent = (size: number) => {
    return (size / cibleSize) * 100 + "%";
  };

  return (
    <div
      className="target-preview"
      style={{
        width: 500,
        height: 500,
        backgroundColor: "white",
        backgroundImage: `url(${targetBlueprint})`,
        position: "relative",
      }}
    >
      {TargetData.topLeft.map((impact: impactData, index) => {
        return placeImpact(impact, TargetZone.topLeft);
      })}
      {TargetData.topRight.map((impact: impactData, index) => {
        return placeImpact(impact, TargetZone.topRight);
      })}
      {TargetData.bottomLeft.map((impact: impactData, index) => {
        return placeImpact(impact, TargetZone.bottomLeft);
      })}
      {TargetData.bottomRight.map((impact: impactData, index) => {
        return placeImpact(impact, TargetZone.bottomRight);
      })}
      {TargetData.center.map((impact: impactData, index) => {
        return placeImpact(impact, TargetZone.center);
      })}
    </div>
  );
};

export default TargetPreview;
