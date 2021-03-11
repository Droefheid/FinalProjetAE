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
};

export { getUserSessionData, setUserSessionData, removeSessionData };
