export const getSubAppUrl = () => {
  if (!process.env.NODE_ENV || process.env.NODE_ENV === "development") {
    return "http://localhost:8080/api";
  } else {
    return `${location.protocol}//${location.hostname}:${location.port}/api`;
  }
};
