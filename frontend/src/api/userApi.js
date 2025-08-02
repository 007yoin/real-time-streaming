import axios from "axios";

const client = axios.create({
  baseURL: "",
  headers: {
    "Content-Type": "application/json",
  },
});

// 회원가입 API
export const registerUser = async ({ loginId, name, password }) => {
  const response = await client.post("/user", { loginId, name, password });
  return response.data;
};
