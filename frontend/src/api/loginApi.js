import axios from "axios";

const client = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export const login = async ({ loginId, password }) => {
  const response = await client.post("/auth/login", { loginId, password });
  return response.data;
};
