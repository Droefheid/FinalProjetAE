import VerifyUserToken from "./VerifyUserToken.js";

const STORE_NAME = "user";
const getUserSessionData = () => {
  let retrievedUser = localStorage.getItem(STORE_NAME);
  if (!retrievedUser) retrievedUser = sessionStorage.getItem(STORE_NAME);
  if (!retrievedUser) return;
  return JSON.parse(retrievedUser);
};

const setUserSessionData = (user, isRemember) => {
  const storageValue = JSON.stringify(user);
  if(isRemember) localStorage.setItem(STORE_NAME, storageValue);
  else sessionStorage.setItem(STORE_NAME, storageValue);
};

const removeSessionData = () => {
  localStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(STORE_NAME);
};

const checkTokenOnLoad = () => {
  let retrievedUser = getUserSessionData();
  if(!retrievedUser) return;
  let isLocalToken = localStorage.getItem(STORE_NAME);
  if(!isLocalToken) isLocalToken = false;
  else isLocalToken = true;
  let id = retrievedUser.user.id;
  VerifyUserToken(id, isLocalToken);
};

export { getUserSessionData, setUserSessionData, removeSessionData, checkTokenOnLoad };
