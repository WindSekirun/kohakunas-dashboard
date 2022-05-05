import axios from "axios";
const API_URL = import.meta.env.API_URL

const instance = axios.create({
  baseURL: `${API_URL}/api`,
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;