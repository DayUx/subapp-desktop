
import targetBlueprint from  "../../assets/target_blueprint.svg";


interface TargetData{
    topLeft: impactData[];
    topRight: impactData[];
    bottomLeft: impactData[];
    bottomRight: impactData[];
    center: impactData[];
}
interface impactData {
    point: number;
    angle: number;
}

const TargetPreview = () => {
    return (
        <div className="target-preview" style={{
            width: 300,
            height: 300,
            backgroundImage: `url(${targetBlueprint})`,
        }}>

        </div>
    );
}

export default TargetPreview;