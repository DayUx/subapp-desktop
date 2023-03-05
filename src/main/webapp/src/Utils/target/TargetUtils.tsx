export interface TargetData {
  topLeft: impactData[];
  topRight: impactData[];
  bottomLeft: impactData[];
  bottomRight: impactData[];
  center: impactData[];
}

export enum TargetZone {
  TOP_LEFT = "TOP_LEFT",
  TOP_RIGHT = "TOP_RIGHT",
  BOTTOM_LEFT = "BOTTOM_LEFT",
  BOTTOM_RIGHT = "BOTTOM_RIGHT",
  CENTER = "CENTER",
}
export interface impactData {
  points: number;
  angle: number;

  zone?: TargetZone;
}
