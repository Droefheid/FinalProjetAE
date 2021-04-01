import VerifyUserToken from "./verifyUserToken.js";
import { user_me } from "../index.js";

const STORE_NAME = "user";
/*const getUserSessionData_OLD = () => {
  let retrievedUser = localStorage.getItem(STORE_NAME);
  if (!retrievedUser) retrievedUser = sessionStorage.getItem(STORE_NAME);
  if (!retrievedUser) return;
  return JSON.parse(retrievedUser);
};

const setUserSessionData_OLD = (user, isRemember) => {
  const storageValue = JSON.stringify(user);
  if(isRemember) localStorage.setItem(STORE_NAME, storageValue);
  else sessionStorage.setItem(STORE_NAME, storageValue);
};

const removeSessionData_OLD = () => {
  localStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(STORE_NAME);
};

const checkTokenOnLoad_OLD = () => {
  let retrievedUser = getUserSessionData();
  if(!retrievedUser) return;
  let isLocalToken = localStorage.getItem(STORE_NAME);
  if(!isLocalToken) isLocalToken = false;
  else isLocalToken = true;
  let id = retrievedUser.user.id;
  VerifyUserToken(id, isLocalToken);
};*/

// Methodes Refactorer pour juste l'id dans le token et le user dans variable de l'index.
const getUserSessionData = () => {
  return user_me.itself;
};

const getTokenSessionDate = () => {
  let retrievedToken = localStorage.getItem(STORE_NAME);
  if (!retrievedToken) {
    retrievedToken = sessionStorage.getItem(STORE_NAME);
  }
  return retrievedToken;
}

const setUserSessionData = (user, isRemember) => {
  const storageValue = JSON.stringify(user.token);
  if(isRemember) localStorage.setItem(STORE_NAME, storageValue);
  else sessionStorage.setItem(STORE_NAME, storageValue);

  user_me.itself = user.user;
};

const removeSessionData = () => {
  localStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(STORE_NAME);

  user_me.itself = null;
};

const checkTokenOnLoad = () => {
  let retrievedToken = localStorage.getItem(STORE_NAME);
  let isLocalToken = true;
  if(!retrievedToken){
    retrievedToken = sessionStorage.getItem(STORE_NAME);
    isLocalToken = false;
  }
  if(!retrievedToken) return;

  VerifyUserToken(retrievedToken, isLocalToken); //TODO /me avec un token
};

export { getUserSessionData, getTokenSessionDate, setUserSessionData, removeSessionData, checkTokenOnLoad };
