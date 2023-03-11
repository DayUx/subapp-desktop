export const getSubAppUrl = () => {
  return "http://localhost:8080/api";
  return `${location.protocol}//${location.hostname}:${location.port}/api`;
};
