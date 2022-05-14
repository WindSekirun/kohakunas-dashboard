import axios from "axios";
const API_URL = import.meta.env.VITE_API_URL

const instance = axios.create({
  baseURL: `${API_URL}`,
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;
export const LOGIN = "/api/authenticate";
export const USER = "/api/user"
export const TOKEN = "/api/token"