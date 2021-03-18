import VerifyUserToken from "./VerifyUserToken.js";
import user_me from "../index.js";

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

// Methodes Refactorer pour juste l'id dans le token et le user dans variable de l'index.
const getUserSessionData_NEW = () => {
  return user_me;
};

const setUserSessionData_NEW = (user, isRemember) => {
  const storageValue = JSON.stringify(user.id);
  if(isRemember) localStorage.setItem(STORE_NAME, storageValue);
  else sessionStorage.setItem(STORE_NAME, storageValue);

  user_me = user;
};

const removeSessionData_NEW = () => {
  localStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(STORE_NAME);

  user_me = null;
};

const checkTokenOnLoad_NEW = () => {
  let retrievedId = localStorage.getItem(STORE_NAME);
  let isLocalToken = true;
  if(!retrievedId){
    retrievedId = sessionStorage.getItem(STORE_NAME);
    isLocalToken = false;
  }
  if(!retrievedId) return;

  VerifyUserToken(retrievedId, isLocalToken);
};

export { getUserSessionData, setUserSessionData, removeSessionData, checkTokenOnLoad };
