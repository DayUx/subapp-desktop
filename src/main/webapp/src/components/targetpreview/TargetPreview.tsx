import targetBlueprint from "../../assets/target_blueprint.svg";
import { Tooltip } from "antd";
import { impactData, TargetZone } from "../../Utils/target/TargetUtils";

type TargetPreviewProps = {
  impacts: impactData[];
};

const TargetPreview = ({ impacts = [] }: TargetPreviewProps) => {
  const caseSize = 9;
  const cibleSize = 2565;
  const visuelSize = 810;
  const globalPadding = 180;
  const arrowSize = 9 * 6;

  const pointToDistance = (points: number) => {
    let i;

    for (i = 0; i < 43 && points > 411; i++) {
      points = points - 3;
    }
    for (; i < 48 && points > 411; i++) {
      points = points - 6;
    }
    console.log(i, points);
    return 48 - i;
  };

  const placeImpact = (impact: impactData) => {
    let center = {
      x: "0%",
      y: "0%",
    };
    switch (impact.zone) {
      case TargetZone.TOP_LEFT:
        center.x = sizeToPercent(globalPadding + visuelSize / 2);
        center.y = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        break;
      case TargetZone.TOP_RIGHT:
        center.x = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        center.y = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        break;
      case TargetZone.BOTTOM_LEFT:
        center.x = sizeToPercent(globalPadding + visuelSize / 2);
        center.y = sizeToPercent(globalPadding + visuelSize / 2);
        break;
      case TargetZone.BOTTOM_RIGHT:
        center.x = sizeToPercent(cibleSize - globalPadding - visuelSize / 2);
        center.y = sizeToPercent(globalPadding + visuelSize / 2);
        break;
      case TargetZone.CENTER:
        center.x = "50%";
        center.y = "50%";
        break;
      default:
        break;
    }

    return (
      <Tooltip title={`Point: ${impact.points}`}>
        <div
          style={{
            position: "absolute",
            height: sizeToPercent(arrowSize),
            width: sizeToPercent(arrowSize),
            transform: `rotate(${impact.angle}deg) translate(${
              ((pointToDistance(impact.points) * caseSize) / arrowSize) * 100
            }%) `,
            borderRadius: "50%",
            backgroundColor: "red",
            bottom: `calc(${center.y} - ${sizeToPercent(arrowSize / 2)})`,
            left: `calc(${center.x} - ${sizeToPercent(arrowSize / 2)})`,
          }}
        ></div>
      </Tooltip>
    );
  };

  const sizeToPercent = (size: number) => {
    console.log((size / cibleSize) * 100 + "%");
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
      {impacts.map((impact: impactData, index: number) => {
        return placeImpact(impact);
      })}
    </div>
  );
};

export default TargetPreview;
