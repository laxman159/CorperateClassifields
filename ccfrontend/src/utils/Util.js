const userObject = window.localStorage.getItem("employee");
export const token = JSON.parse(userObject)?.authToken;
export const loggedInUserId = JSON.parse(userObject)?.empid;
